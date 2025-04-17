package cheekykoala;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class Main {
    static String ALGORITHM = "iterative";
    static long THINK_TIME = 5000;
    static int DEPTH = 5;
    static int TRIALS = 3;

    public static void readInputs(String[] args) {

        if (args.length > 0) {
            try {
                ALGORITHM = args[0];
                THINK_TIME = Long.parseLong(args[1]);
                DEPTH = Integer.parseInt(args[2]);
                TRIALS = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid time limit argument, using default.");
            }
        }
    }

    public static void main(String[] args) {
        readInputs(args);
        apiConnect();
    }

    public static void apiConnect() {
        Board board = new Board();
        Scanner consoleInput = new Scanner(System.in);
        Color color = Color.w;
        while (true) {
            String input = consoleInput.nextLine();
            System.out.println(input);
            if (input.contains("go")) {
                System.out.println(onGo(board, color));
            } else if (input.equals("uci")) {
                System.out.println("uciok");
            } else if (input.contains("isready")) {
                System.out.println("readyok");
            } else if (input.contains("position")) {
                color = onPosition(board, input);
                board.printBoardSimple();
            } else if (input.equals("test")) {
                test();
            } else if (input.equals("quit")) {
                break;
            }
        }
    }

    public static void test() {
        List<Utils.JsonTestEntry> testEntries = List.of();
        try {
            testEntries = Utils.getTestCases();
        }  catch (Exception e) {
            System.out.println("Could not get test cases");
        }
        List<Utils.TestOutput> results = new ArrayList<>();
        Board board = new Board();
        for (Utils.JsonTestEntry entry : testEntries) {
            board.importBoard(entry.fen);
            board.printBoard();
            Color color = Color.getColorFromFen(entry.fen);
            List<Long> runTimes = new ArrayList<>();
            for (int i = 0; i < TRIALS; i++) {
                long runTime = reportDepthSearch(board, DEPTH, color);
                runTimes.add(runTime);
            }
            Optional<Long> shorteshRunTime = runTimes.stream().min(Long::compareTo);
            double average = runTimes.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0) / 1000.0;

            double min = runTimes.stream()
                    .mapToLong(Long::longValue)
                    .min()
                    .orElse(0) / 1000.0;

            results.add(new Utils.TestOutput(entry.fen, average, min));
            System.out.println("Completed depth " + DEPTH + ": " + ((shorteshRunTime.get()) / 1000.0) + " sec");
        }
        Utils.outputTestCases(results);
    }

    public static Color onPosition(Board board, String UCIPosition) {
        int startMoves = 3;
        String[] UCIStringArray = UCIPosition.split(" ");
        if (UCIStringArray[1].equals("startpos")) {
            board.importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        } else {
            int i = 2;
            StringBuilder fenString = new StringBuilder(UCIStringArray[1]);
            while (!(UCIStringArray[i].equals("moves"))) {
                fenString.append(" ").append(UCIStringArray[i]);
                i++;
            }
            fenString.toString().stripLeading();
            // position k7/5P2/8/8/8/8/8/K7 w - - 0 1 moves f7f8
            board.importBoard(fenString.toString());
            startMoves = i + 1;
        }
        int numMoves = 0;
        for (int i = startMoves; i < UCIStringArray.length; i++) {
            Move move = Move.moveFromString(UCIStringArray[i], board);
            board.doMove(move);
            numMoves++;
        }
        if (numMoves % 2 == 0) {
            return Color.w;
        }
        return Color.b;
    }

    public static String onGo(Board board, Color color) {
        Move bestMove;
        if (ALGORITHM.equals("depth")) {
            bestMove = depthSearch(board, DEPTH, color);
        } else if (ALGORITHM.equals("iterative")) {
            bestMove = iterativeDeepening(board, color, THINK_TIME);
        } else {
            throw new IllegalArgumentException("Invalid algorithm, using default.");
        }
        System.out.println("Board Evaluation:" + board.getEval());
        return "bestmove " + bestMove;
    }

    private static class MoveEntry {
        public Move move;
        public double score;

        MoveEntry(Move move, Board board, int depth, boolean isWhite) {
            this.move = move;
            this.score = minimax(board.getChild(move), depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, isWhite);
        }

        MoveEntry(Move move, Board board, int depth, boolean isWhite, long timeLeft) throws TimeoutException {
            this.move = move;
            this.score = minimax(board.getChild(move), depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, isWhite, timeLeft);
        }
    }

    public static long reportDepthSearch(Board board, int depth, Color color) {
        long startTime = System.currentTimeMillis();
        Move bestMove = depthSearch(board, depth, color);
        long depthTime = System.currentTimeMillis() - startTime;
        System.out.println("Completed depth " + depth + " with move " + bestMove + ": " + (depthTime / 1000.0) + " sec");
        return depthTime;
    }

    public static Move depthSearch(Board board, int depth, Color color) {
        boolean isMaxPlayer;
        isMaxPlayer = color != Color.w;
        List<Move> moves = board.getAllMoves(color);
        Move bestMove = moves.get(0);
        Optional<MoveEntry> moveEntry;
        if (isMaxPlayer) {
            moveEntry = moves.parallelStream()
                    .map(move -> new MoveEntry(move, board, depth - 1, true))
                    .max((a, b) -> Double.compare(b.score, a.score));
        } else {
            moveEntry = moves.parallelStream()
                    .map(move -> new MoveEntry(move, board, depth - 1, false))
                    .min((a, b) -> Double.compare(b.score, a.score));
        }
        if (moveEntry.isPresent()) {
            bestMove = moveEntry.get().move;
        }
        return bestMove;
    }

    public static Move depthSearch(Board board, int depth, Color color, long timeLeft) {
        boolean isMaxPlayer;
        isMaxPlayer = color != Color.w;
        List<Move> moves = board.getAllMoves(color);
        Move bestMove = moves.get(0);
        Optional<MoveEntry> moveEntry;
        if (isMaxPlayer) {
            moveEntry = moves.parallelStream()
                    .map(move -> {
                        try {
                            return new MoveEntry(move, board, depth - 1, true, timeLeft);
                        } catch (TimeoutException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .max((a, b) -> Double.compare(b.score, a.score));
        } else {
            moveEntry = moves.parallelStream()
                    .map(move -> {
                        try {
                            return new MoveEntry(move, board, depth - 1, false, timeLeft);
                        } catch (TimeoutException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .min((a, b) -> Double.compare(b.score, a.score));
        }
        if (moveEntry.isPresent()) {
            bestMove = moveEntry.get().move;
        }
        return bestMove;
    }

    public static Move iterativeDeepening(Board board, Color color, long timeLimitMillis) {
        Move bestMove = null;
        long startTime = System.currentTimeMillis();
        long softLimit = timeLimitMillis * 9 / 10; // Use 90% of time

        for (int depth = 1; ; depth++) {
            if (System.currentTimeMillis() - startTime >= softLimit) {
                break;
            }
            long depthTime = System.currentTimeMillis();
            long remainingTime = softLimit - (System.currentTimeMillis() - startTime);
            try {
                Move currentMove = depthSearch(board, depth, color, remainingTime);
                if (System.currentTimeMillis() - startTime < softLimit) {
                    bestMove = currentMove;
                    System.out.println("Completed depth " + depth + " with move " + bestMove + ": " + ((System.currentTimeMillis() - depthTime) / 1000.) + " sec");
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        return bestMove;
    }

    public static double minimax(Board board, int depth, double alpha, double beta, boolean isWhite) {
        if (depth == 0) {
            return board.getEval();
        }
        Color color = isWhite ? Color.w : Color.b;
        List<Move> moveList = board.getMoves(color, move -> true);
        if (moveList.isEmpty()) {
            return checkmateEval(color);
        }
        double bestMoveValue;
        if (isWhite) {
            bestMoveValue = Double.NEGATIVE_INFINITY;
            for (Move move : moveList) {
                bestMoveValue = Math.max(bestMoveValue, minimax(board.getChild(move), depth - 1, alpha, beta, false));
                if (bestMoveValue >= beta) {
                    break;
                }
                alpha = Math.max(alpha, bestMoveValue);
            }
        } else {
            bestMoveValue = Double.POSITIVE_INFINITY;
            for (Move move : moveList) {
                bestMoveValue = Math.min(bestMoveValue, minimax(board.getChild(move), depth - 1, alpha, beta, true));
                if (bestMoveValue <= alpha) {
                    break;
                }
                beta = Math.min(beta, bestMoveValue);
            }
        }
        return bestMoveValue;
    }

    public static double minimax(Board board, int depth, double alpha, double beta, boolean isWhite, long timeLeft) throws TimeoutException {
        long startTime = System.currentTimeMillis();
        if (timeLeft <= 0) {
            throw new TimeoutException();
        }
        if (depth == 0) {
            return board.getEval();
        }
        Color color = isWhite ? Color.w : Color.b;
        List<Move> moveList = board.getMoves(color, move -> true);
        if (moveList.isEmpty()) {
            return checkmateEval(color);
        }
        double bestMoveValue;
        if (isWhite) {
            bestMoveValue = Double.NEGATIVE_INFINITY;
            for (Move move : moveList) {
                bestMoveValue = Math.max(bestMoveValue, minimax(board.getChild(move), depth - 1, alpha, beta, false, timeLeft - (System.currentTimeMillis() - startTime)));
                if (bestMoveValue >= beta) {
                    break;
                }
                alpha = Math.max(alpha, bestMoveValue);
            }
        } else {
            bestMoveValue = Double.POSITIVE_INFINITY;
            for (Move move : moveList) {
                bestMoveValue = Math.min(bestMoveValue, minimax(board.getChild(move), depth - 1, alpha, beta, true, timeLeft - (System.currentTimeMillis() - startTime)));
                if (bestMoveValue <= alpha) {
                    break;
                }
                beta = Math.min(beta, bestMoveValue);
            }
        }
        return bestMoveValue;
    }

    public static double checkmateEval(Color color) {
        if (color == Color.w)
            return Double.NEGATIVE_INFINITY;
        else
            return Double.POSITIVE_INFINITY;
    }

}
