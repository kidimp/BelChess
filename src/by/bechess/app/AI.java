package by.bechess.app;

import java.util.ArrayList;

public class AI {
    private Board gameBoard;
    private Color sideColor;

    public AI(Board gameBoard, Color sideColor) {
        this.gameBoard = gameBoard;
        this.sideColor = sideColor;
    }

    //Выпадковы ход з усіх магчымых
    public Move getMove(){
        ArrayList<Move> moves = gameBoard.getAllPossibleMoves(sideColor);
        return moves.get((int)(Math.random() * moves.size()));
    }
}
