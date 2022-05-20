package by.bechess.app;

public abstract class Piece {
    protected Cell cell;
    protected String name,
                     shortName;
    protected int value;
    protected Color color;
    protected int movesCount;
    //protected boolean isMoved;

    public Piece(Cell cell, Color color){
        //this.cell = cell;
        //this.cell.setPiece(this);
        setCell(cell);
        this.color = color;
        movesCount = 0;
    }

    protected boolean isCanTake(Cell toCell) {
        Piece pieceTaken = toCell.getPiece();
        if ((pieceTaken != null) && (pieceTaken.color != color)) {
            return true;
        }
        return false;
    }

    public void draw(){

    }

    public String getName() { return name; }

    public String getShortName() { return shortName; }

    public Color getColor() { return color; }

    public abstract boolean isPossibleMove(Cell toCell);

    public abstract boolean isCanTakeThrone();

    public void move(Cell toCell){
        cell.setPiece(null);
        setCell(toCell);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        this.cell.setPiece(this);
    }

    public void remove() {
        cell = null;
    }

}
