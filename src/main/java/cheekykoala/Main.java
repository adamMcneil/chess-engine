package cheekykoala;

import cheekykoala.pieces.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static int index = 0;
    public static Color minimaxColor = Color.w;
    public static long startTime = System.currentTimeMillis();
    public static boolean timeout = false;
    public static int TIMEOUT_TIME = 5000;

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
                board.printBoard();
                System.out.println(onGo(board));
            } else if (input.equals("uci")) {
                System.out.println("uciok");
            } else if (input.contains("isready")) {
                System.out.println("readyok");
            } else if (input.contains("position")) {
                onPosition(board, input);
            } else if (input.equals("quit")) {
                break;
            }
        }
    }

    public static void onPosition(Board board, String UCIPosition) {
        int startMoves = 3;
        index = 0;
        String[] UCIStringArray = UCIPosition.split(" ");
        if (UCIStringArray[1].equals("startpos")) {
            board.importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
            board.setBoardEval(board.recomputeBoardEval());
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
            board.setBoardEval(board.recomputeBoardEval());
            board.printBoard();
            startMoves = i + 1;
        }
        for (int i = startMoves; i < UCIStringArray.length; i++) {
            index++;
            Move move;
            Position first = new Position(charToInt(UCIStringArray[i].charAt(0)),
                    8 - Character.getNumericValue(UCIStringArray[i].charAt(1)));
            Position second = new Position(charToInt(UCIStringArray[i].charAt(2)),
                    8 - Character.getNumericValue(UCIStringArray[i].charAt(3)));

            if (UCIStringArray[i].length() == 5) {
                char letter = UCIStringArray[i].charAt(4);
                move = new PromotionMove(first, second, makePiece(second, letter));

            } else {
                move = new Move(first, second);
            }
            board.getPieceAt(first).move(board, move);
            if (index % 2 == 0) {
                minimaxColor = Color.w;
            } else {
                minimaxColor = Color.b;
            }
        }
    }

    public static String onGo(Board board) {
        timeout = false;
        int INITIAL_DEPTH = 3;
        int CURRENT_DEPTH;
        startTime = System.currentTimeMillis();
        Move bestMove = moveMinimax(board, 1, minimaxColor);
        for (int i = 0;; i++) {
            if (timeout) {
                break;
            }
            CURRENT_DEPTH = INITIAL_DEPTH + i;
            System.out.println("Current depth: " + CURRENT_DEPTH);
            Move checkMove = moveMinimax(board, CURRENT_DEPTH, minimaxColor);
            if (checkMove != null) {
                bestMove = checkMove;
            }
        }
        System.out.println("Board Evaluation:" + board.getBoardEval());
        if (bestMove.isPromotionMove(bestMove)) {
            return "bestmove " + bestMove.getBeginning().convertPosition() + bestMove.getEnd().convertPosition() + bestMove.getPiece().getLetter();
        }
        return "bestmove " + bestMove.getBeginning().convertPosition() + bestMove.getEnd().convertPosition();
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
        ArrayList<Move> bestMoves = new ArrayList<>();
        Move bestMove;
        Board child;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == color) {
                    for (Move m : p.getMoves(board)) {
                        child = board.getChild(m);
                        double mx = minimax(child, depth - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                                isMaxPlayer);
                        if (mx == 123456)
                            return null;
                        if (bestMoveValue == mx) {
                            bestMoves.add(m);
                        }
                        if (color == Color.w) {
                            if (mx > bestMoveValue) {
                                bestMoveValue = mx;
                                bestMoves.clear();
                                bestMoves.add(m);
                            }
                        } else {
                            if (mx < bestMoveValue) {
                                bestMoveValue = mx;
                                bestMoves.clear();
                                bestMoves.add(m);
                            }
                        }
                    }
                }
            }
        }
        bestMove = bestMoves.get((int) (Math.random() * bestMoves.size()));
        return bestMove;
    }

    public static double minimax(Board board, int depth, double alpha, double beta, boolean isMaxPlayer) {
        ArrayList<Move> moveList = new ArrayList<>();
        ArrayList<Move> captureMoveList = new ArrayList<>();
        double eval;
        double minEval;
        double maxEval;
        Color color;
        if (depth == 0) {
            return (board.getBoardEval());
        }
        if (isMaxPlayer) {
            color = Color.w;
            maxEval = Double.NEGATIVE_INFINITY;
            if (System.currentTimeMillis() - startTime > TIMEOUT_TIME) {
                timeout = true;
                return 123456;
            }
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    if (piece.getColor() == Color.w) {
                        for (Move move : piece.getMoves(board)) {
                            if (move.isCapture(board))
                                captureMoveList.add(move);
                            else
                                moveList.add(move);
                        }
                    }
                }
            }
            moveList.addAll(0, captureMoveList);
            if (moveList.isEmpty()) {
                if (board.isColorInCheck(color))
                    return checkmateEval(color);
                else
                    return 0; // <--- this is most likely not right
            }
            for (Move m : moveList) {
                eval = minimax(board.getChild(m), depth - 1, alpha, beta, false);
                maxEval = Math.max(alpha, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            // sortArray(moveList);
            return maxEval;
        } else {
            color = Color.b;
            minEval = Double.POSITIVE_INFINITY;
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    if (piece.getColor() == Color.b) {
                        for (Move move : piece.getMoves(board)) {
                            if (move.isCapture(board))
                                captureMoveList.add(move);
                            else
                                moveList.add(move);
                        }
                    }
                }
            }
            moveList.addAll(0, captureMoveList);
            if (moveList.isEmpty()) {
                if (board.isColorInCheck(color))
                    return checkmateEval(color);
                else
                    return 0; // <--- this is most likely not right
            }
            for (Move m : moveList) {
                eval = minimax(board.getChild(m), depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    public static double checkmateEval(Color color) {
        if (color == Color.w)
            return Double.NEGATIVE_INFINITY;
        else
            return Double.POSITIVE_INFINITY;
    }

    public static Piece makePiece(Position position, char letter) {
        letter = Character.toUpperCase(letter);
        if (letter == 'Q')
            return new Queen(minimaxColor, position);
        if (letter == 'N')
            return new Knight(minimaxColor, position);
        if (letter == 'B')
            return new Bishop(minimaxColor, position);
        if (letter == 'R')
            return new Rook(minimaxColor, position);
        return Empty.getInstance();
    }

    public static int charToInt(char letter) {
        return switch (letter) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            default -> 999;
        };
    }
}
