package Cheakykoala;

import Cheakykoala.Pieces.*;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int index = 0;
    public static Color minimaxColor = Color.w;
    public static long startTime = System.currentTimeMillis();
    public static boolean timeout = false;
    public static int TIMEOUT_TIME = 3000;

    public static void main(String[] args) throws InterruptedException {
//        playGame(9999);
        Board board = new Board();
        apiConnect(board);
    }


    public static void apiConnect(Board board){
        Scanner consoleInput = new Scanner(System.in);
        while (true) {
            board.printBoard();
            String input = consoleInput.nextLine();
            if (input.contains("go")) {
                System.out.println(playMinimax(board, 3, minimaxColor));
            } else if (input.contains("uci")){
                System.out.println ("uciok");
            } else if (input.contains("isready")){
                System.out.println("readyok");
            } else if (input.contains("position")) {
                UCIPosition(board, input);
            }
        }
    }

    public static String onGo(Board board){
        int INITIAL_DEPTH = 3;
        int CURRENT_DEPTH;
        Move bestMove = playMinimax(board, 1, minimaxColor);;
        startTime = System.currentTimeMillis();
        for (int i = INITIAL_DEPTH;; i++){
            if (timeout){
                break;
            }
                CURRENT_DEPTH = INITIAL_DEPTH + i;
                bestMove = playMinimax(board, CURRENT_DEPTH, minimaxColor);
        }
        if (bestMove.isPromotionMove(bestMove)){
            return new StringBuilder().append("bestmove ").append(bestMove.getBeginning().convertPosition()).append(bestMove.getEnd().convertPosition()).append(bestMove.getPiece().getLetter()).toString() ;
        }
        return new StringBuilder().append("bestmove ").append(bestMove.getBeginning().convertPosition()).append(bestMove.getEnd().convertPosition()).toString();
    }

    public static void UCIPosition(Board board, String UCIPosition) {
        //position startps moves e2e4
        int startMoves = 3;
        index = 0;
        String[] UCIStringArray = UCIPosition.split(" ");
        if (UCIStringArray[1].equals("startpos")) {
            board.importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        } else {
            int i = 2;
            String fenString = "";
            fenString = UCIStringArray[1];
            while (!(UCIStringArray[i].equals("moves"))){
                fenString += " " + UCIStringArray[i];
                i++;
            }
            fenString.stripLeading();
            //position k7/5P2/8/8/8/8/8/K7 w - - 0 1 moves f7f8
            board.importBoard(fenString);
            board.printBoard();
            startMoves = i + 1;
        }
        for (int i = startMoves; i < UCIStringArray.length; i++) {
            index++;
            Move move;
            Position first = new Position(charToInt(UCIStringArray[i].charAt(0)), 8 - Character.getNumericValue(UCIStringArray[i].charAt(1)));
            Position second = new Position(charToInt(UCIStringArray[i].charAt(2)), 8 - Character.getNumericValue(UCIStringArray[i].charAt(3)));
            if (UCIStringArray[i].length() == 5){
                char letter = UCIStringArray[i].charAt(4);
                Color color;
                if (Character.isUpperCase(letter))
                    color = Color.w;
                else
                    color = Color.b;
                move = new PromotionMove(first, second, makePiece(second, letter, color));
            }
            else {
                move = new Move(first, second);
            }
            board.getPieceAt(first).move(board, move);
        }
        if (index % 2 == 0){
            minimaxColor = Color.w;
        } else {
            minimaxColor = Color.b;
        }
    }

    public static Piece makePiece(Position position, char letter, Color color){
        switch (letter){
            case 'Q':
                return new Queen(color, position);
            case'q':
                return new Queen(color, position);
            case'N':
                return new Knight(color, position);
            case 'n':
                return new Knight(color, position);
            case 'B':
                return new Bishop(color, position);
            case 'b':
                return new Bishop(color, position);
            case 'R':
                return new Rook(color, position);
            case 'r':
                return new Rook(color, position);
        }
        return new Empty(position);
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

    public static void debugger() {
        Board board = new Board();
        String fenString = "r3k2r/p1ppqNb1/bn2pnp1/3P4/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 1";
        board.importBoard(fenString);
        board.printBoard();
        int total = 0;
        int nodes = 0;
        Color color;
        if (fenString.contains("w")) {
            color = Color.w;
        } else
            color = Color.b;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == color) {
                    for (Move m : p.getMoves(board)) {
                        System.out.print(m.getBeginning().convertPosition() + " -> " + m.getEnd().convertPosition() + " ");
                        nodes = countNodes(board.getChild(m), 2, getOppositeColor(color));
                        System.out.println(nodes);
                        total = total + nodes;
                    }
                }
            }
        }
        System.out.println(total);
    }

    public static void testNodes() {
        Board board = new Board();
        if (countNodes(board, 1, Color.w) == 20)
            System.out.println("depth 1");
        if (countNodes(board, 2, Color.w) == 400)
            System.out.println("depth 2");
        if (countNodes(board, 3, Color.w) == 8902)
            System.out.println("depth 3");
        if (countNodes(board, 4, Color.w) == 197281)
            System.out.println("depth 4");
        if (countNodes(board, 5, Color.w) == 4865609)
            System.out.println("depth 5");
    }

    public static void testNodes2() {
        Board board = new Board();
        ArrayList<String> fen = new ArrayList<>();
        fen.add("r6r/1b2k1bq/8/8/7B/8/8/R3K2R b KQ - 3 2");

        board.importBoard("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ");
        Color color = Color.w;
        board.printBoard();
        Position position = new Position(4,0);
        System.out.println (board.getPieceAt(position).getMoves(board));
        System.out.println (board.getPieceAt(position).getMoves(board).size());

    }

    public static Color getOppositeColor(Color color) {
        if (color == Color.w) {
            return Color.b;
        }
        return Color.w;
    }

    public static int countNodes(Board board, int depth, Color color) {
        int count = 0;
        Board child;
        if (depth == 0) {
            return 1;
        }
        for (Move move : board.getAllMoves(color)) {
            child = board.getChild(move);
            count += countNodes(child, depth - 1, getOppositeColor(color));
        }
        return count;
    }

