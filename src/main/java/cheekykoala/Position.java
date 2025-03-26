package cheekykoala;

public class Position {
    int[] position;

    public Position(int x, int y) {
        position = new int[] { x, y };
    }

    public int getX() {
        return position[0];
    }

    public int getY() {
        return position[1];
    }

    public void setX(int x) {
        position[0] = x;
    }

    public void setY(int y) {
        position[1] = y;
    }

    public boolean comparePositions(Position x) {
        return (x.getX() == this.getX() && x.getY() == this.getY());
    }

    public boolean isOnBoard() {
        return (position[0] < 8 && position[0] > -1 && position[1] < 8 && position[1] > -1);
    }

    public String toString() {
        return intToChar(getX()) + (8 - getY());
    }

    public static String intToChar(int num) {
        return switch (num) {
            case 0-> "a" ;
            case 1-> "b" ;
            case 2-> "c" ;
            case 3-> "d" ;
            case 4-> "e" ;
            case 5-> "f" ;
            case 6-> "g" ;
            case 7-> "h" ;
            default -> null;
        };
    }
    public String convertPosition() {
        return new StringBuilder().append((char) (position[0] + 97)).append((8 - position[1])).toString();
    }
}
