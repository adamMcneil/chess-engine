package cheekykoala;

import cheekykoala.pieces.*;

import java.util.ArrayList;
import java.util.Objects;

public class Board {
    private static final int[][] knightMoves = {
            { 2, 1 },
            { 2, -1 },
            { 1, 2 },
            { 1, -2 },
            { -1, 2 },
            { -1, -2 },
            { -2, 1 },
            { -2, -1 }
    };
    private static final int[][] straightMoves = {
            { 1, 0 },
            { 0, -1 },
            { -1, 0 },
            { 0, 1 }
    };
    private static final int[][] diagonalMoves = {
            { 1, 1 },
            { 1, -1 },
            { -1, -1 },
            { -1, 1 }
    };
    private static final int[][] kingMoves = {
            { 1, 0 },
            { 0, -1 },
            { -1, 0 },
            { 0, 1 },
            { 1, -1 },
            { 1, 1 },
            { -1, -1 },
            { -1, 1 }
    };

    Piece[][] board = new Piece[8][8];
    Position inPassingSquare = new Position(8, 8);
    public boolean canEnpassant = false;
    public int whiteCastleMoveState = 0;
    public int blackCastleMoveState = 0;
    public double boardEval;
    Color colorToMove = Color.w;

    public Board() {
        makeBoard();
    }

    public Board(Board other) {
        this.setInPassingSquare(other.getInPassingSquare());
        this.setCanEnpassant(other.getCanEnpassant());
        this.increaseWhiteMoveState(other.getWhiteCastleMoveState());
        this.increaseBlackMoveState(other.getBlackCastleMoveState());
        this.copyBoard(other);
        this.setBoardEval(other.getBoardEval());
        this.colorToMove = other.colorToMove;

    }

    public int countNodes(int depth, Color color) {
        int count = 0;
        Board child;
        if (depth == 0) {
            return 1;
        }
        for (Move move : getAllMoves(color)) {
            child = getChild(move);
            count += child.countNodes(depth - 1, color.getOppositeColor());
        }
        return count;
    }

    public double recomputeBoardEval() {
        double eval = 0;
        for (Piece[] pieces : getBoard()) {
            for (Piece piece : pieces) {
                if (piece.getColor() == Color.w) {
                    eval += piece.getPieceEval();
                    eval += piece.getValue();
                } else {
                    eval -= piece.getPieceEval();
                    eval -= piece.getValue();
                }
            }
        }
        return eval;
    }

    public double getBoardEval() {
        return boardEval;
    }

    public void setBoardEval(double newBoardEval) {
        this.boardEval = newBoardEval;
    }

    public void changeEval(Move move, Piece movedPiece) {
        int index = move.getBeginning().getX() + move.getBeginning().getY() * 8;
        if (movedPiece.getColor() == Color.w) {
            boardEval = boardEval - movedPiece.getValue(index);
        } else {
            boardEval = boardEval + movedPiece.getValue(63 - index);
        }

        Piece takenPiece = this.getPieceAt(move.getEnd());
        index = move.getEnd().getX() + move.getEnd().getY() * 8;
        if (takenPiece.getColor() != Color.g) {
            if (takenPiece.getColor() == Color.w) {
                boardEval = boardEval - this.getPieceAt(move.getEnd()).getValue(index) - takenPiece.getPieceEval();
            } else {
                boardEval = boardEval + this.getPieceAt(move.getEnd()).getValue(63 - index)
                        + takenPiece.getPieceEval();
            }
        }

        if (move.isPromotionMove(move)) {
            if (takenPiece.getColor() == Color.w) {
                boardEval = boardEval + move.getPiece().getPieceEval();
            } else {
                boardEval = boardEval - move.getPiece().getPieceEval();
            }
            movedPiece = move.getPiece();
        }

        if (movedPiece.getColor() == Color.w) {
            boardEval = boardEval + movedPiece.getValue(index);
        } else {
            boardEval = boardEval - movedPiece.getValue(63 - index);
        }

        if (move.isInPassingMove(this)) {
            if (movedPiece.getColor() == Color.w) {
                boardEval += 100;
                // boardEval = boardEval + movedPiece.getValueInt(63 - (index - 8));
            } else {
                boardEval -= 100;
                // boardEval = boardEval - movedPiece.getValueInt(index + 8);
            }
        }
        if (move.isCastleMove(this)) {
            if (movedPiece.getColor() == Color.w) {
                if (index == 58) {
                    boardEval += 5;
                }
            } else {
                if (index == 2) {
                    boardEval -= 5;
                }
            }
        }
    }

