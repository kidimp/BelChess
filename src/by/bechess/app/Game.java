package by.bechess.app;

public class Game {
    private final String PIECES_START_POSITIONS = "lvmgakvml/rrrrrrrrr/9/9/9/9/9/RRRRRRRRR/LMVKAGMVL";
    private Color playerColor,
                  whoisTurn;
    private Status gameStatus;
    private Board gameBoard;

    enum Status{
        FINISHED,
        ONGOING
    }

    public Game(Color playerColor){
        this.playerColor = playerColor;
    }

    public void startNewGame(){
        gameBoard = new Board(playerColor);
        gameBoard.setupPieces(PIECES_START_POSITIONS);
        gameBoard.draw();

        gameStatus = Status.ONGOING;
        whoisTurn = Color.WHITE;                                //Белыя пачынаюць і парамагаюць! :)
    }

    public Status getGameStatus() {
        return gameStatus;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public Color getTurnColor() {
        return whoisTurn;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void makeMove(String enteredMove) {
        Move move = new Move(enteredMove, gameBoard);
        if (move.isPossible()) {
            move.make();
            gameBoard.draw();
        }
        //Змяняем чарговасць ходу
        whoisTurn = (whoisTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
}
