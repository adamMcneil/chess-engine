package cheekykoala;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class InputTest {

    static class JsonTestEntry {
        public int depth;
        public int nodes;
        public String fen;
    }

    @Test
    void testMoveGenerationFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-fens.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource file test-fens.json not found");
        }
        List<JsonTestEntry> testEntries = objectMapper.readValue(inputStream, new TypeReference<>() {});

        for (JsonTestEntry entry : testEntries) {
            Board board = new Board();
            board.importBoard(entry.fen);
            Color color;
            if (entry.fen.contains("w")) {
                color = Color.w;
            } else {
                color = Color.b;
            }
            int totalMoves = board.countNodes(entry.depth, color);
            assertEquals(entry.nodes, totalMoves, "Failed for FEN: " + entry.fen);
            System.out.println(entry.fen + " : depth " + entry.depth);
        }
    }

    @Test
    public void testStartPosition() {
        Board board = new Board();
        assertEquals(20, board.countNodes(1, Color.w));
        assertEquals(400, board.countNodes(2, Color.w));
        assertEquals(8902, board.countNodes(3, Color.w));
        assertEquals(197281, board.countNodes(4, Color.w));
        // assertEquals(4865609, board.countNodes(5, Color.w));
    }

    @Test
    public void testPerformanceStartPosition() {
        Board board = new Board();
        assertEquals(4865609, board.countNodes(5, Color.w));
    }
}
