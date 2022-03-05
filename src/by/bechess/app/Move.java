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

    public Move(int fromX, int fromY, int toX, int toY, Board board) {
        fromPoint = new Point(fromX, fromY);
        toPoint = new Point(toX, toY);
        this.board = board;
        piece = board.findPiece(fromPoint);
    }

    public void make() {
        piece.cell.setPiece(null);
        board.getCell(toPoint.x, toPoint.y).setPiece(piece);
        if (pieceEaten != null) {
            pieceEaten.remove();
        }
    }

    //?interface? or to Board
    private boolean isFreePath() {
        int diffX = toPoint.x - fromPoint.x;
        int diffY = toPoint.y - fromPoint.y;
        if (diffX != 0) { diffX /= Math.abs(diffX); }
        if (diffY != 0) { diffY /= Math.abs(diffY); }

        //for (int x = fromPoint.x +diffX, y = fromPoint.y +diffY; (x != toPoint.x) || (y != toPoint.y); x += diffX, y += diffY) {
        int x = fromPoint.x,
            y = fromPoint.y;
        do {
            x += diffX;
            y += diffY;

            Cell cell = board.getCell(x, y);
            if ((cell.getPiece() != null)
                    || ((cell.getCellType() == Cell.Type.THRONE) && (piece.isCanTakeThrone() == false))){
                return false;
            }
        } while ((x != toPoint.x) || (y != toPoint.y));

        return true;
    }

    public String getNotation() {
        return piece.getShortName() +(char)(fromPoint.x +97) + String.valueOf(fromPoint.y +1) + "-" +(char)(toPoint.x +97) + String.valueOf(toPoint.y +1);
    }

    public Piece getPiece() { return piece; }

    public boolean isPossible() {
        if ((piece != null) && (piece.isPossibleMove(board.getCell(toPoint.x, toPoint.y)))) {
            //Для ўсіх фігур акрамя каня павяраем ці вольны шлях
            if ((isFreePath()) || (piece.getClass() == Knight.class)) {
                return true;
            }
        }
        return false;
    }
}
