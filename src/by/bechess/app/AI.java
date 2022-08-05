package by.bechess.app;

import java.util.ArrayList;

public class AI {
    private Board gameBoard;
    private Color sideColor;
    private Level aiLevel;



    public AI(Board gameBoard, Color sideColor, Level aiLevel) {
        this.gameBoard = gameBoard.clone();
        this.sideColor = sideColor;
        this.aiLevel = aiLevel;
    }

    //Выпадковы ход з усіх магчымых
    public MoveInfo getMove(){
        Engine engine = new Engine(this, aiLevel.depth);
        MoveInfo move = engine.getBestMove();

        return move;

        /*Board copiedBoard = gameBoard.clone();

        ArrayList<Move> moves = copiedBoard.getAllPossibleMoves(sideColor);
        return new MoveInfo(moves.get((int)(Math.random() * moves.size())));*/
    }

    public Board getGameBoard() { return gameBoard; }

    public Color getSideColor() { return sideColor; }

    //Пералічэнне для вызначэння ўзроўню кампутарнага суперніка
    public enum Level {
        EASY(1),
        MEDIUM(3),
        HARD(5),
        VERY_HARD(9);

        private int depth;

        Level(int depth) {
            this.depth = depth;
        }

        public int getLevelCode() { return depth; }
    }
}
