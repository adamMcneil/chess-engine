package Cheakykoala;

import Cheakykoala.Pieces.*;

public class Board {

    Piece[][] board = new Piece[8][8];
    Position inPassingSquare = new Position(8, 8);
    boolean canEnpassant = false;
    int whiteCastleMoveState = 0;
    int blackCastleMoveState = 0;
    int count = 0;

    public Board() {
        makeBoard();
    }

    public void increaseCount(){
        count++;
        System.out.println (count);
    }

    public void setMoveState(Piece piece, Move move){
        if (piece.isKing()) {
            if (piece.getColor() == Color.w) {
                this.increaseWhiteMoveState(3);
            } else {
                this.increaseBlackMoveState(3);
            }
        }
        if (piece.isRook()) {
            if (piece.getColor() == Color.w) {
                if (piece.getPosition().getX() == 0 && piece.getPosition().getY() == 7 && this.getWhiteCastleMoveState() != 2) {
                    this.increaseWhiteMoveState(2);
                } else if (piece.getPosition().getX() == 7 && piece.getPosition().getY() == 7 && this.getWhiteCastleMoveState() != 1) {
                    this.increaseWhiteMoveState(1);
                }
            }
            if (piece.getColor() == Color.b) {
                if (piece.getPosition().getX() == 0 && piece.getPosition().getY() == 0 && this.getBlackCastleMoveState() != 2) {
                    this.increaseBlackMoveState(2);
                } else if (piece.getPosition().getX() == 7 && piece.getPosition().getY() == 0 && this.getBlackCastleMoveState() != 1) {
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

    public int getWhiteCastleMoveState(){
        return whiteCastleMoveState;
    }

    public int getBlackCastleMoveState(){
        return blackCastleMoveState;
    }

    public void increaseWhiteMoveState(int number){
        whiteCastleMoveState+= number;
    }

    public void increaseBlackMoveState(int number){
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
            for (int j = 0; j < board[0].length; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print("\033[40m");
                }
                System.out.print(board[i][j].getPiece() + "  ");
                if ((i + j) % 2 == 0) {
                    System.out.print("\033[0m");
                }
            }
            System.out.println();
        }
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
                Piece checkpiece = this.getPieceAt(new Position(j,i));
                if (this.getPieceAt(new Position(j,i)).isKing() && this.getPieceAt(new Position(j,i)).getColor() == color) {
                    Position checkPosition = null;
                    Position home = new Position(j, i);
                    int[][] knightMoves = {
                            {2, 1},
                            {2, -1},
                            {1, 2},
                            {1, -2},
                            {-1, 2},
                            {-1, -2},
                            {-2, 1},
                            {-2, -1}
                    };
                    int[][] straightMoves = {
                            {1, 0},
                            {0, -1},
                            {-1, 0},
                            {0, 1}
                    };
                    int[][] diagonalMoves = {
                            {1, 1},
                            {1, -1},
                            {-1, -1},
                            {-1, 1}
                    };
                    int[][] kingMoves = {
                            {1, 0},
                            {0, -1},
                            {-1, 0},
                            {0, 1},
                            {1, -1},
                            {1, 1},
                            {-1, -1},
                            {-1, 1}
                    };
                    for (int[] knightMove : knightMoves) {
                        checkPosition = new Position(home.getX() + knightMove[0], home.getY() + knightMove[1]);
                        if (checkPosition.isOnBoard()) {
                            if (getPieceAt(checkPosition).isKnight() && getPieceAt(checkPosition).getColor() != color) {
//                                System.out.println ("in check");
                                return true;
                            }
                        }
                    }

                    for (int[] straightMove : straightMoves) {
                        checkPosition = new Position(home.getX() + straightMove[0], home.getY() + straightMove[1]);
                        while (checkPosition.isOnBoard()) {
                            if ((getPieceAt(checkPosition).isQueen() || getPieceAt(checkPosition).isRook()) && getPieceAt(checkPosition).getColor() != color) {
//                                System.out.println ("in check");
                                return true;
                            }
                            if (getPieceAt(checkPosition).getColor() != Color.g) {
                                break;
                            }
                            checkPosition = new Position(checkPosition.getX() + straightMove[0], checkPosition.getY() + straightMove[1]);
                        }
                    }

                    for (int[] diagonalMove : diagonalMoves) {
                        checkPosition = new Position(home.getX() + diagonalMove[0], home.getY() + diagonalMove[1]);
                        while (checkPosition.isOnBoard()) {
                            if ((getPieceAt(checkPosition).isQueen() || getPieceAt(checkPosition).isBishop()) && getPieceAt(checkPosition).getColor() != color) {
//                                System.out.println ("in check");
                                return true;
                            }
                            if (getPieceAt(checkPosition).getColor() != Color.g) {
                                break;
                            }
                            checkPosition = new Position(checkPosition.getX() + diagonalMove[0], checkPosition.getY() + diagonalMove[1]);
                        }
                    }

                    int direction;
                    if (color == Color.w)
                        direction = -1;
                    else
                        direction = 1;
                    Position leftCheckPosition = new Position(home.getX() - 1, home.getY() + direction);
                    Position rightCheckPosition = new Position(home.getX() + 1, home.getY() + direction);
                    if (leftCheckPosition.isOnBoard() && getPieceAt(leftCheckPosition).isPawn() && getPieceAt(leftCheckPosition).getColor() != color) {
//                        System.out.println ("in check");
                        return true;
                    }
                    if (rightCheckPosition.isOnBoard() && getPieceAt(rightCheckPosition).isPawn() && getPieceAt(rightCheckPosition).getColor() != color){
//                        System.out.println ("in check");
                        return true;
                    }

                    for (int[] kingMove : kingMoves) {
                        checkPosition = new Position(home.getX() + kingMove[0], home.getY() + kingMove[1]);
                        if (checkPosition.isOnBoard()) {
                            if (getPieceAt(checkPosition).isKing()){
//                                System.out.println ("in check");
                                return true;
                            }
                        }
                    }
                }
            }
        }
//        System.out.println ("not in check");
        return false;
    }

