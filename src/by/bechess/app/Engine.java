package by.bechess.app;

import java.util.ArrayList;

public class Engine {
    private int depth;
    private AI ai;
    private final double WORST_VALUE = -100_000.0,
                         BEST_VALUE = 100_000.0;

    public Engine(AI ai, int depth) {
        this.depth = depth;
        this.ai = ai;
    }

    public MoveInfo getBestMove() {
        Board board = ai.getGameBoard();
        Color sideColor = ai.getSideColor();
        Move bestMove = null;

        ArrayList<Move> moves = board.getAllPossibleMoves(sideColor);

        //Калі хадоў няма, не правяраем далей
        if (moves.size() == 0) {
            return null;
        }

        //Мінімакс + альфа-бэта адсячэнне
        double bestMoveValue = WORST_VALUE +1, worstMoveValue = BEST_VALUE -1;
        for (Move move : moves) {
            move.make();
            double value = miniMax(depth -1, board, Color.getReversedColor(sideColor), false,
                    WORST_VALUE, BEST_VALUE);
            move.revoke();

            if (value >= bestMoveValue) {
                bestMoveValue = value;
                bestMove = move;
            }
        }

        return new MoveInfo(bestMove);
    }

    private double miniMax(int currentDepth, Board currentBoard, Color currentColor, boolean isMaximization, double alpha, double beta) {
        if (currentDepth == 0) {
            return evaluateBoard(ai.getSideColor());
        }

        ArrayList<Move> moves = currentBoard.getAllPossibleMoves(currentColor);
        if (isMaximization) {
            double maxBestMove = WORST_VALUE +1;
            for (Move move : moves) {
                move.make();
                double value = miniMax(currentDepth -1, currentBoard, Color.getReversedColor(currentColor), !isMaximization, alpha, beta);
                move.revoke();

                if (value > maxBestMove) {
                    maxBestMove = value;
                }

                alpha = Math.max(alpha, value);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxBestMove;
        }
        else {
            double minBestMove = BEST_VALUE -1;
            for (Move move : moves) {
                move.make();
                double value = miniMax(currentDepth -1, currentBoard, Color.getReversedColor(currentColor), !isMaximization, alpha, beta);
                move.revoke();

                if (value < minBestMove) {
                    minBestMove = value;
                }

                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    break;
                }
            }
            return minBestMove;
        }
    }

    //Падлічэнне сумы вагі фігур. Кожная фігура памнажаецца на 10
    private double calculateAllPiecesValue() {
        double result = 0.0;
        for (Piece piece : ai.getGameBoard().getAllPieces()) {
            if (piece.cell != null) {
                result += piece.value * 10 * ((piece.getColor() == Color.WHITE) ? 1 : -1);
            }
        }
        return result;
    }

    private double evaluateBoard(Color colorToEvaluate) {
        return calculateAllPiecesValue()*((colorToEvaluate == Color.WHITE) ? 1 : -1);
    }

    public class PieceEvaluation {
        protected double value;
        protected double[][] debutValues;
        //protected double[][] mittelspielValues;
        //protected double[][] endspielValues;
        protected Piece piece;

        public PieceEvaluation() {
            debutValues = new double[Board.BOARD_SIZE][Board.BOARD_SIZE];
            //mittelspielValues = new double[Board.BOARD_SIZE][Board.BOARD_SIZE];
            //endspielValues = new double[Board.BOARD_SIZE][Board.BOARD_SIZE];
        }

        protected double getEvaluation(int stage) {
            return debutValues[piece.cell.getX()][piece.cell.getY()];
        }
    }
}
