package by.bechess.app;

import java.awt.*;

public class Move {
    protected Point fromPoint,
                    toPoint;
    protected Piece piece,
                    pieceEaten;
    protected Board board;

    public Move(){}

    public Move(int fromX, int fromY, int toX, int toY, Board board) {
        fromPoint = new Point(fromX, fromY);
        toPoint = new Point(toX, toY);
        this.board = board;
        piece = board.findPiece(fromPoint);
        pieceEaten = board.findPiece(toPoint);
    }

    public void make() {
        piece.move(board.getCell(toPoint.x, toPoint.y));
        piece.movesCount++;
        if (pieceEaten != null) {
            pieceEaten.remove();
        }
    }

    public void revoke(){
        piece.move(board.getCell(fromPoint.x, fromPoint.y));
        if (--piece.movesCount < 0) { piece.movesCount = 0; }
        if (pieceEaten != null) {
            pieceEaten.setCell(board.getCell(toPoint.x, toPoint.y));
        }
    }

    public Piece getPiece() { return piece; }

    public Piece getPieceEaten() { return pieceEaten; }

    public Board getBoard() { return board; }

    public boolean isPossible() {
        boolean toRet = false;

        if ((piece != null) && (piece.isPossibleMove(board.getCell(toPoint.x, toPoint.y)))) {
            //Павяраем ці вольны шлях праз шлях
            if (board.isFreePath(piece.cell, board.getCell(toPoint.x, toPoint.y))) {
                //Робім ход, правяраем пазіцыю для пэўнага колеру, адмяняем
                make();
                if (board.position.isValid(piece.getColor())) {
                    toRet = true;
                }
                revoke();
            }
        }
        return toRet;
    }
}
