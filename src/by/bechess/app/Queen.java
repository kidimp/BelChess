package by.bechess.app;

public class Queen extends Piece{
    public Queen(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2655" : "\u265b";
        this.shortName = "G";
        this.value = 9;
    }

    @Override
    public boolean isCanTakeThrone() { return false; }

    @Override
    public boolean isPossibleMove(Cell toCell) {
        int deltaX = Math.abs(cell.getX() -toCell.getX());
        int deltaY = Math.abs(cell.getY() -toCell.getY());

        if (((deltaX == 0) && (deltaY != 0)) || ((deltaY == 0) && (deltaX != 0))
            || (deltaX == deltaY)) {
            if ((toCell.getPiece() == null) || (isCanTake(toCell))) {
                return true;
            }
        }

        return false;
    }
}
