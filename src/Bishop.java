public class Bishop extends Piece{
    public Bishop(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2657" : "\u265d";
        this.shortName = "M";
        this.value = 3;
    }

    public boolean isPossibleMove(int x, int y) {
        return true;
    }
}
