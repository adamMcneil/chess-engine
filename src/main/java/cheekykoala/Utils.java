package cheekykoala;

import cheekykoala.pieces.Piece;

import java.util.ArrayList;
import java.util.Scanner;

import static cheekykoala.Main.moveMinimax;

public class Utils {

    public static int convertLetter(char x) {
        return (x - 97);
    }

    public static char convertNumber(int x) {
        return ((char) (x + 97));
    }

    public static void playHuman(Board board, Color color) {
        Scanner input = new Scanner(System.in);
        boolean wasLegal = false;
        while (!wasLegal) {
            System.out.print("Piece you want to move : ");
            String beginning = input.nextLine();
            System.out.println();
            System.out.print("Where you would so like to move your pieceage to sir : ");
            String end = input.nextLine();
            System.out.println();
            System.out.println(convertLetter(beginning.charAt(0)));
            System.out.println(8 - Character.getNumericValue(beginning.charAt(1)));

            Position first = new Position(convertLetter(beginning.charAt(0)),
                    8 - Character.getNumericValue(beginning.charAt(1)));
            Position last = new Position(convertLetter(end.charAt(0)), 8 - Character.getNumericValue(end.charAt(1)));
            if (new Move(first, last).isMoveLegal(board, color)) {
                wasLegal = true;
                board.getPieceAt(first).move(board, new Move(first, last));
                System.out.println("mediocre move");
                return;
            }
            System.out.println("GO BACK TO CHECKERS!");
        }
    }

    public static void playRandom(Board board, Color color) {
        ArrayList<Move> moves = new ArrayList();
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == color) {
                    for (Move m : p.getMoves(board)) {
                        moves.add(m);
                    }
                }
            }
        }
        int y = (int) (Math.random() * moves.size());
        board.getPieceAt(moves.get(y).getBeginning()).move(board, moves.get(y));
    }

    public static void playGame(int depth) throws InterruptedException {
        Board board = new Board();
        for (int i = 0; i < depth; i++) {
            board.printBoard();
            System.out.println();
            playRandom(board, Color.w);
            board.printBoard();
            System.out.println();
            moveMinimax(board, 3, Color.b);
        }
    }

}
