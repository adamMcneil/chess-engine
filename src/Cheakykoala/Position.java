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

    public boolean isOnBoard() {
        if (position[0] < 8 && position[0] > -1 && position[1] < 8 && position[1] > -1)
            return true;
        return false;
    }

    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
