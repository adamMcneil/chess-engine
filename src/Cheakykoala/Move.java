package Cheakykoala;

public class Move {
    Position beginning;
    Position end;
    public Move(Position beginning, Position end) {
        this.beginning = beginning;
        this.end = end;
    }
    public void printMove(){
        System.out.println (beginning.toString() + "        " + end.toString());
    }
    public boolean isMoveCheck(Board board){
        if (board.getPieceAt(end).isKing())
            return true;
        return false;
    }
    public boolean isMoveLegal(Board board){

    }
    public Position getBeginning(){
        return beginning;
    }
    public Position getEnd(){
        return end;
    }
}