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
    }

    public void make() {
        piece.cell.setPiece(null);
        piece.setCell(board.getCell(toPoint.x, toPoint.y));
        if (pieceEaten != null) {
            pieceEaten.remove();
        }
    }

    public void revoke(){
        piece.cell.setPiece(null);
        piece.setCell(board.getCell(fromPoint.x, fromPoint.y));
        if (pieceEaten != null) {
            pieceEaten.setCell(board.getCell(toPoint.x, toPoint.y));
        }
    }

    public Piece getPiece() { return piece; }

    public boolean isPossible() {
        boolean toRet = false;

        if ((piece != null) && (piece.isPossibleMove(board.getCell(toPoint.x, toPoint.y)))) {
            //Для ўсіх фігур акрамя каня павяраем ці вольны шлях
            if ((piece.getClass() == Knight.class) || (board.isFreePath(piece.cell, board.getCell(toPoint.x, toPoint.y)))) {
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
