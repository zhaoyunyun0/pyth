package network.pyth.staking.types;

public class PositionWithIndex {
    private final Position position;
    private final int index;

    public PositionWithIndex(Position position, int index) {
        this.position = position;
        this.index = index;
    }

    public Position getPosition() {
        return position;
    }

    public int getIndex() {
        return index;
    }
} 