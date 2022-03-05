package by.bechess.app;

import java.util.ArrayList;

public class Chess {
    public static void main(String[] args){
        Game game = new Game(Menu.getSideColor());
        game.startNewGame();
        AI aiPlayer = new AI(game.getGameBoard(), (game.getPlayerColor() == Color.WHITE) ? Color.BLACK : Color.WHITE);

        //Бясконца па-чарзе даем магчымасць выбару ходу беламу і чорнаму
        while(game.getGameStatus() != Game.Status.FINISHED) {
            String enteredMove;
            if (game.getTurnColor() == game.getPlayerColor()) {
                enteredMove = Menu.getPlayerMove();
            }
            else {
                enteredMove = Menu.getOpponentMove(aiPlayer);
            }

            game.makeMove(enteredMove);
            //
            /*ArrayList<Move> moves = game.getGameBoard().getAllPossibleMoves(game.getTurnColor());
            for (Move move : moves) {
                System.out.println(move.getNotation());
            }*/
        }
    }
}