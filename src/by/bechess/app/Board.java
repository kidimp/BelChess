package by.bechess.app;

import java.awt.*;
import java.util.ArrayList;

public class Board implements Cloneable{
    public static final int BOARD_SIZE = 9;
    private final int BOARD_SIZE_IN_ARRAY = BOARD_SIZE -1,
              THRONE_POSITION = 4;
    private Cell[][] cells;
    private ArrayList<Piece> pieces;
    private boolean isBoardRotated;
    public Position position;

    Board(Color playersColor){
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }

        isBoardRotated = (playersColor == Color.BLACK) ? true : false;
    }

    @Override
    public Board clone() {
        Board copyBoard;
        try {
            copyBoard = (Board) super.clone();
            //Кланаванне фігурак
            ArrayList<Piece> copyPieces = new ArrayList<>();
            for (Piece pieceToCopy : pieces) {
                copyPieces.add(pieceToCopy.clone());
            }
            pieces = copyPieces;
            //Ствараем пазіцыю
            position = new Position();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return copyBoard;
    }

    //Намаляваць дошку, фігуркі і подпісы
    public void draw(){
        for (int y = -1; y < BOARD_SIZE; y++) {
            int rotatedY = isBoardRotated ? y : BOARD_SIZE_IN_ARRAY -y;
            System.out.print((y > -1) ? rotatedY +1 + "  " : "   ");           //подпіс па вертыкалі
            for (int x = 0; x < BOARD_SIZE; x++) {
                //подпіс па гарызанталі
                int rotatedX = !isBoardRotated ? x : BOARD_SIZE_IN_ARRAY -x;
                if (y == -1) {
                    System.out.print((char)(65 +rotatedX));
                }
                //Дошка і фігуркі
                else {
                    cells[rotatedY][rotatedX].draw();                          //Перагорнутае адлюстраванне па-вертыкалі
                }
                System.out.print("  ");
            }
            System.out.println("");
        }
    }

    //Заданне стартавых пазіцый па натацыі Фарсайта-Найтгейтса
    public void setupPieces(String fenNotation){
        pieces = new ArrayList<>();

        char symbol;
        Color color;

        int xPos = 0, yPos = BOARD_SIZE_IN_ARRAY;                                 //Пачынаецца з 8-й вертыкалі, паколькі у Натацыі позірк з боку белых
        for (int i = 0; i < fenNotation.length(); i++) {
            symbol = fenNotation.charAt(i);

            //Фігуры
            color = ((symbol >= 'a') && (symbol <= 'z')) ? Color.BLACK : Color.WHITE;
            switch (String.valueOf(symbol).toLowerCase()) {
                case "r" -> { pieces.add(new Pawn(cells[yPos][xPos], color)); }
                case "v" -> { pieces.add(new Knight(cells[yPos][xPos], color)); }
                case "m" -> { pieces.add(new Bishop(cells[yPos][xPos], color)); }
                case "l" -> { pieces.add(new Rook(cells[yPos][xPos], color)); }
                case "g" -> { pieces.add(new Queen(cells[yPos][xPos], color)); }
                case "k" -> { pieces.add(new Prince(cells[yPos][xPos], color)); }
                case "a" -> { pieces.add(new King(cells[yPos][xPos], color)); }
            }

            //Пустыя клеткі або пераход на наступную клетку
            if ((symbol >= '1') && (symbol <= '9')) {
                xPos += Integer.parseInt(String.valueOf(symbol));
            }
            else {
                xPos++;
            }

            //Пераход на новы радок
            if (symbol == '/') {
                xPos = 0;
                yPos--;
            }
        }
        //Ствараем пазіцыю
        position = new Position();
    }

    public boolean isFreePath(Cell fromCell, Cell toCell) {
        Piece pieceOnCell = fromCell.getPiece();
        //Калі вершнік, то праход вольны
        if ((pieceOnCell != null) && (pieceOnCell.getClass() == Knight.class)) {
            return true;
        }

        int x = fromCell.getX(), y = fromCell.getY(),
            toX = toCell.getX(), toY = toCell.getY(),
            diffX = toX - x, diffY = toY - y;

        //Калі няма клетак між пачаткам і канцом - праход магчымы
        if ((Math.abs(diffX) <= 1) && (Math.abs(diffY) <= 1)) {
            return true;
        }

        //Знаходзім дэльту (на колькі клетак змяшчэнне)
        if (diffX != 0) { diffX /= Math.abs(diffX); }
        if (diffY != 0) { diffY /= Math.abs(diffY); }

        do {
            if (x != toX -diffX) { x += diffX; }
            if (y != toY -diffY) { y += diffY; }

            Cell cell = getCell(x, y);
            if ((cell.getPiece() != null)
                    || ((cell.getCellType() == Cell.Type.THRONE) && (fromCell.getPiece().isCanTakeThrone() == false))){
                return false;
            }
        } while ((x != toX -diffX) || (y != toY -diffY));

        return true;
    }

    //Атрымаць колькасць фігур, якія могуць пахадзіць у патрэбную ячэйку
    public int getAmountOfAttackers(Cell cell, Color sideColor) {
        int amountOfAttackers = 0;

        for (Piece piece : pieces) {
            if (piece.isOnBoard()) {
                if (piece.getColor() != sideColor) {
                    if (piece.isPossibleMove(cell)) {
                        //Для ўсіх фігур акрамя каня павяраем ці вольны шлях
                        if (isFreePath(piece.cell, cell)) {
                            amountOfAttackers++;
                        }
                    }
                }
            }
        }
        return amountOfAttackers;
    }

    public Piece findPiece(Point point) {
        return cells[point.y][point.x].getPiece();
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public ArrayList<Piece> getAllPieces() {return pieces; }

    //Атрымаць усе хады для пэўнага колеру
    public ArrayList<Move> getAllPossibleMoves(Color colorToFind) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece : pieces) {
            if (piece.isOnBoard()) {
                if (piece.getColor() == colorToFind) {
                    int fromX = piece.cell.getX(),
                        fromY = piece.cell.getY();
                    for (int y = 0; y < BOARD_SIZE; y++) {
                        for (int x = 0; x < BOARD_SIZE; x++) {
                            if (!((fromX == x) && (fromY == y))) {
                                Move move = new Move(fromX, fromY, x, y, this);
                                if (move.isPossible()) {
                                    moves.add(move);
                                }
                            }
                        }
                    }
                }
            }
        }

        return moves;
    }

    public class Position {
        King whiteKing, blackKing;
        Prince whitePrince, blackPrince;
        Bishop[] whiteBishops, blackBishops;

        Position() {
            whiteBishops = new Bishop[2];
            blackBishops = new Bishop[2];
            int[] amountOfBishops = {0, 0};

            for (Piece piece : pieces) {
                switch (piece.getColor()) {
                    case WHITE -> {
                        if (piece.getClass() == King.class) { whiteKing = (King) piece; }
                        if (piece.getClass() == Prince.class) { whitePrince = (Prince) piece; }
                        if (piece.getClass() == Bishop.class) { whiteBishops[amountOfBishops[0]++] = (Bishop) piece; }
                    }
                    case BLACK -> {
                        if (piece.getClass() == King.class) { blackKing = (King) piece; }
                        if (piece.getClass() == Prince.class) { blackPrince = (Prince) piece; }
                        if (piece.getClass() == Bishop.class) { blackBishops[amountOfBishops[1]++] = (Bishop) piece; }
                    }
                }
            }
        }

        public boolean isValid(Color sideColor) {
            //Праверка наяўнасці Князёў
            if ((whiteKing == null) || (blackKing == null)) {
                return false;
            }

            //Праверка валіднасці фігур на Троне
            Piece pieceOnThrone = cells[THRONE_POSITION][THRONE_POSITION].getPiece();
            if (pieceOnThrone != null) {
                //Праверка прысутнасці на Троне толькі Князя або Княжыча
                if (!((pieceOnThrone.getClass() == King.class) || (pieceOnThrone.getClass() == Prince.class))) {
                    return false;
                }

                //Праверка белага Князя, каб быў на Троне або ў Палацы
                if (pieceOnThrone.getColor() == Color.WHITE) {
                    if (whiteKing.isOnBoard()) {
                        if (whiteKing.cell.getCellType() == Cell.Type.REGULAR) {
                            return false;
                        }
                    }
                }
                //Праверка чорнага Князя, каб быў на Троне або ў Палацы
                else {
                    if (blackKing.isOnBoard()) {
                        if (blackKing.cell.getCellType() == Cell.Type.REGULAR) {
                            return false;
                        }
                    }
                }

                //Праверка на абарону Трона (нельга хадзіць на трон пад атакай і трэба абараняцца, калі атакуюць на троне)
                if ((pieceOnThrone.color == sideColor)
                        && (getAmountOfAttackers(cells[THRONE_POSITION][THRONE_POSITION], sideColor) > 0)) {
                    return false;
                }
            }

            //Праверка разнапольных гарматаў
            if ((whiteBishops[0].isOnBoard()) && (whiteBishops[1].isOnBoard())) {
                if (whiteBishops[0].cell.getCellColor() == whiteBishops[1].cell.getCellColor()) {
                    return false;
                }
            }
            if ((blackBishops[0].isOnBoard()) && (blackBishops[1].isOnBoard())) {
                if (blackBishops[0].cell.getCellColor() == blackBishops[1].cell.getCellColor()) {
                    return false;
                }
            }

            //Праверка на напад на Князя (князь не можа хадзіць на бітыя палі і іншыя фігуры не могуць станавіцца на палі, куды б'е князь)
            if (((whiteKing.isOnBoard()) && (getAmountOfAttackers(whiteKing.cell, sideColor) > 0))
                || ((blackKing.isOnBoard()) && (getAmountOfAttackers(blackKing.cell, sideColor) > 0))){
                return false;
            }

            return true;
        }
    }
}
