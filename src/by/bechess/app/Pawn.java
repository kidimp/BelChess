package by.bechess.app;

public class Pawn extends Piece{
    public Pawn(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2659" : "\u265f";
        this.shortName = "r";
        this.value = 1;
    }

    public boolean isPossibleMove(int x, int y) {
        return true;
    }
}
