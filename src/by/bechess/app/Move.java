package by.bechess.app;

import java.awt.*;

public class Move {
    private Point fromPoint,
                  toPoint;
    private Piece piece,
                  pieceEaten;
    private Board board;

    private Point parseStringNotation(String notation) {
        return new Point((int) notation.charAt(0) - 97, (int) notation.charAt(1) - 49);
    }

    public Move(String fromPoint, String toPoint) {
        this.fromPoint = parseStringNotation(fromPoint);
        this.toPoint = parseStringNotation(toPoint);
    }

    public Move(String fullNotation, Board board) {
        if (fullNotation != "") {
            fromPoint = parseStringNotation(fullNotation.substring(0, 2));
            toPoint = parseStringNotation(fullNotation.substring(2, 4));
            this.board = board;
            piece = board.findPiece(fromPoint);
        }
    }

    public void make() {
        piece.cell.setPiece(null);
        board.getCell(toPoint.x, toPoint.y).setPiece(piece);
        if (pieceEaten != null) {
            pieceEaten.remove();
        }
    }

    public String getNotation() {
        return "";
    }

    public boolean isPossible() {
        return (piece != null) ? true : false;
    }
}
