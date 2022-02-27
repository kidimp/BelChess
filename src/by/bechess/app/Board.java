package by.bechess.app;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private final int BOARD_SIZE = 9,
              BOARD_SIZE_IN_ARRAY = BOARD_SIZE -1;
    private Cell[][] cells;
    private ArrayList<Piece> pieces;
    private boolean isBoardRotated;

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
    }

    public Piece findPiece(Point point) {
        return cells[point.y][point.x].getPiece();
    }

    public Cell getCell(int x, int y) {
        return cells[y][x];
    }
}
