package cheekykoala;

import cheekykoala.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[] board = new Piece[64];
    private int inPassingSquare = -1;
    private boolean canInPassingAttack = false;
    private int whiteCastleMoveState = 0;
    private int blackCastleMoveState = 0;
    private double eval = 0;

    public Board() {
        importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Board(String fen) {
        importBoard(fen);
    }

    public Board(Board other) {
        this.copyBoard(other);
        this.setInPassingSquare(other.getInPassingSquare());
        this.setCanInPassingAttack(other.getCanInPassingAttack());
        this.increaseWhiteMoveState(other.getWhiteCastleMoveState());
        this.increaseBlackMoveState(other.getBlackCastleMoveState());
        this.eval = other.eval;
    }

    public void divideOne(Color color, int depth) {
        List<Move> moves = getAllMoves(color);
        for (Move move : moves) {
            Board child = getChild(move);
            int nodes = child.countOnlyNodes(depth, color.getOppositeColor());
            System.out.println(move + String.format(" %d", nodes));

        }
    }

    public MoveCounter countNodes(int depth, Color color) {
        MoveCounter moveCounter = new MoveCounter();
        if (isColorInMate(color)) {
            moveCounter.checkMates = 1;
            moveCounter.nodes = 1;
            return moveCounter;
        }
        if (depth == 0) {
            moveCounter.nodes = 1;
            return moveCounter;
        }
        Board child;
        for (Move move : getAllMoves(color)) {
            if (getPieceAt(move.getBeginning()).isEmpty()) {
                System.out.println("This is bad");
                printBoard();
            }
            child = getChild(move);
            if (move.isPromotionMove()) {
                moveCounter.promotions++;
            } else if (move.isCastleMove(this)) {
                moveCounter.castles++;
            } else if (move.isInPassingMove(this)) {
                moveCounter.inPassing++;
            }
            if (!this.getPieceAt(move.getEnd()).isEmpty()) {
                moveCounter.captures++;
            }
            if (child.isColorInCheck(color.getOppositeColor())) {
                moveCounter.check++;
            }
            MoveCounter i = child.countNodes(depth - 1, color.getOppositeColor());
            moveCounter.Add(i);
        }
        return moveCounter;
    }

    public int countOnlyNodes(int depth, Color color) {
        if (depth == 0) {
            return 1;
        }
        Board child;
        int i = 0;
        for (Move move : getAllMoves(color)) {
            if (getPieceAt(move.getBeginning()).isEmpty()) {
                System.out.println("This is bad");
                printBoard();
            }
            child = getChild(move);
            i += child.countOnlyNodes(depth - 1, color.getOppositeColor());
        }
        return i;
    }

    public double recomputeEval() {
        double eval = 0;
        int position = 0;
        for (Piece piece : getBoard()) {
            eval += piece.getPieceEval();
            eval += piece.getSquareEval(position);
            position++;
        }
        return eval;
    }

    public double getEval() {
        return eval;
    }

    public List<Move> getAllMoves(Color color) {
        ArrayList<Move> moves = new ArrayList<>();
        int position = 0;
        for (Piece piece : board) {
            if (piece.getColor() == color) {
                moves.addAll(piece.getMoves(this, position));
            }
            position++;
        }
        return moves;
    }

    public List<Move> getPseudoMoves(Color color) {
        ArrayList<Move> moves = new ArrayList<>();
        int position = 0;
        for (Piece piece : board) {
            if (piece.getColor() == color) {
                moves.addAll(piece.getPseudoMoves(this, position));
            }
            position++;
        }
        return moves;
    }

    public void updateCastleState(Piece piece, Move move) {
        if (piece.isKing()) {
            if (piece.getColor() == Color.w) {
                increaseWhiteMoveState(3);
            } else {
                increaseBlackMoveState(3);
            }
        }
        if (piece.isRook()) {
            if (piece.getColor() == Color.w) {
                if (Position.getColumn(move.getBeginning()) == 0 && Position.getRow(move.getBeginning()) == 7
                        && getWhiteCastleMoveState() != 2) {
                    increaseWhiteMoveState(2);
                } else if (Position.getColumn(move.getBeginning()) == 7 && Position.getRow(move.getBeginning()) == 7
                        && getWhiteCastleMoveState() != 1) {
                    increaseWhiteMoveState(1);
                }
            }
            if (piece.getColor() == Color.b) {
                if (Position.getColumn(move.getBeginning()) == 0 && Position.getRow(move.getBeginning()) == 0
                        && getBlackCastleMoveState() != 2) {
                    increaseBlackMoveState(2);
                } else if (Position.getColumn(move.getBeginning()) == 7 && Position.getRow(move.getBeginning()) == 0
                        && getBlackCastleMoveState() != 1) {
                    increaseBlackMoveState(1);
                }
            }
        }
        if (move.getEnd() == 56 && getWhiteCastleMoveState() != 2) {
            increaseWhiteMoveState(2);
        } else if (move.getEnd() == 63 && getWhiteCastleMoveState() != 1) {
            increaseWhiteMoveState(1);
        } else if (move.getEnd() == 0 && getWhiteCastleMoveState() != 2) {
            increaseWhiteMoveState(2);
        } else if (move.getEnd() == 7 && getWhiteCastleMoveState() != 1) {
            increaseWhiteMoveState(1);
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

    public boolean getCanInPassingAttack() {
        return canInPassingAttack;
    }

    public Piece[] getBoard() {
        return board;
    }

    public void printBoard(int position) {
        List<Move> moves = board[position].getMoves(this, position);
        int flip = 1;
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) {
                System.out.print(8 - (i / 8) + " ");
            }
            if ((flip + i) % 2 == 0) {
                System.out.print("\033[40m");
            }
            boolean canMoveHere = false;
            for (Move move : moves) {
                if (move.getEnd() == i && (!move.isPromotionMove() || (move.isPromotionMove() && move.getPiece().isKnight()))) {
                    System.out.print(" x ");
                    canMoveHere = true;
                }
            }
            if (!canMoveHere) {
                System.out.print(" " + board[i].getPiece() + " ");
            }
            if ((flip + i) % 2 == 0) {
                System.out.print("\033[0m");
            }
            if (i % 8 == 7) {
                System.out.println();
                flip++;
            }
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    public void printBoard() {
        int flip = 1;
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) {
                System.out.print(8 - (i / 8) + " ");
            }
            if ((flip + i) % 2 == 0) {
                System.out.print("\033[40m");
            }
            System.out.print(" " + board[i].getPiece() + " ");
            if ((flip + i) % 2 == 0) {
                System.out.print("\033[0m");
            }
            if (i % 8 == 7) {
                System.out.println();
                flip++;
            }
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    public void printBoardSimple() {
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) {
                System.out.print(8 - (i / 8) + " ");
            }
            System.out.print(" " + board[i].getPiece() + " ");
            if (i % 8 == 7) {
                System.out.println();
            }
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    public Piece getPieceAt(int position) {
        return board[position];
    }

    public boolean isColorInMate(Color color) {
        if (!isColorInCheck(color)) {
            return false;
        }
        return getAllMoves(color).isEmpty();
    }

    public boolean isColorInCheck(Color color) {
        int home = -1;
        for (int i = 0; i < 64; i++) {
            Piece piece = board[i];
            if (piece.isKing() && piece.getColor() == color) {
                home = i;
            }
        }
        if (home == -1) {
            return false;
        }
        int checkPosition;
        for (int change : Directions.knights) {
            checkPosition = home + change;
            if (Position.isOnBoard(checkPosition)
                    && Math.abs(Position.getRow(checkPosition) - Position.getRow(home)) < 3
                    && Math.abs(Position.getColumn(checkPosition) - Position.getColumn(home)) < 3) {
                if (getPieceAt(checkPosition).isKnight() && getPieceAt(checkPosition).getColor() != color) {
                    return true;
                }
            }
        }

        for (int change : Directions.orthogonal) {
            checkPosition = home + change;
            while (Position.isOnBoard(checkPosition) && (Position.isSameRow(home, checkPosition) || Position.isSameColumn(home, checkPosition))) {
                if ((getPieceAt(checkPosition).isQueen() || getPieceAt(checkPosition).isRook())
                        && getPieceAt(checkPosition).getColor() != color) {
                    return true;
                }
                if (getPieceAt(checkPosition).getColor() != Color.g) {
                    break;
                }
                checkPosition += change;
            }
        }

        for (int change : Directions.diagonal) {
            checkPosition = home + change;
            while (Position.isOnBoard(checkPosition) && Position.isDiagonal(home, checkPosition)) {
                if ((getPieceAt(checkPosition).isQueen() || getPieceAt(checkPosition).isBishop())
                        && getPieceAt(checkPosition).getColor() != color) {
                    return true;
                }
                if (getPieceAt(checkPosition).getColor() != Color.g) {
                    break;
                }
                checkPosition += change;
            }
        }

        int direction;
        if (color == Color.w)
            direction = -8;
        else
            direction = 8;
        int leftCheckPosition = home - 1 + direction;
        int rightCheckPosition = home + 1 + direction;
        if (Position.isOnBoard(leftCheckPosition) && Position.isDiagonal(home, leftCheckPosition) && getPieceAt(leftCheckPosition).isPawn()
                && getPieceAt(leftCheckPosition).getColor() != color) {
            return true;
        }
        if (Position.isOnBoard(rightCheckPosition) && Position.isDiagonal(home, rightCheckPosition) && getPieceAt(rightCheckPosition).isPawn()
                && getPieceAt(rightCheckPosition).getColor() != color) {
            return true;
        }

        for (int change : Directions.all) {
            checkPosition = home + change;
            if (Position.isOnBoard(checkPosition) && Math.abs(Position.getColumn(home) - Position.getColumn(checkPosition)) < 2) {
                if (getPieceAt(checkPosition).isKing()) {
                    return true;
                }
            }
        }
        return false;
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

    public int decodeInPassingSquare(String fenData) {
        if (fenData.equals("-")) {
            return -1;
        }
        this.canInPassingAttack = true;
        char one = fenData.charAt(0);
        int x = ((int) one) - 97;
        int y = 7 - (Character.getNumericValue(fenData.charAt(1)) - 1);
        return x + (y * 8);
    }

    public void importBoard(String fenBoard) {
        int z = 0;
        String[] splitFen = fenBoard.split(" ");
        fenBoard = splitFen[0];
        setCastleStates(splitFen[2]);
        setInPassingSquare(decodeInPassingSquare(splitFen[3]));
        for (int i = 0; i < fenBoard.length(); i++) {
            if (fenBoard.charAt(i) != '/' && !Character.isDigit(fenBoard.charAt(i))) {
                board[z] = Piece.getPiece(fenBoard.charAt(i));
                z++;
            } else if (Character.isDigit(fenBoard.charAt(i))) {
                for (int j = 0; j < Character.getNumericValue(fenBoard.charAt(i)); j++) {
                    board[z] = Empty.getInstance();
                    z++;
                }
            }
        }
        eval = recomputeEval();
    }


    public void setInPassingSquare(int x) {
        this.inPassingSquare = x;
    }

    public void setCanInPassingAttack(Boolean logic) {
        canInPassingAttack = logic;
    }

    public int getInPassingSquare() {
        return inPassingSquare;
    }

    public void copyBoard(Board board) {
        int i = 0;
        for (Piece piece : board.getBoard()) {
            this.board[i] = piece;
            i++;
        }
    }

    public Board getChild(Move move) {
        Board child = new Board(this);
        child.doMove(move);
        return child;
    }

    public void doPromotionMove(Move move) {
        removePieceUpdateEval(getPieceAt(move.getBeginning()), move.getBeginning());
        removePieceUpdateEval(getPieceAt(move.getEnd()), move.getEnd());
        addPieceUpdateEval(move.getPiece(), move.getEnd());

        board[move.getEnd()] = move.getPiece();
        board[move.getBeginning()] = Empty.getInstance();
        setCanInPassingAttack(false);
    }

    public void doCastleMove(Move move) {
        Piece king = board[move.getBeginning()];
        doNormalMove(move);
        int direction = move.getEnd() - move.getBeginning();
        int rookPosition = (move.getBeginning() + move.getEnd()) / 2;
        int rookStartPosition;
        if (king.getColor() == Color.w) {
            if (direction < 0) {
                rookStartPosition = 56;
            } else {
                rookStartPosition = 63;
            }
        } else {
            if (direction < 0) {
                rookStartPosition = 0;
            } else {
                rookStartPosition = 7;
            }
        }
        Piece rookPiece = getPieceAt(rookStartPosition);
        board[rookPosition] = rookPiece;
        board[rookStartPosition] = Empty.getInstance();
        movePieceUpdateEval(getPieceAt(rookPosition), new Move(rookStartPosition, rookPosition));
    }

    public void doInPassingMove(Move move) {
        int otherPawnPosition = Position.getColumn(move.getEnd()) + (8 * Position.getRow(move.getBeginning()));
        removePieceUpdateEval(getPieceAt(otherPawnPosition), otherPawnPosition);
        movePieceUpdateEval(getPieceAt(move.getBeginning()), move);

        Piece movedPiece = getPieceAt(move.getBeginning());
        board[move.getEnd()] = movedPiece;
        board[move.getBeginning()] = Empty.getInstance();
        board[otherPawnPosition] = Empty.getInstance();
        setCanInPassingAttack(false);
    }

    public void doUpTwoMove(Move move) {
        doNormalMove(move);
        Piece movedPiece = getPieceAt(move.getBeginning());
        int direction;
        if (movedPiece.getColor() == Color.w)
            direction = -8;
        else
            direction = 8;
        int inPassingSquare = move.getBeginning() + direction;
        setInPassingSquare(inPassingSquare);
        setCanInPassingAttack(true);
    }

    public void doNormalMove(Move move) {
        removePieceUpdateEval(getPieceAt(move.getEnd()), move.getEnd());
        movePieceUpdateEval(getPieceAt(move.getBeginning()), move);

        Piece movedPiece = getPieceAt(move.getBeginning());
        board[move.getEnd()] = movedPiece;
        board[move.getBeginning()] = Empty.getInstance();
        setCanInPassingAttack(false);
    }

    public void doMove(Move move) {
        Piece movedPiece = getPieceAt(move.getBeginning());
        updateCastleState(movedPiece, move);
        if (move.isPromotionMove()) {
            doPromotionMove(move);
        } else if (move.isCastleMove(this)) {
            doCastleMove(move);
        } else if (move.isInPassingMove(this)) {
            doInPassingMove(move);
        } else if (move.isUpTwoMove(this)) {
            doUpTwoMove(move);
        } else {
            doNormalMove(move);
        }
    }

    private void removePieceUpdateEval(Piece piece, int position) {
        eval -= piece.getPieceEval();
        eval -= piece.getSquareEval(position);
    }

    private void addPieceUpdateEval(Piece piece, int position) {
        eval += piece.getPieceEval();
        eval += piece.getSquareEval(position);
    }

    private void movePieceUpdateEval(Piece piece, Move move) {
        eval -= piece.getSquareEval(move.getBeginning());
        eval += piece.getSquareEval(move.getEnd());
    }
}
