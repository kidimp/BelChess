package by.bechess.app;

public abstract class Piece {
    protected Cell cell;
    protected String name,
                     shortName;
    protected int value;
    protected Color color;

    public Piece(Cell cell, Color color){
        this.cell = cell;
        this.cell.setPiece(this);
        this.color = color;
    }

    public void draw(){

    }

    public String getName() { return name; }

    public String getShortName() { return shortName; }

    public abstract boolean isPossibleMove(int x, int y);

    public void move(int x, int y){
        //cell.setPiece(null);
    }

    public void remove() {
        cell = null;
    }

}
