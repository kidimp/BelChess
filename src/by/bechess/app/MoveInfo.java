package by.bechess.app;

import java.awt.*;

public class MoveInfo extends Move{
    public State state;
    public enum State{
        NONE,
        CORRECT,
        INCORRECT
    }

    {
        state = State.NONE;
    }

    private Point parseStringNotation(String notation) {
        return new Point((int) notation.charAt(0) - 97, (int) notation.charAt(1) - 49);
    }

    public MoveInfo(Move move) {
        super(move.fromPoint.x, move.fromPoint.y, move.toPoint.x, move.toPoint.y, move.board);
    }

    public MoveInfo(String fromPoint, String toPoint) {
        Point fromXY = parseStringNotation(fromPoint);
        Point toXY = parseStringNotation(toPoint);
    }

    public MoveInfo(String fullNotation, Board board) {
        if (fullNotation != "") {
            fromPoint = parseStringNotation(fullNotation.substring(0, 2));
            toPoint = parseStringNotation(fullNotation.substring(2, 4));
            this.board = board;
            piece = board.findPiece(fromPoint);
            pieceEaten = board.findPiece(toPoint);
        }
    }

    public String getNotation() {
        return piece.getShortName() +(char)(fromPoint.x +97) + String.valueOf(fromPoint.y +1)
                + ((pieceEaten == null) ? "-" : ":") +(char)(toPoint.x +97) + String.valueOf(toPoint.y +1);
    }
}
