package Cheakykoala;

public enum Color {
    b,
    w,
    g;

    public Color getOppositeColor(){
        if (Color.w == w)
            return Color.b;
        return Color.w;
    }
}
