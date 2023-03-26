package by.bechess.app;

import java.util.ArrayList;

public class Chess {
    public static void main(String[] args){
        Game game = new Game(Menu.getSideColor());
        game.startNewGame();
        AI aiPlayer = new AI(game.getGameBoard(), Color.getReversedColor(game.getPlayerColor()), AI.Level.HARD);

        //Бясконца па-чарзе даем магчымасць выбару ходу беламу і чорнаму
        while(game.getGameStatus() != Game.Status.FINISHED) {
            String enteredMove;
            if (game.getTurnColor() == game.getPlayerColor()) {
                enteredMove = Menu.getPlayerMove();
            }
            else {
                enteredMove = Menu.getOpponentMove(aiPlayer);
            }

            //Робім ход, запісваем і правярае на канец гульні
            if (enteredMove != "") {
                MoveInfo lastMove = game.makeMove(enteredMove);
                Menu.messageMoveResult(lastMove);
                game.checkForEndOfGame();
            }
        }
    }
}