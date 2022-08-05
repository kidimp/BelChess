package by.bechess.app;

public enum Color {
    WHITE,
    BLACK;

    public static Color getReversedColor(Color color) {
        return (color == WHITE) ? BLACK : WHITE;
    }
}