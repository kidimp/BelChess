package by.bechess.app;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private final int BOARD_SIZE = 9,
              BOARD_SIZE_IN_ARRAY = BOARD_SIZE -1,
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

    //Намаляваць дошку, фігуркі і подпісы
    public void draw(){
        for (int y = -1; y < BOARD_SIZE; y++) {
            int rotatedY = isBoardRotated ? y : BOARD_SIZE_IN_ARRAY -y;
            System.out.print((y > -1) ? rotatedY +1 + "  " : "   ");           //подпіс па вертыкалі
            for (int x = 0; x < BOARD_SIZE; x++) {
                //подпіс па гарызанталі
                if (y == -1) {
                    System.out.print((char)(65 +x));
                }
                //Дошка і фігуркі
                else {
                    cells[rotatedY][x].draw();                          //Перагорнутае адлюстраванне па-вертыкалі
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
        int x = fromCell.getX(), y = fromCell.getY();
        int toX = toCell.getX(), toY = toCell.getY();

        int diffX = toX - x;
        int diffY = toY - y;
        if (diffX != 0) { diffX /= Math.abs(diffX); }
        if (diffY != 0) { diffY /= Math.abs(diffY); }


        do {
            if (x != toX) { x += diffX; }
            if (y != toY) { y += diffY; }

            Cell cell = getCell(x, y);
            if ((cell.getPiece() != null)
                    || ((cell.getCellType() == Cell.Type.THRONE) && (fromCell.getPiece().isCanTakeThrone() == false))){
                return false;
            }
        } while ((x != toX) || (y != toY));

        return true;
    }

    //Атрымаць колькасць фігур, якія могуць пахадзіць у патрэбную ячэйку
    public int getAmountOfAttackers(Cell cell, Color sideColor) {
        int amountOfAttackers = 0;

        for (Piece piece : pieces) {
            if (piece.getColor() != sideColor) {
                if (piece.isPossibleMove(cell)) {
                    //Для ўсіх фігур акрамя каня павяраем ці вольны шлях
                    if ((isFreePath(piece.cell, cell)) || (piece.getClass() == Knight.class)) {
                        amountOfAttackers++;
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

    //Атрымаць усе хады для пэўнага колеру
    public ArrayList<Move> getAllPossibleMoves(Color colorToFind) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece : pieces) {
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

                /*
                //Князь
                if (piece.getClass() == King.class) {
                    if (piece.getColor() == Color.WHITE) {
                        whiteKing = (King) piece;
                    }
                    else{
                        blackKing = (King) piece;
                    }
                }
                //Княжыч
                if (piece.getClass() == Prince.class) {
                    if (piece.getColor() == Color.WHITE) {
                        whitePrince = (Prince) piece;
                    }
                    else{
                        blackPrince = (Prince) piece;
                    }
                }
                //Гарматы
                if (piece.getClass() == Bishop.class) {
                    if (piece.getColor() == Color.WHITE) {
                        whiteBishops[amountOfBishops[0]++] = (Bishop) piece;
                    }
                    else{
                        blackBishops[amountOfBishops[1]++] = (Bishop) piece;
                    }
                }*/
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
                    if (whiteKing.cell.getCellType() == Cell.Type.REGULAR) {
                        return false;
                    }
                }
                //Праверка чорнага Князя, каб быў на Троне або ў Палацы
                else {
                    if (blackKing.cell.getCellType() == Cell.Type.REGULAR) {
                        return false;
                    }
                }
            }

            //Праверка разнапольных гарматаў
            if (whiteBishops[0].cell.getCellColor() == whiteBishops[1].cell.getCellColor()) {
                return false;
            }
            if (blackBishops[0].cell.getCellColor() == blackBishops[1].cell.getCellColor()) {
                return false;
            }

            //Праверка на Мат


            //Праверка на абарону Трона
            if (getAmountOfAttackers(cells[THRONE_POSITION][THRONE_POSITION], sideColor) > 0) {
                return false;
            }

            return true;
        }
    }
}
