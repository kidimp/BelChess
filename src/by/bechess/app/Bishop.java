package by.bechess.app;

public class Bishop extends Piece{
    public Bishop(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2657" : "\u265d";
        this.shortName = "M";
        this.value = 3;
    }

    @Override
    public boolean isCanTakeThrone() { return false; }

    @Override
    public boolean isPossibleMove(Cell toCell) {
        int deltaX = Math.abs(cell.getX() -toCell.getX());
        int deltaY = Math.abs(cell.getY() -toCell.getY());


        if (deltaX == deltaY) {
            if ((toCell.getPiece() == null) || (isCanTake(toCell))) {
                return true;
            }
        }

        return false;
    }
}
