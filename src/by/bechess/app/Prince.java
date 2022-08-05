package by.bechess.app;

public class Prince extends Piece{
    public Prince(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2654" : "\u265a";
        this.shortName = "K";
        this.value = 9;
    }

    @Override
    public boolean isCanTakeThrone() { return true; }

    @Override
    public boolean isPossibleMove(Cell toCell) {
        int deltaX = Math.abs(cell.getX() -toCell.getX());
        int deltaY = Math.abs(cell.getY() -toCell.getY());

        if (((deltaX == 0) && (deltaY <= 2)) || ((deltaY == 0) && (deltaX <= 2))
                || ((deltaX == deltaY) && (deltaX <= 2))) {
            if ((toCell.getPiece() == null) || (isCanTake(toCell))) {
                return true;
            }
        }

        return false;
    }
}
