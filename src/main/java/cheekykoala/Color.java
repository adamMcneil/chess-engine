package cheekykoala;

public enum Color {
    b,
    w,
    g;

    public Color getOppositeColor() {
        if (this == Color.w) {
            return Color.b;
        }
        return Color.w;
    }

    public static Color getColorFromFen(String fen) {
        if (fen.contains("b")) {
            return Color.b;
        }
        if (fen.contains("w")) {
            return Color.w;
        }
        throw new IllegalArgumentException("Invalid fen: " + fen);
    }
}

