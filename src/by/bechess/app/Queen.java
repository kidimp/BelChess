package by.bechess.app;

public class Queen extends Piece{
    public Queen(Cell cell, Color color){
        super(cell, color);
        this.name = (color == Color.WHITE) ? "\u2655" : "\u265b";
        this.shortName = "G";
        this.value = 5;
    }

    public boolean isPossibleMove(int x, int y) {
        return true;
    }
}