    public void addPiece(Position position, Piece piece) {
        board[position.getY()][position.getX()] = this.getPiece(piece.getLetter(), position.getX(), position.getY());
    }

    private void makeBoard() {
        importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public int getWinState(Color color){
        int moves = 0;
    for (Piece[] pieces : this.getBoard()) {
        for (Piece p : pieces) {
            if (p.getColor() == color){
                moves += p.getMoves(this).size();
                if (moves > 0){
                    return 0;
                }
                }
        }
    }
    if (this.isColorInCheck(color) && moves == 0) {
        return 2;
    } else if  (moves == 0){
        return 1;
    }
   return 0;
    }

    public void importBoard(String fenBoard) {
        int x = 0;
        int y = 0;
        int end = fenBoard.length();
        String[] splitFen = fenBoard.split(" ");
        String boardState = fenBoard.substring(splitFen.length, end);
//        System.out.println (boardState);
        fenBoard = splitFen[0];
        for (int i = 0; i < fenBoard.length(); i++) {
            if (fenBoard.charAt(i) != '/' && !Character.isDigit(fenBoard.charAt(i))) {
                board[y][x] = getPiece(fenBoard.charAt(i), x, y);
                x++;
            } else if (Character.isDigit(fenBoard.charAt(i))) {
                for (int j = 0; j < Character.getNumericValue(fenBoard.charAt(i)); j++) {
                    board[y][x] = new Empty(new Position(x, y));
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
        piece = new Empty(new Position(x, y));
        return piece;
    }

    public void setInPassingSquare(Position x) {
        this.inPassingSquare.setY(x.getY());
        this.inPassingSquare.setX(x.getX());
    }

    public void setCanEnpassant(Boolean logic) {
        canEnpassant = logic;
    }

    public Position getInPassingSquare(){
        return inPassingSquare;
    }

    public  void copyBoard(Board board){
        int x = 0, y = 0;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                Piece pieceCopy = getPiece(piece.getLetter(), x, y);
                this.getBoard()[y][x] = pieceCopy;
                x++;
            }
            x = 0;
            y++;
        }
    }

    public Board getChild(Board board, Move move) {
        Board child = new Board();
        child.setInPassingSquare(board.getInPassingSquare());
        child.setCanEnpassant(board.getCanEnpassant());
        child.increaseWhiteMoveState(board.getWhiteCastleMoveState());
        child.increaseBlackMoveState(board.getBlackCastleMoveState());

        child.copyBoard(board);

        child.getPieceAt(move.getBeginning()).move(child, move);
        return child;
    }

}
