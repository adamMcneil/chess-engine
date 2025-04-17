package cheekykoala;

import cheekykoala.pieces.Piece;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static cheekykoala.Main.depthSearch;

public class Utils {


    public static class JsonTestEntry {
        public int depth;
        public int nodes;
        public String fen;
    }

    public static class TestOutput {
        public double min;
        public double average;
        public String fen;

        public TestOutput(String fen, double average, double min) {
            this.fen = fen;
            this.min = min;
            this.average = average;
        }
    }

    public static List<JsonTestEntry> getTestCases() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("test-fens.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource file test-fens.json not found");
        }
        return objectMapper.readValue(inputStream, new TypeReference<>() {
        });
    }

    public static void outputTestCases(List<TestOutput> results) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("benchmark_results.json"), results);
        } catch (IOException e) {
            System.out.println("Failed to write JSON results: " + e.getMessage());
        }
    }

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

            int first = (convertLetter(beginning.charAt(0)) - 8 - Character.getNumericValue(beginning.charAt(1)));
            int last = (convertLetter(end.charAt(0)) - 8 - Character.getNumericValue(end.charAt(1)));
            if (new Move(first, last).isMoveLegal(board, color)) {
                board.doMove(new Move(first, last));
                System.out.println("mediocre move");
                return;
            }
            System.out.println("GO BACK TO CHECKERS!");
        }
    }

    public static void playRandom(Board board, Color color) {
        ArrayList<Move> moves = new ArrayList<>();
        int position = 0;
        for (Piece piece : board.getBoard()) {
            if (piece.getColor() == color) {
                moves.addAll(piece.getMoves(board, position));
            }
            position++;
        }
        int y = (int) (Math.random() * moves.size());
        board.doMove(moves.get(y));
    }

    public static void playGame(int depth) throws InterruptedException {
        Board board = new Board();
        for (int i = 0; i < depth; i++) {
            board.printBoard();
            System.out.println();
            playRandom(board, Color.w);
            board.printBoard();
            System.out.println();
            Main.depthSearch(board, 3, Color.b);
        }
    }

}
