package cheekykoala;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        apiConnect(board);
    }

    public static void apiConnect(Board board) {
        Scanner consoleInput = new Scanner(System.in);
        while (true) {
            String input = consoleInput.nextLine();
            System.out.println(input);
            if (input.contains("go")) {
                System.out.println(onGo(board));
            } else if (input.equals("uci")) {
                System.out.println("uciok");
            } else if (input.contains("isready")) {
                System.out.println("readyok");
            } else if (input.contains("position")) {
                onPosition(board, input);
                board.printBoardSimple();
            } else if (input.equals("quit")) {
                break;
            }
        }
    }

    public static void onPosition(Board board, String UCIPosition) {
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
        for (int i = startMoves; i < UCIStringArray.length; i++) {
            Move move = Move.moveFromString(UCIStringArray[i], board);
            board.doMove(move);
        }
    }

    public static String onGo(Board board) {
        Move bestMove = iterativeDeepening(board, 1000);
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

    public static Move moveMinimax(Board board, int depth, Color color) {
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

    public static Move moveMinimax(Board board, int depth, Color color, long timeLeft) {
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

    public static Move iterativeDeepening(Board board, long timeLimitMillis) {
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
                Move currentMove = moveMinimax(board, depth, board.colorToMove, remainingTime);
                if (System.currentTimeMillis() - startTime < softLimit) {
                    bestMove = currentMove;
                    System.out.println("Completed depth " + depth + " with move " + bestMove + ": " + ((System.currentTimeMillis() - depthTime) /1000.) + " sec");
                }
            } catch (Exception e) {
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
        List<Move> moveList = board.getPseudoMoves(color);
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
        List<Move> moveList = board.getPseudoMoves(color);
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