package by.bechess.app;

public class Knight extends Piece{
    public Knight(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2658" : "\u265e";
        this.shortName = "V";
        this.value = 2;
    }

    public boolean isCanTakeThrone() { return false; }

    public boolean isPossibleMove(Cell toCell) {
        int deltaX = Math.abs(cell.getX() -toCell.getX());
        int deltaY = Math.abs(cell.getY() -toCell.getY());

        if (((deltaX == 2) && (deltaY == 1)) || ((deltaY == 2) && (deltaX == 1))) {
            if ((toCell.getPiece() == null) || (isCanTake(toCell))) {
                return true;
            }
        }

        return false;
    }
}
