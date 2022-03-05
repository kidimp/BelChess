package by.bechess.app;

public class Rook extends Piece{
    public Rook(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2656" : "\u265c";
        this.shortName = "L";
        this.value = 4;
    }

    public boolean isCanTakeThrone() { return false; }

    public boolean isPossibleMove(Cell toCell) {
        int deltaX = Math.abs(cell.getX() -toCell.getX());
        int deltaY = Math.abs(cell.getY() -toCell.getY());

        if (((deltaX == 0) && (deltaY != 0)) || ((deltaY == 0) && (deltaX != 0))) {
            if ((toCell.getPiece() == null) || (isCanTake(toCell))) {
                return true;
            }
        }

        return false;
    }
}
