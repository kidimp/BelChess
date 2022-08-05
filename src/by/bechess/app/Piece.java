package by.bechess.app;

public abstract class Piece implements Cloneable {
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

    @Override
    public Piece clone() throws CloneNotSupportedException {
        Piece copyPiece = (Piece) super.clone();
        if (copyPiece.cell != null) {
            copyPiece.setCell(new Cell(copyPiece.cell.getX(), copyPiece.cell.getY()));
        }
        return copyPiece;
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
