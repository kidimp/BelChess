package by.bechess.app;

public class Knight extends Piece{
    public Knight(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2658" : "\u265e";
        this.shortName = "V";
        this.value = 2;
    }

    public boolean isPossibleMove(int x, int y) {
        return true;
    }
}
