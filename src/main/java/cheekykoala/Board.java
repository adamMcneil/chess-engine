package cheekykoala;

import cheekykoala.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
    private final Piece[] board = new Piece[64];
    private int inPassingSquare = -1;
    private boolean canInPassingAttack = false;
    private boolean whiteCanCastleKingSide = true;
    private boolean whiteCanCastleQueenSide = true;
    private boolean blackCanCastleKingSide = true;
    private boolean blackCanCastleQueenSide = true;

    public boolean isWhiteCanCastleKingSide() {
        return whiteCanCastleKingSide;
    }

    public boolean isWhiteCanCastleQueenSide() {
        return whiteCanCastleQueenSide;
    }

    public boolean isBlackCanCastleKingSide() {
        return blackCanCastleKingSide;
    }

    public boolean isBlackCanCastleQueenSide() {
        return blackCanCastleQueenSide;
    }

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
        this.whiteCanCastleKingSide = other.whiteCanCastleKingSide;
        this.whiteCanCastleQueenSide = other.whiteCanCastleQueenSide;
        this.blackCanCastleKingSide = other.blackCanCastleKingSide;
        this.blackCanCastleQueenSide = other.blackCanCastleQueenSide;
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
        return recomputeEval();
    }

    public List<Move> getAllMoves(Color color) {
        return getMoves(color, move -> move.isMoveLegal(this, color));
    }

    public List<Move> getMoves(Color color, Predicate<Move> filter) {
        ArrayList<Move> moves = new ArrayList<>();
        int position = 0;
        for (Piece piece : board) {
            if (piece.getColor() == color) {
                moves.addAll(piece.getMoves(this, position, filter));
            }
            position++;
        }
        return moves;
    }

    public void updateCastleState(Piece piece, Move move) {
        if (piece.isKing()) {
            if (piece.getColor() == Color.w) {
                whiteCanCastleKingSide = false;
                whiteCanCastleQueenSide = false;
            } else {
                blackCanCastleKingSide = false;
                blackCanCastleQueenSide = false;
            }
        }
        if (piece.isRook()) {
            if (piece.getColor() == Color.w) {
                if (move.getBeginning() == 56) {
                    whiteCanCastleQueenSide = false;
                } else if (move.getBeginning() == 63) {
                    whiteCanCastleKingSide = false;
                }
            }
            if (piece.getColor() == Color.b) {
                if (move.getBeginning() == 0) {
                    blackCanCastleQueenSide = false;
                } else if (move.getBeginning() == 7) {
                    blackCanCastleKingSide = false;
                }
            }
        }
        if (move.getEnd() == 56) {
            whiteCanCastleQueenSide = false;
        } else if (move.getEnd() == 63) {
            whiteCanCastleKingSide = false;
        } else if (move.getEnd() == 0) {
            blackCanCastleQueenSide = false;
        } else if (move.getEnd() == 7) {
        blackCanCastleKingSide = false;
        }
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
        assert Position.isOnBoard(position);
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
        whiteCanCastleKingSide = false;
        whiteCanCastleQueenSide = false;
        blackCanCastleKingSide = false;
        blackCanCastleQueenSide = false;
        for (char c : fenCastleData.toCharArray()) {
            switch (c) {
                case 'K':
                    whiteCanCastleKingSide = true;
                    break;
                case 'Q':
                    whiteCanCastleQueenSide = true;
                    break;
                case 'k':
                    blackCanCastleKingSide = true;
                    break;
                case 'q':
                    blackCanCastleQueenSide = true;
                    break;
            }
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
        movePieceUpdateEval(getPieceAt(rookPosition), new Move(rookStartPosition, rookPosition, MoveType.normal));
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
        Piece movedPiece = getPieceAt(move.getEnd());
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
        MoveType type = move.getType();
        updateCastleState(movedPiece, move);
        if (type == MoveType.promotion) {
            doPromotionMove(move);
        } else if (type == MoveType.castling) {
            doCastleMove(move);
        } else if (type == MoveType.inPassing) {
            doInPassingMove(move);
        } else if (type == MoveType.upTwo) {
            doUpTwoMove(move);
        } else if (type == MoveType.normal){
            doNormalMove(move);
        } else {
            throw new RuntimeException("Move type not is null");
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
