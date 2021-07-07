package Cheakykoala;

public class Position {
    int[] position;

    public Position(int x, int y) {
        position = new int[]{x, y};
    }

    public int getX() {
        return position[0];
    }

    public int getY() {
        return position[1];
    }

    public void setX(int x){
        position [0] = x;
    }

    public void setY(int y){
        position [1] = y;
    }

    public void isEqualTo(Position p){
        setX(p.getX());
        setY(p.getY());
    }

    public boolean comparePositions(Position x) {
        return (x.getX() == this.getX() && x.getY() == this.getY());
    }

    public boolean isOnBoard() {
        if (position[0] < 8 && position[0] > -1 && position[1] < 8 && position[1] > -1)
            return true;
        return false;
    }

    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
