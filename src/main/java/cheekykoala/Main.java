package cheekykoala;

import java.util.List;
import java.util.Scanner;

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
        Move bestMove = moveMinimax(board, 4, board.colorToMove);
        System.out.println("Board Evaluation:" + board.getEval());
        return "bestmove " + bestMove;
    }

    public static Move moveMinimax(Board board, int depth, Color color) {
        double bestMoveValue;
        boolean isMaxPlayer;
        if (color == Color.w) {
            bestMoveValue = Double.NEGATIVE_INFINITY;
            isMaxPlayer = false;
        } else {
            bestMoveValue = Double.POSITIVE_INFINITY;
            isMaxPlayer = true;
        }
        Board child;
        List<Move> moves = board.getAllMoves(color);
        Move bestMove = moves.get(0);
        for (Move move : moves) {
            child = board.getChild(move);
            double mx = minimax(child, depth - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                    isMaxPlayer);
            if (color == Color.w) {
                if (mx > bestMoveValue) {
                    bestMoveValue = mx;
                    bestMove = move;
                }
            } else {
                if (mx < bestMoveValue) {
                    bestMoveValue = mx;
                    bestMove = move;
                }
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
        if (isWhite) {
            return moveList.parallelStream()
                    .mapToDouble(move -> minimax(board.getChild(move), depth - 1, alpha, beta, false))
                    .reduce(Double.NEGATIVE_INFINITY, Math::max);
        } else {
            return moveList.parallelStream()
                    .mapToDouble(move -> minimax(board.getChild(move), depth - 1, alpha, beta, true))
                    .reduce(Double.POSITIVE_INFINITY, Math::min);
        }
    }

    public static double checkmateEval(Color color) {
        if (color == Color.w)
            return Double.NEGATIVE_INFINITY;
        else
            return Double.POSITIVE_INFINITY;
    }

}