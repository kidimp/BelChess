public class Prince extends Piece{
    public Prince(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2654" : "\u265a";
        this.shortName = "K";
        this.value = 6;
    }

    public boolean isPossibleMove(int x, int y) {
        return true;
    }
}
