package by.bechess.app.engine;

import by.bechess.app.*;

public class Evaluator {
    private static final int VALUE_MULTIPLAYER = 10;
    KingEvaluation kingEval;
    PrinceEvaluation princeEval;
    QueenEvaluation queenEval;
    RookEvaluation rookEval;
    BishopEvaluation bishopEval;
    KnightEvaluation knightEval;
    PawnEvaluation pawnEval;

    Evaluator(){
        kingEval = new KingEvaluation();
        princeEval = new PrinceEvaluation();
        queenEval = new QueenEvaluation();
        rookEval = new RookEvaluation();
        bishopEval = new BishopEvaluation();
        knightEval = new KnightEvaluation();
        pawnEval = new PawnEvaluation();
    };

    private PieceEvaluation getPieceEvaluationInstance(Piece piece) {
        if (piece.getClass() == King.class) {
            return kingEval;
        }
        if (piece.getClass() == Prince.class) {
            return princeEval;
        }
        if (piece.getClass() == Queen.class) {
            return queenEval;
        }
        if (piece.getClass() == Rook.class) {
            return rookEval;
        }
        if (piece.getClass() == Bishop.class) {
            return bishopEval;
        }
        if (piece.getClass() == Knight.class) {
            return knightEval;
        }
        if (piece.getClass() == Pawn.class) {
            return pawnEval;
        }
        return null;
    }


    public double getPieceEvaluation(Piece piece) {
        return getPieceEvaluationInstance(piece).getEvaluation(piece);
    }

    public double getPieceValue(Piece piece) {
        return getPieceEvaluationInstance(piece).getValue();
    }

    public abstract class PieceEvaluation {
        protected double[][] debutValues;
        protected double[][] mittelspielValues;
        protected double[][] endspielValues;
        protected int value;

        public double getEvaluation(Piece piece) {
            int y = (piece.getColor() == Color.WHITE) ? Board.BOARD_SIZE - 1 - piece.getCell().getY() : piece.getCell().getY();
            double positionBonus = debutValues[y][piece.getCell().getX()];
            double valueBonus = value * VALUE_MULTIPLAYER;

            return  valueBonus + positionBonus;
        }

        public double getValue() { return value; }
    }

