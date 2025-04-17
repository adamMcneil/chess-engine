package cheekykoala;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static cheekykoala.Utils.getTestCases;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void testMoveGenerationFromJson() throws IOException {
        List<Utils.JsonTestEntry> testEntries = getTestCases();
        for (Utils.JsonTestEntry entry : testEntries) {
            Board board = new Board();
            board.importBoard(entry.fen);
            Color color;
            if (entry.fen.contains("w")) {
                color = Color.w;
            } else {
                color = Color.b;
            }
            int totalMoves = board.countOnlyNodes(entry.depth, color);
            assertEquals(entry.nodes, totalMoves, "Failed for FEN: " + entry.fen);
            System.out.println(entry.fen + " : depth " + entry.depth);
        }
    }

    static class CheckEntry {
        public boolean white;
        public boolean black;
        public String fen;
    }

    @Test
    void testCheckInput() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("check-fens.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource file test-fens.json not found");
        }
        List<CheckEntry> testEntries = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        for (CheckEntry entry : testEntries) {
            Board board = new Board();
            board.importBoard(entry.fen);
            System.out.println(entry.fen);
            assertEquals(board.isColorInCheck(Color.w), entry.white);
            assertEquals(board.isColorInCheck(Color.b), entry.black);
        }
    }

    @Test
    void testMate() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mate-fens.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource file test-fens.json not found");
        }
        List<CheckEntry> testEntries = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        for (CheckEntry entry : testEntries) {
            Board board = new Board();
            board.importBoard(entry.fen);
            System.out.println(entry.fen);
            System.out.println(entry.white);
            assertEquals(entry.white, board.isColorInMate(Color.w));
            assertEquals(board.isColorInMate(Color.b), entry.black);
        }
    }

    @Test
    public void testStartPosition() {
        Board board = new Board();
        int[] expectedNodes = {20, 400, 8902, 197281, 4865609};

        for (int depth = 1; depth <= expectedNodes.length; depth++) {
            MoveCounter counter = board.countNodes(depth, Color.w);
            System.out.println("Depth " + depth + ": " + counter);
            assertEquals(expectedNodes[depth - 1], counter.nodes);
        }
    }

    @Test
    public void testPerformanceStartPosition1() {
        Board board = new Board();
        assertEquals(4865609, board.countOnlyNodes(5, Color.w));
    }

    @Test
    public void testDivide() {
        int depth = 4;
        List<String> moves = List.of("a2b3", "e6f5", "d2d4");
        Color color = Color.w;
        if (moves.size() % 2 == 1) {
            color = Color.b;
        }
        Board board = new Board("8/8/4k3/8/2p5/8/B2P2K1/8 w - - 0 1");
        for (String move : moves) {
            board.doMove(Move.moveFromString(move, board));
        }
        board.printBoard();
        board.printBoard(32);
        System.out.println("Number of moves: " + board.getAllMoves(color).size());
        System.out.println("Number of nodes: " + board.countOnlyNodes(depth - moves.size(), color));
        board.divideOne(color, depth - moves.size() - 1);

    }

}
