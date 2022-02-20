public class King extends Piece{
    public King(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2654" : "\u265a";
        this.shortName = "A";
        this.value = 7;
    }

    public boolean isPossibleMove(int x, int y) {
        return true;
    }
}
