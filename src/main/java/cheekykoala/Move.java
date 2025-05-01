package cheekykoala;

import cheekykoala.pieces.Piece;

public class Move {

    private int beginning;
    private int end;
    private MoveType type;
    private Double eval = null;

    private Move(int beginning, int end) {
        this.beginning = beginning;
        this.end = end;
    }

    public Move(Board board, int beginning, int end) {
        assert Position.isOnBoard(beginning) && Position.isOnBoard(end);
        this.beginning = beginning;
        this.end = end;
        this.type = getMoveCode(board, beginning, end);
    }

    public Move(int beginning, int end, MoveType type) {
        assert Position.isOnBoard(beginning) && Position.isOnBoard(end);
        this.beginning = beginning;
        this.end = end;
        this.type = type;
    }

    public MoveType getType() {
        return type;
    }

    public static MoveType getMoveCode(Board board, int start, int end) {
        Move move = new Move(start, end);
        if (move.isPromotionMove()) {
            return MoveType.promotion;
        } else if (move.isCastleMove(board)) {
            return MoveType.castling;
        } else if (move.isInPassingMove(board)) {
            return MoveType.inPassing;
        } else if (move.isUpTwoMove(board)) {
            return MoveType.upTwo;
        } else {
            return MoveType.normal;
        }
    }
    public static Move moveFromString(String string, Board board) {
        Move move;
        int start = (Position.charToInt(string.charAt(0)) + 8 * (8 - Character.getNumericValue(string.charAt(1))));
        int end = (Position.charToInt(string.charAt(2)) + 8 * (8 - Character.getNumericValue(string.charAt(3))));

        if (string.length() == 5) {
            char letter = string.charAt(4);
            Color color = board.getPieceAt(start).getColor();
            if (color == Color.w) {
                letter = Character.toUpperCase(letter);
            }
            move = new PromotionMove(start, end, Piece.getPiece(letter));

        } else {
            move = new Move(board, start, end);
        }
        return move;
    }

    public Piece getPiece() {
        return null;
    }

    public boolean isPromotionMove() {
        return false;
    }

    public boolean isCastleMove(Board board) {
        return (
                (getEnd() - getBeginning() == 2 || getEnd() - getBeginning() == -2)
                        && board.getPieceAt(getBeginning()).isKing()
        );
    }

    public boolean isCapture(Board board) {
        return !board.getPieceAt(this.end).isEmpty();
    }

    public boolean isInPassingMove(Board board) {
        return (Position.getColumn(getEnd()) != Position.getColumn(getBeginning())
                && board.getPieceAt(getBeginning()).isPawn()
                && board.getPieceAt(getEnd()).isEmpty()
        );
    }

    public boolean isUpTwoMove(Board board) {
        return (board.getPieceAt(this.getBeginning()).isPawn()
                && Math.abs(getBeginning() - getEnd()) == 16);
    }

    @Override
    public String toString() {
        return Position.toString(beginning) + Position.toString(end);
    }

    public boolean isMoveLegal(Board board, Color color) {
        if (!Position.isOnBoard(end)) {
            return false;
        }
        Piece moved = board.getPieceAt(beginning);
        Piece taken = board.getPieceAt(end);
        if (moved.isSameColor(taken)) {
            return false;
        }
        Board checkBoard = board.getChild(this); // TODO: we could just undo the move here
        if (checkBoard.isColorInCheck(color)) {
            return false;
        }
        return true;
    }

    public double recomputeEval(Board board) {
        Piece movedPiece = board.getPieceAt(getBeginning());
        Piece takenPiece = board.getPieceAt(getEnd());
        double eval = 0;
        if (type == MoveType.normal || type == MoveType.upTwo) {
            eval += removePieceUpdateEval(takenPiece, getEnd());
            eval += movePieceUpdateEval(movedPiece);
        } else if (type == MoveType.castling) {
            if (movedPiece.getColor() == Color.w) {
                eval += 15;
            } else {
                eval -= 15;
            }
        } else if (type == MoveType.promotion) {
            eval += removePieceUpdateEval(movedPiece, getBeginning());
            eval += removePieceUpdateEval(takenPiece, getEnd());
            eval += addPieceUpdateEval(getPiece(), getEnd());
        } else if (type == MoveType.inPassing) {
            int otherPawnPosition = Position.getColumn(getEnd()) + (8 * Position.getRow(getBeginning()));
            takenPiece = board.getPieceAt(otherPawnPosition);
            eval += removePieceUpdateEval(takenPiece, otherPawnPosition);
            eval += movePieceUpdateEval(movedPiece);
        } else {
            throw new RuntimeException("Move type is null");
        }
        this.eval = eval;
        return eval;
    }

    private double removePieceUpdateEval(Piece piece, int position) {
        double eval = -piece.getPieceEval();
        eval -= piece.getSquareEval(position);
        return eval;
    }

    private double addPieceUpdateEval(Piece piece, int position) {
        double eval = piece.getPieceEval();
        eval += piece.getSquareEval(position);
        return eval;
    }

    private double movePieceUpdateEval(Piece piece) {
        double eval = -piece.getSquareEval(getBeginning());
        eval += piece.getSquareEval(getEnd());
        return eval;
    }
    public double getEval(Board board) {
        if (eval == null) {
            recomputeEval(board);
        }
        return eval;
    }

    public double getEval() {
        assert eval != null;
        return eval;
    }

    public int getBeginning() {
        return beginning;
    }

    public int getEnd() {
        return end;
    }
}
