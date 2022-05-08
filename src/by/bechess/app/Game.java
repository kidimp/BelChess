package by.bechess.app;

import java.util.ArrayList;

public class Game {
    private final String PIECES_START_POSITIONS = "lvmgakvml/rrrrrrrrr/9/9/9/9/9/RRRRRRRRR/LMVKAGMVL";
    private Color playerColor,
                  whoisTurn;
    private Status gameStatus;
    private Board gameBoard;
    private ArrayList<MoveInfo> movesHistory;

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
        movesHistory = new ArrayList<>();

        gameStatus = Status.ONGOING;
        whoisTurn = Color.WHITE;                                //Белыя пачынаюць і парамагаюць! :)

        gameBoard.draw();
    }

    public Status getGameStatus() {
        return gameStatus;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void changeTurn(){
        whoisTurn = (whoisTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public Color getTurnColor() {
        return whoisTurn;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public MoveInfo makeMove(String enteredMove) {
        MoveInfo move = new MoveInfo(enteredMove, gameBoard);
        //немагчымы ход
        if (move.getPiece() == null) {
            move.state = MoveInfo.State.NONE;
            return move;
        }
        //Магчымы ход
        if (move.getPiece().getColor() == whoisTurn) {
            if (move.isPossible()) {
                move.state = MoveInfo.State.CORRECT;

                move.make();
                movesHistory.add(move);
                changeTurn();

                gameBoard.draw();
            }
            else{
                move.state = MoveInfo.State.INCORRECT;
            }
        }

        return move;
    }

    public MoveInfo getLastMove(){
        return getMoveFromHistory(movesHistory.size() -1);
    }

    public MoveInfo getMoveFromHistory(int index){
        return movesHistory.get(index);
    }
}
