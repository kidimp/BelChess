package by.bechess.app;

public class Cell {
    private int x, y;
    private final String WHITE_CELL = "\u25A0",
                         BLACK_CELL = "\u25A1",
                         THRONE_CELL = "\u25A3";
    private Piece piece = null;

    public Cell (int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() { return piece; }

    public void draw(){
        String cellSymbol = ((x + y) % 2 != 0) ? WHITE_CELL : BLACK_CELL;
        if ((x == 4) && (y == 4)) {
            cellSymbol = THRONE_CELL;
        }
        if (piece != null) {
            cellSymbol = piece.getName();
        }
        System.out.print(cellSymbol);
    }
}
