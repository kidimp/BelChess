package by.bechess.app.engine;

import by.bechess.app.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class Engine {
    private int depth;
    private AI ai;
    private final double WORST_VALUE = -100_000.0,
            BEST_VALUE = 100_000.0;
    private Evaluator evaluator;
    private IterationsCounter counter;

    public Engine(AI ai, int depth) {
        this.depth = depth;
        this.ai = ai;

        evaluator = new Evaluator();
        counter = new IterationsCounter();
    }

    public MoveInfo getBestMove() {
        Board board = ai.getGameBoard();
        Color sideColor = ai.getSideColor();
        Move bestMove = null;
        counter.start();

        ArrayList<Move> moves = board.getAllPossibleMoves(sideColor);
        //Калі хадоў няма, не правяраем далей
        if (moves.size() == 0) {
            return null;
        }

        //Мінімакс + альфа-бэта адсячэнне
        double bestMoveValue = WORST_VALUE + 1, worstMoveValue = BEST_VALUE - 1;
        for (Move move : moves) {
            move.make();
            double value = miniMax(depth - 1, board, Color.getReversedColor(sideColor), false,
                    WORST_VALUE, BEST_VALUE);
            move.revoke();

            if (value >= bestMoveValue) {
                bestMoveValue = value;
                bestMove = move;
            }
        }

        counter.stop();
        System.out.println("Seconds " + counter.getIterationTime());
        System.out.println("Number of iterations " + counter.getIterationsCount());
        return new MoveInfo(bestMove);
    }

    //Сартыроўка хадоў для адсячэння дрэнных хадоў
    private int getGuessMoveScore(Move move) {
        double score = 0;
        //Калі з'ядаем
        if (move.getPieceEaten() != null) {
            score = 10 * evaluator.getPieceValue(move.getPieceEaten()) - evaluator.getPieceValue(move.getPiece());
        }
        //Калі з'ядаюць
        int amountOfAttackers = move.getBoard().getAmountOfAttackers(move.getPiece().getCell(), move.getPiece().getColor());
        if (amountOfAttackers > 0) {
            score -= amountOfAttackers * evaluator.getPieceValue(move.getPiece());
        }

        return (int) score;
    }

    private double miniMax(int currentDepth, Board currentBoard, Color currentColor, boolean isMaximization, double alpha, double beta) {
        counter.addIteration();

        if (currentDepth == 0) {
            return evaluateBoard(currentBoard, ai.getSideColor());
        }

        ArrayList<Move> moves = currentBoard.getAllPossibleMoves(currentColor);

        //Сартыроўка хадоў
        moves.sort((move1, move2) -> getGuessMoveScore(move2) - getGuessMoveScore(move1));

        double maxBestMove = WORST_VALUE + 1, minBestMove = BEST_VALUE - 1;
        for (Move move : moves) {
            move.make();
            double value = miniMax(currentDepth - 1, currentBoard, Color.getReversedColor(currentColor), !isMaximization, alpha, beta);
            move.revoke();

            //Альфа-бэта адсячэнне
            if (isMaximization) {
                if (value > maxBestMove) {
                    maxBestMove = value;
                }
                alpha = Math.max(alpha, value);
            } else {
                if (value < minBestMove) {
                    minBestMove = value;
                }
                beta = Math.min(beta, value);
            }
            if (beta <= alpha) {
                break;
            }
        }
        return (isMaximization) ? maxBestMove : minBestMove;
    }

    //Падлічэнне сумы вагі фігур і падлічэнне сумы пазіцый фігур.
    private double calculateAllPiecesEval(Board currentBoard) {
        double result = 0.0;
        for (Piece piece : currentBoard.getAllPieces()) {
            if (piece.getCell() != null) {
                result += evaluator.getPieceEvaluation(piece) * piece.getColor().getSign();
            }
        }
        return result;
    }

    //Падлічэнне агульнага кошту пазіцыі
    private double evaluateBoard(Board currentBoard, Color colorToEvaluate) {
        return calculateAllPiecesEval(currentBoard) * colorToEvaluate.getSign();
    }
}
