package cheekykoala;

import cheekykoala.pieces.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static int index = 0;
    public static Color minimaxColor = Color.w;
    public static long startTime = System.currentTimeMillis();
    public static boolean timeout = false;
    public static int TIMEOUT_TIME = 10000;

    public static void main(String[] args) {
        Board board = new Board();
        apiConnect(board);
    }

    public static void apiConnect(Board board) {
        Scanner consoleInput = new Scanner(System.in);
        while (true) {
            String input = consoleInput.nextLine();
            if (input.contains("go")) {
                System.out.println(onGo(board));
            } else if (input.contains("uci")) {
                System.out.println("uciok");
            } else if (input.contains("isready")) {
                System.out.println("readyok");
            } else if (input.contains("position")) {
                UCIPosition(board, input);
            } else if (input.contains("quit")) {
                break;
            }
        }
    }

    public static void UCIPosition(Board board, String UCIPosition) {
        int startMoves = 3;
        index = 0;
        String[] UCIStringArray = UCIPosition.split(" ");
        if (UCIStringArray[1].equals("startpos")) {
            board.importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
            board.setBoardEval(board.recomputeBoardEval());
        } else {
            int i = 2;
            String fenString = "";
            fenString = UCIStringArray[1];
            while (!(UCIStringArray[i].equals("moves"))) {
                fenString += " " + UCIStringArray[i];
                i++;
            }
            fenString.stripLeading();
            // position k7/5P2/8/8/8/8/8/K7 w - - 0 1 moves f7f8
            board.importBoard(fenString);
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
            // System.out.println (board.getBoardEval());
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
        int CURRENT_DEPTH = 0;
        startTime = System.currentTimeMillis();
        Move bestMove = moveMinimax(board, 1, minimaxColor);
        for (int i = 0;; i++) {
            if (timeout) {
                break;
            }
            CURRENT_DEPTH = INITIAL_DEPTH + i;
            System.out.println("current depth is " + CURRENT_DEPTH);
            Move checkMove = moveMinimax(board, CURRENT_DEPTH, minimaxColor);
            if (checkMove != null) {
                bestMove = checkMove;
            }
        }
        // System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(board.getBoardEval());
        if (bestMove.isPromotionMove(bestMove)) {
            return new StringBuilder().append("bestmove ").append(bestMove.getBeginning().convertPosition())
                    .append(bestMove.getEnd().convertPosition()).append(bestMove.getPiece().getLetter()).toString();
        }
        return new StringBuilder().append("bestmove ").append(bestMove.getBeginning().convertPosition())
                .append(bestMove.getEnd().convertPosition()).toString();
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

    public static void timeMinimax() {
        Board board = new Board();
        board.importBoard("r2q1bnr/pp1p1kpp/1p1pn3/2b2p2/B2P2QP/1PP1NR2/P1N1B1P1/R3KP2 w Q - 0 1");
        board.setBoardEval(board.recomputeBoardEval());
        board.printBoard();
        double start = System.currentTimeMillis();
        System.out.println(moveMinimax(board, 4, Color.w));
        double end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0);
        System.out.println(board.getBoardEval());
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

    public static int convertLetter(char x) {
        return (x - 97);
    }

    public static char convertNumber(int x) {
        return ((char) (x + 97));
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
        switch (letter) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
        }
        return 999;
    }

}
