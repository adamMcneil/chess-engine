package cheekykoala;

public class Position {

    public static boolean isOnBoard(int position) {
        return (position < 64 && position > -1 );
    }

    public static boolean isSameRow(int p1, int p2) {
        return getRow(p1) == getRow(p2);
    }

    public static boolean isSameColumn(int p1, int p2) {
        return getColumn(p1) == getColumn(p2);
    }

    public static boolean isDiagonal(int p1, int p2) {
        int rowDiff = Math.abs(getRow(p1) - getRow(p2));
        int colDiff = Math.abs(getColumn(p1) - getColumn(p2));
        return rowDiff == colDiff;
    }

    public static int getColumn(int position) {
        return position % 8;
    }

    public static int getRow(int position) {
        return position / 8;
    }
    public static String toString(int position) {
        return rowToLetter(getColumn(position)) + (8 - getRow(position));
    }

    public static String rowToLetter(int column) {
        return switch (column) {
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
}
