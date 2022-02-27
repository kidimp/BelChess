package by.bechess.app;

public class Chess {
    public static void main(String[] args){
        Game game = new Game(Menu.getSideColor());
        game.startNewGame();

        //Бясконца па-чарзе даем магчымасць выбару ходу беламу і чорнаму
        while(game.getGameStatus() != Game.Status.FINISHED) {
            String enteredMove;
            if (game.getTurnColor() == Color.WHITE) {
                enteredMove = Menu.getPlayerMove();
            }
            else {
                enteredMove = Menu.getOpponentMove();
            }

            game.makeMove(enteredMove);
        }
    }
}