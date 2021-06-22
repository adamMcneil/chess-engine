package Cheakykoala;

public class Position {
    int[] position;

    public Position(int x, int y) {
        position = new int[]{x, y};
    }
    public int getX(){
        return position [0];
    }
    public int getY(){
        return position [1];
    }

    public String toString(){
        return "x: " + getX() + ", y: " + getY();
    }
}
