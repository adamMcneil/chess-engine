package cheekykoala;

public class MoveCounter {

    public int nodes = 0;
    public int captures = 0;
    public int inPassing = 0;
    public int castles = 0;
    public int promotions = 0;
    public int check = 0;
    public int checkMates = 0;

    void Add(MoveCounter moveCounter) {
        nodes += moveCounter.nodes;
        captures += moveCounter.captures;
        inPassing += moveCounter.inPassing;
        castles += moveCounter.castles;
        promotions += moveCounter.promotions;
        check += moveCounter.check;
        checkMates += moveCounter.checkMates;
    }
    @Override
    public String toString() {
        return String.format(
                "MoveCounter { " +
                        "nodes = %d, " +
                        "captures = %d, " +
                        "enPassant = %d, " +
                        "castles = %d, " +
                        "promotions = %d, " +
                        "checks = %d, " +
                        "checkMates = %d }",
                nodes, captures, inPassing, castles, promotions, check, checkMates
        );
    }
}