    public ArrayList<Move> getAllMoves(Color color) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece[] pieces : board) {
            for (Piece p : pieces) {
                if (p.getColor() == color) {
                    moves.addAll(p.getMoves(this));
                }
            }
        }
        return moves;
    }

    public void setMoveState(Piece piece, Move move) {
        if (piece.isKing()) {
            if (piece.getColor() == Color.w) {
                this.increaseWhiteMoveState(3);
            } else {
                this.increaseBlackMoveState(3);
            }
        }
        if (piece.isRook()) {
            if (piece.getColor() == Color.w) {
                if (piece.getPosition().getX() == 0 && piece.getPosition().getY() == 7
                        && this.getWhiteCastleMoveState() != 2) {
                    this.increaseWhiteMoveState(2);
                } else if (piece.getPosition().getX() == 7 && piece.getPosition().getY() == 7
                        && this.getWhiteCastleMoveState() != 1) {
                    this.increaseWhiteMoveState(1);
                }
            }
            if (piece.getColor() == Color.b) {
                if (piece.getPosition().getX() == 0 && piece.getPosition().getY() == 0
                        && this.getBlackCastleMoveState() != 2) {
                    this.increaseBlackMoveState(2);
                } else if (piece.getPosition().getX() == 7 && piece.getPosition().getY() == 0
                        && this.getBlackCastleMoveState() != 1) {
                    this.increaseBlackMoveState(1);
                }
            }
        }
        if (move.getEnd().getX() == 0 && move.getEnd().getY() == 7 && this.getWhiteCastleMoveState() != 2) {
            this.increaseWhiteMoveState(2);
        } else if (move.getEnd().getX() == 7 && move.getEnd().getY() == 7 && this.getWhiteCastleMoveState() != 1) {
            this.increaseWhiteMoveState(1);
        } else if (move.getEnd().getX() == 0 && move.getEnd().getY() == 0 && this.getWhiteCastleMoveState() != 2) {
            this.increaseWhiteMoveState(2);
        } else if (move.getEnd().getX() == 7 && move.getEnd().getY() == 0 && this.getWhiteCastleMoveState() != 1) {
            this.increaseWhiteMoveState(1);
        }
    }

    public int getWhiteCastleMoveState() {
        return whiteCastleMoveState;
    }

    public int getBlackCastleMoveState() {
        return blackCastleMoveState;
    }

    public void increaseWhiteMoveState(int number) {
        whiteCastleMoveState += number;
    }

    public void increaseBlackMoveState(int number) {
        blackCastleMoveState += number;
    }

    public boolean getCanEnpassant() {
        return canEnpassant;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < board[0].length; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print("\033[40m");
                }
                System.out.print(" " + board[i][j].getPiece()+ " ");
                if ((i + j) % 2 == 0) {
                    System.out.print("\033[0m");
                }
            }
            System.out.println();
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    public Piece getPieceAt(Position position) {
        return board[position.getY()][position.getX()];
    }

    public Piece get(int x, int y) {
        return board[y][x];
    }

    public boolean isColorInCheck(Color color) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (this.getPieceAt(new Position(j, i)).isKing()
                        && this.getPieceAt(new Position(j, i)).getColor() == color) {
                    Position checkPosition;
                    Position home = new Position(j, i);
                    for (int[] knightMove : knightMoves) {
                        checkPosition = new Position(home.getX() + knightMove[0], home.getY() + knightMove[1]);
                        if (checkPosition.isOnBoard()) {
                            if (getPieceAt(checkPosition).isKnight() && getPieceAt(checkPosition).getColor() != color) {
                                // System.out.println ("in check");
                                return true;
                            }
                        }
                    }

                    for (int[] straightMove : straightMoves) {
                        checkPosition = new Position(home.getX() + straightMove[0], home.getY() + straightMove[1]);
                        while (checkPosition.isOnBoard()) {
                            if ((getPieceAt(checkPosition).isQueen() || getPieceAt(checkPosition).isRook())
                                    && getPieceAt(checkPosition).getColor() != color) {
                                // System.out.println ("in check");
                                return true;
                            }
                            if (getPieceAt(checkPosition).getColor() != Color.g) {
                                break;
                            }
                            checkPosition = new Position(checkPosition.getX() + straightMove[0],
                                    checkPosition.getY() + straightMove[1]);
                        }
                    }

                    for (int[] diagonalMove : diagonalMoves) {
                        checkPosition = new Position(home.getX() + diagonalMove[0], home.getY() + diagonalMove[1]);
                        while (checkPosition.isOnBoard()) {
                            if ((getPieceAt(checkPosition).isQueen() || getPieceAt(checkPosition).isBishop())
                                    && getPieceAt(checkPosition).getColor() != color) {
                                // System.out.println ("in check");
                                return true;
                            }
                            if (getPieceAt(checkPosition).getColor() != Color.g) {
                                break;
                            }
                            checkPosition = new Position(checkPosition.getX() + diagonalMove[0],
                                    checkPosition.getY() + diagonalMove[1]);
                        }
                    }

                    int direction;
                    if (color == Color.w)
                        direction = -1;
                    else
                        direction = 1;
                    Position leftCheckPosition = new Position(home.getX() - 1, home.getY() + direction);
                    Position rightCheckPosition = new Position(home.getX() + 1, home.getY() + direction);
                    if (leftCheckPosition.isOnBoard() && getPieceAt(leftCheckPosition).isPawn()
                            && getPieceAt(leftCheckPosition).getColor() != color) {
                        // System.out.println ("in check");
                        return true;
                    }
                    if (rightCheckPosition.isOnBoard() && getPieceAt(rightCheckPosition).isPawn()
                            && getPieceAt(rightCheckPosition).getColor() != color) {
                        // System.out.println ("in check");
                        return true;
                    }

                    for (int[] kingMove : kingMoves) {
                        checkPosition = new Position(home.getX() + kingMove[0], home.getY() + kingMove[1]);
                        if (checkPosition.isOnBoard()) {
                            if (getPieceAt(checkPosition).isKing()) {
                                // System.out.println ("in check");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        // System.out.println ("not in check");
        return false;
    }

    public void addPiece(Position position, Piece piece) {
        board[position.getY()][position.getX()] = piece;
    }

    private void makeBoard() {
        importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public void setCastleStates(String fenCastleData) {
        boolean whiteKing = false;
        boolean whiteQueen = false;
        boolean blackKing = false;
        boolean blackQueen = false;
        for (char c : fenCastleData.toCharArray()) {
            switch (c) {
                case 'K':
                    whiteKing = true;
                    break;
                case 'Q':
                    whiteQueen = true;
                    break;
                case 'k':
                    blackKing = true;
                    break;
                case 'q':
                    blackQueen = true;
                    break;
            }
        }
        if (whiteKing) {
            if (whiteQueen) {
                this.whiteCastleMoveState = 0;
            } else {
                this.whiteCastleMoveState = 2;
            }
        } else if (whiteQueen) {
            this.whiteCastleMoveState = 1;
        } else {
            this.whiteCastleMoveState = 3;
        }
        if (blackKing) {
            if (blackQueen) {
                this.blackCastleMoveState = 0;
            } else {
                this.blackCastleMoveState = 2;
            }
        } else if (blackQueen) {
            this.blackCastleMoveState = 1;
        } else {
            this.blackCastleMoveState = 3;
        }
    }

    public static Color getColorFromString(String color) {
        if (Objects.equals(color, "w"))
            return Color.w;
        else if (Objects.equals(color, "b")) {
            return Color.b;
        }
        return Color.g;
    }

    public Position decodeInPassingSquare(String fenData) {
        if (fenData.equals("-")) {
            return new Position(8, 8);
        }
        this.canEnpassant = true;
        char one = fenData.charAt(0);
        int x = ((int) one) - 97;
        int y = 7 - (Character.getNumericValue(fenData.charAt(1)) - 1);
        return new Position(x, y);
    }

    public void importBoard(String fenBoard) {
        int x = 0;
        int y = 0;
        String[] splitFen = fenBoard.split(" ");
        fenBoard = splitFen[0];
        this.colorToMove = getColorFromString(splitFen[1]);
        setCastleStates(splitFen[2]);
        setInPassingSquare(decodeInPassingSquare(splitFen[3]));
        for (int i = 0; i < fenBoard.length(); i++) {
            if (fenBoard.charAt(i) != '/' && !Character.isDigit(fenBoard.charAt(i))) {
                board[y][x] = getPiece(fenBoard.charAt(i), x, y);
                x++;
            } else if (Character.isDigit(fenBoard.charAt(i))) {
                for (int j = 0; j < Character.getNumericValue(fenBoard.charAt(i)); j++) {
                    board[y][x] = Empty.getInstance();
                    x++;
                }
            } else {
                x = 0;
                y++;
            }
        }

    }

    public Piece getPiece(char letter, int x, int y) {
        Piece piece;
        switch (letter) {
            case 'r':
                piece = new Rook(Color.b, new Position(x, y));
                return piece;
            case 'R':
                piece = new Rook(Color.w, new Position(x, y));
                return piece;
            case 'n':
                piece = new Knight(Color.b, new Position(x, y));
                return piece;
            case 'N':
                piece = new Knight(Color.w, new Position(x, y));
                return piece;
            case 'b':
                piece = new Bishop(Color.b, new Position(x, y));
                return piece;
            case 'B':
                piece = new Bishop(Color.w, new Position(x, y));
                return piece;
            case 'q':
                piece = new Queen(Color.b, new Position(x, y));
                return piece;
            case 'Q':
                piece = new Queen(Color.w, new Position(x, y));
                return piece;
            case 'k':
                piece = new King(Color.b, new Position(x, y));
                return piece;
            case 'K':
                piece = new King(Color.w, new Position(x, y));
                return piece;
            case 'p':
                piece = new Pawn(Color.b, new Position(x, y));
                return piece;
            case 'P':
                piece = new Pawn(Color.w, new Position(x, y));
                return piece;
        }
        piece = Empty.getInstance();
        return piece;
    }

    public void setInPassingSquare(Position x) {
        this.inPassingSquare.setY(x.getY());
        this.inPassingSquare.setX(x.getX());
    }

    public void setCanEnpassant(Boolean logic) {
        canEnpassant = logic;
    }

    public Position getInPassingSquare() {
        return inPassingSquare;
    }

    public void copyBoard(Board board) {
        int x = 0, y = 0;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                this.getBoard()[y][x] = piece.copy();
                x++;
            }
            x = 0;
            y++;
        }
    }

    public Board getChild(Move move) {
        Board child = new Board(this);
        child.getPieceAt(move.getBeginning()).move(child, move);
        return child;
    }

}