//    public static void playGame(int depth) throws InterruptedException {
//        Board board = new Board();
//        for (int i = 0; i < depth; i++) {
//            board.printBoard();
//            System.out.println();
//            playRandom(board, Color.w);
//            board.printBoard();
//            System.out.println();
//            moveMinimax(board, 3,Color.b);
//        }
//    }

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

            Position first = new Position(convertLetter(beginning.charAt(0)), 8 - Character.getNumericValue(beginning.charAt(1)));
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

//    public static void moveMinimax(Board board, int depth, Color color) {
//        String movestr = playMinimax(board, depth, color);
//        Position beginning = new Position(convertLetter(movestr.charAt(9)), 8 - Character.getNumericValue(movestr.charAt(10)));
//        Position end = new Position(convertLetter(movestr.charAt(11)), 8 - Character.getNumericValue(movestr.charAt(12)));
//        Move move = new Move(beginning, end);
//        board.getPieceAt(beginning).move(board, move);
//    }

    public static Move playMinimax(Board board, int depth, Color color) {
        double bestMoveValue;
        boolean isMaxPlayer;
        if (color == Color.w) {
            bestMoveValue = Double.NEGATIVE_INFINITY;
            isMaxPlayer = false;

        } else{
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
                        double mx = minimax(child, depth - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, isMaxPlayer);
                        if (bestMoveValue == mx) {
                            bestMoves.add(m);
                        }
                        if (color == Color.w){
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
        return bestMove = bestMoves.get((int) (Math.random() * bestMoves.size()));
    }

    public static double evalBoard(Board board) {
        int eval = 0;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                if (piece.getColor() == Color.w) {
                    eval += getPieceEval(piece);
                } else {
                    eval -= getPieceEval(piece);
                }
            }
        }
        return eval;
    }

    public static double getPieceEval(Piece piece) {
        char letter = piece.getLetter();
        letter = Character.toUpperCase(letter);
        switch (letter) {
            case 'R':
                return 500;
            case 'N':
            case 'B':
                return 300;
            case 'Q':
                return 900;
            case 'P':
                return 100;
        }
        return 0;
    }

    public static double minimax(Board board, int depth, double alpha, double beta, boolean isMaxPlayer) {
        double eval;
        double minEval;
        double maxEval;
        if (System.currentTimeMillis() - startTime > TIMEOUT_TIME)
            timeout = true;
        if (board.getWinState(Color.w) == 2){
            return Double.NEGATIVE_INFINITY;
        }
        if (board.getWinState(Color.b) == 2){
            return Double.POSITIVE_INFINITY;
        }
        if (board.getWinState(Color.w) != 0) { //checks for stalemate
            return (evalBoard(board) * -1);
        }
        if (depth == 0){
            return (evalBoard(board));
        }
        if (isMaxPlayer) {
            maxEval = Double.NEGATIVE_INFINITY;
            prune:
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    if (piece.getColor() == Color.w) {
                        for (Move move : piece.getMoves(board)) {
                            eval = minimax(board.getChild(move), depth - 1, alpha, beta, false);
                            maxEval = Math.max(alpha, eval);
                            alpha = Math.max(alpha, eval);
                            if (beta <= alpha) {
                                break prune;
                            }
                        }
                    }
                }
            }
            return maxEval;
        } else {
            minEval = Double.POSITIVE_INFINITY;
            prune:
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    if (piece.getColor() == Color.b) {
                        for (Move move : piece.getMoves(board)) {
                            eval = minimax(board.getChild(move), depth - 1, alpha, beta, true);

                            minEval = Math.min(minEval, eval);
                            beta = Math.min(beta, eval);
                            if (beta <= alpha) {
                                break prune;
                            }
                        }
                    }
                }
            }
            return minEval;
        }
    }

    public static int convertLetter(char x) {
        return (x - 97);
    }

    public static char convertNumber(int x) {
        return ((char) (x + 97));
    }
}