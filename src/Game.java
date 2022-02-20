public class Game {
    private String PIECES_START_POSITIONS = "lvmgakvml/rrrrrrrrr/9/9/9/9/9/RRRRRRRRR/LMVKAGMVL";

    public void startNewGame(){
        Board gameBoard = new Board();
        gameBoard.setupPieces(PIECES_START_POSITIONS);
        gameBoard.draw();
    }
}