    public class KingEvaluation extends PieceEvaluation {
        KingEvaluation() {
            value = 120;
            debutValues = new double[][]{
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 2.08, 2.08, 2.08, 0.0, 0.0, 0.0}
            };
        }
    }
    public class PrinceEvaluation extends PieceEvaluation {
        PrinceEvaluation() {
            value = 9;
            debutValues = new double[][]{
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                    {0.0, 0.0, 0.0, 5.55, 5.55, 5.55, 0.0, 0.0, 0.0}
            };
        }
    }
    public class QueenEvaluation extends PieceEvaluation {
        QueenEvaluation() {
            value = 9;
            debutValues = new double[][]{
                    {6.66, 13.33, 13.33, 13.33, 11.11, 13.33, 13.33, 13.33, 6.66},
                    {13.33, 19.55, 23.11, 23.11, 19.55, 23.11, 23.11, 19.55, 13.33},
                    {13.33, 23.11, 21.33, 24.88, 21.33, 24.88, 21.33, 23.11, 13.33},
                    {13.33, 23.11, 24.88, 23.11, 23.11, 23.11, 24.88, 23.11, 13.33},
                    {11.11, 19.55, 21.33, 23.11, 0.0, 23.11, 21.33, 19.55, 11.11},
                    {13.33, 23.11, 24.88, 23.11, 23.11, 23.11, 24.88, 23.11, 13.33},
                    {13.33, 23.11, 21.33, 24.88, 21.33, 24.88, 21.33, 23.11, 13.33},
                    {13.33, 19.55, 23.11, 23.11, 19.55, 23.11, 23.11, 19.55, 13.33},
                    {6.66, 13.33, 13.33, 13.33, 11.11, 13.33, 13.33, 13.33, 6.66}
            };
        }
    }
    public class RookEvaluation extends PieceEvaluation {
        RookEvaluation() {
            value = 5;
            debutValues = new double[][]{
                    {6.4, 6.4, 9.6, 9.6, 7.2, 9.6, 9.6, 6.4, 6.4},
                    {6.4, 12.8, 12.8, 12.8, 9.6, 12.8, 12.8, 12.8, 6.4},
                    {6.4, 12.8, 12.8, 12.8, 9.6, 12.8, 12.8, 12.8, 6.4},
                    {9.6, 12.8, 12.8, 12.8, 9.6, 12.8, 12.8, 12.8, 9.6},
                    {9.6, 9.6, 9.6, 9.6, 0.0, 9.6, 9.6, 9.6, 9.6},
                    {9.6, 12.8, 12.8, 12.8, 9.6, 12.8, 12.8, 12.8, 9.6},
                    {6.4, 12.8, 12.8, 12.8, 9.6, 12.8, 12.8, 12.8, 6.4},
                    {6.4, 12.8, 12.8, 12.8, 9.6, 12.8, 12.8, 12.8, 6.4},
                    {6.4, 6.4, 9.6, 9.6, 7.2, 9.6, 9.6, 6.4, 6.4}
            };
        }
    }
    public class BishopEvaluation extends PieceEvaluation {
        BishopEvaluation() {
            value = 3;
            debutValues = new double[][]{
                    {1.33, 5.33, 5.33, 5.33, 5.33, 5.33, 5.33, 5.33, 1.33},
                    {5.33, 13.3, 10.66, 16.0, 16.0, 16.0, 10.66, 13.3, 5.33},
                    {5.33, 13.3, 10.66, 0.33, 0.33, 0.33, 10.66, 13.3, 5.33},
                    {5.33, 13.33, 0.33, 0.33, 0.33, 0.33, 0.33, 13.33, 5.33},
                    {5.33, 13.3, 0.33, 0.33, 0.0, 0.33, 0.33, 13.33, 5.33},
                    {5.33, 13.3, 0.33, 0.33, 0.33, 0.33, 0.33, 13.33, 5.33},
                    {5.33, 13.3, 10.66, 0.33, 0.33, 0.33, 10.66, 13.3, 5.33},
                    {5.33, 8.0, 13.3, 13.3, 13.3, 13.3, 13.3, 8.0, 5.33},
                    {1.33, 5.33, 5.33, 5.33, 5.33, 5.33, 5.33, 5.33, 1.33}
            };
        }
    }
    public class KnightEvaluation extends PieceEvaluation {
        KnightEvaluation() {
            value = 3;
            debutValues = new double[][]{
                    {1.33, 3.0, 5.33, 4.0, 4.0, 4.0, 5.33, 3.0, 1.33},
                    {3.0, 5.33, 12.0, 12.0, 12.0, 12.0, 12.0, 5.33, 3.0},
                    {5.33, 12.0, 21.33, 21.33, 21.33, 21.33, 21.33, 12.0, 5.33},
                    {4.0, 12.0, 21.33, 21.33, 21.33, 21.33, 21.33, 12.0, 4.0},
                    {4.0, 12.0, 21.33, 21.33, 0.0, 21.33, 21.33, 12.0, 4.0},
                    {4.0, 12.0, 21.33, 21.33, 21.33, 21.33, 21.33, 12.0, 4.0},
                    {5.33, 12.0, 21.33, 21.33, 21.33, 21.33, 21.33, 12.0, 5.33},
                    {3.0, 5.33, 12.0, 12.0, 12.0, 12.0, 12.0, 5.33, 3.0},
                    {1.33, 3.0, 5.33, 5.33, 5.33, 5.33, 5.33, 3.0, 1.33}
            };
        }
    }
    public class PawnEvaluation extends PieceEvaluation {
        PawnEvaluation() {
            value = 1;
            debutValues = new double[][]{
                    {20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0},
                    {8.0, 8.0, 18.0, 18.0, 18.0, 18.0, 18.0, 8.0, 8.0},
                    {6.0, 6.0, 16.0, 18.0, 18.0, 18.0, 16.0, 6.0, 6.0},
                    {1.0, 4.0, 16.0, 18.0, 18.0, 18.0, 16.0, 4.0, 1.0},
                    {1.0, 4.0, 16.0, 18.0, 0.0, 18.0, 16.0, 4.0, 1.0},
                    {1.0, 4.0, 14.0, 18.0, 18.0, 18.0, 14.0, 4.0, 1.0},
                    {1.0, 6.0, 14.0, 16.0, 16.0, 16.0, 14.0, 6.0, 1.0},
                    {1.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 1.0},
                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
            };
        }
    }
}