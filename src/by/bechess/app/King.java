package by.bechess.app;

public class King extends Piece{
    public King(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2654" : "\u265a";
        this.shortName = "A";
        this.value = 7;
    }

    @Override
    public boolean isCanTakeThrone() { return true; }

    @Override
    public boolean isPossibleMove(Cell toCell) {
        int deltaX = Math.abs(cell.getX() -toCell.getX());
        int deltaY = Math.abs(cell.getY() -toCell.getY());

        if ((deltaX <= 1) && (deltaY <= 1)) {
            if ((toCell.getPiece() == null) || (isCanTake(toCell))) {
                return true;
            }
        }

        return false;
    }
}
