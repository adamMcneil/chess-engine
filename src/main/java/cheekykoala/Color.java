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
}

