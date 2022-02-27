package by.bechess.app;

public class Rook extends Piece{
    public Rook(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2656" : "\u265c";
        this.shortName = "L";
        this.value = 4;
    }

    public boolean isPossibleMove(int x, int y) {
        return true;
    }
}
