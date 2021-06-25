package Cheakykoala;

public enum Color {
    b,
    w,
    g;

    public Color getOppositeColor(){
        if (Color.w == w)
            return Color.b;
        if (Color.b == b) {
            return Color.w;
        }
        return Color.g;
    }
}
