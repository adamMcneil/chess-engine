package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class King extends Piece {
    public King(Color c, Position position) {
        this.valueTable = new int[] {
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -20,-30,-30,-40,-40,-30,-30,-20,
                -10,-20,-20,-20,-20,-20,-20,-10,
                20, 20,  0,  0,  0,  0, 20, 20,
                20, 30, 10,  0,  0, 10, 30, 20
        };
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265A;
            letter = 'K';
        } else {
            piece = (char) 0x2654;
            letter = 'k';
        }
    }

    @Override
    public boolean isKing() {
        return true;
    }

    public ArrayList<Move> castleMoves(Board board) {
        ArrayList<Move> castleMoves = new ArrayList<>();
        int moveState;
        if (color == Color.w) {
            moveState = board.getWhiteCastleMoveState();
            if ((moveState == 0 || moveState == 2) && !board.isColorInCheck(color)) {
                Position right1 = new Position(5, 7);
                Position right2 = new Position(6, 7);
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color) && board.getPieceAt(move1.getEnd()).getColor()!= Color.b && board.getPieceAt(move2.getEnd()).getColor()!= Color.b) {
                    castleMoves.add(new Move(this.position, new Position(6, 7)));
                }
            }
            if ((moveState == 0 || moveState == 1) && !board.isColorInCheck(color)) {
                Position left1 = new Position(3, 7);
                Position left2 = new Position(2, 7);
                Position left3 = new Position(1, 7);
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color) && board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor()!= Color.b && board.getPieceAt(move2.getEnd()).getColor()!= Color.b) {
                    castleMoves.add(new Move(this.position, new Position(2, 7)));
                }
            }
        } else {
            moveState = board.getBlackCastleMoveState();
            if ((moveState == 0 || moveState == 2) && !board.isColorInCheck(color)) {
                Position right1 = new Position(5, 0);
                Position right2 = new Position(6, 0);
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color) && board.getPieceAt(move1.getEnd()).getColor()!= Color.w && board.getPieceAt(move2.getEnd()).getColor()!= Color.w) {
                    castleMoves.add(new Move(this.position, new Position(6, 0)));
                }
            }
            if ((moveState == 0 || moveState == 1) && !board.isColorInCheck(color)) {
                Position left1 = new Position(3, 0);
                Position left2 = new Position(2, 0);
                Position left3 = new Position(1, 0);
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color) && board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor()!= Color.w && board.getPieceAt(move2.getEnd()).getColor()!= Color.w) {
                    castleMoves.add(new Move(this.position, new Position(2, 0)));
                }
            }
        }
        return castleMoves;
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] baseMoves = {
                {1, 1},
                {1, 0},
                {1, -1},
                {0, -1},
                {-1, -1},
                {-1, 0},
                {-1, 1},
                {0, 1},
        };
        for (int[] arr : baseMoves) {
            Position checkPosition = new Position(position.getX() + arr[0], position.getY() + arr[1]);
            Move move = new Move(position, checkPosition);
            if (move.isMoveLegal(board, color)) {
                moves.add(move);
            }
        }
        moves.addAll(castleMoves(board));

        return moves;
    }

    @Override
    public boolean canMove(Board board) {
        int[][] baseMoves = {
                {1, 1},
                {1, 0},
                {1, -1},
                {0, -1},
                {-1, -1},
                {-1, 0},
                {-1, 1},
                {0, 1},
        };
        for (int[] arr : baseMoves) {
            Position checkPosition = new Position(position.getX() + arr[0], position.getY() + arr[1]);
            Move move = new Move(position, checkPosition);
            if (move.isMoveLegal(board, color)) {
                return true;
            }
        }
        return false;
    }
}
