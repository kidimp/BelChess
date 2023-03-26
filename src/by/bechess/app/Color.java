package by.bechess.app;

public enum Color {
    WHITE(1),
    BLACK(-1);

    private int sign;

    Color(int sign) {
        this.sign = sign;
    }

    public int getSign() { return sign; }

    public static Color getReversedColor(Color color) {
        return (color == WHITE) ? BLACK : WHITE;
    }
}