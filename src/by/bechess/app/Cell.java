package by.bechess.app;

public class Cell {
    private int x, y;
    private final String WHITE_CELL = "\u25A0",
                         BLACK_CELL = "\u25A1",
                         THRONE_CELL = "\u25A3";
    private Piece piece = null;
    private Type cellType = Type.REGULAR;

    enum Type {
        REGULAR,
        PALACE,
        THRONE
    }

    public Cell (int x, int y){
        this.x = x;
        this.y = y;
        //Заданне тыпу ячэйкі адрознай ад стандартнай
        if ((x == 4) && (y == 4)) {
            cellType = Type.THRONE;
        }
        else{
            if ((Math.abs(4 -x) <= 2) && (Math.abs(4 -y) <= 2)) {
                cellType = Type.PALACE;
            }
        }
    }

    public Type getCellType() { return cellType; }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() { return piece; }

    public void draw(){
        String cellSymbol = ((x + y) % 2 != 0) ? WHITE_CELL : BLACK_CELL;
        if (cellType == Type.THRONE) {
            cellSymbol = THRONE_CELL;
        }
        if (piece != null) {
            cellSymbol = piece.getName();
        }
        System.out.print(cellSymbol);
    }
}
