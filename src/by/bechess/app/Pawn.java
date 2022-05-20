package by.bechess.app;

public class Pawn extends Piece{
    public Pawn(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2659" : "\u265f";
        this.shortName = "r";
        this.value = 1;
    }

    public boolean isCanTakeThrone() { return false; }

    public boolean isPossibleMove(Cell toCell) {
        int deltaX = Math.abs(cell.getX() -toCell.getX());
        int deltaY = Math.abs(cell.getY() -toCell.getY());

        if (((deltaX == 0)
                && ((deltaY == 1) || ((deltaY == 2) && (movesCount == 0))) && (toCell.getPiece() == null))
            || ((deltaX == 1) && (deltaY == 1) && (isCanTake(toCell)))) {
            //Ход толькі наперад
            if (((color == Color.WHITE) && (cell.getY() < toCell.getY()))
                || ((color == Color.BLACK) && (cell.getY() > toCell.getY()))) {
                return true;
            }
        }

        return false;
    }
}
