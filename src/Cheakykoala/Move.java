package Cheakykoala;

public class Move {
    Position beginning;
    Position end;
    public Move(Position beginning, Position end) {
        this.beginning = beginning;
        this.end = end;
    }
    public Position getBeginning(){
        return beginning;
    }
    public Position getEnd(){
        return end;
    }
}