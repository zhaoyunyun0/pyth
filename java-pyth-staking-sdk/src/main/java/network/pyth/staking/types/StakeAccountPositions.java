package network.pyth.staking.types;

import java.util.List;

public class StakeAccountPositions {
    private String stakeAccountAddress;
    private List<Position> positions;

    public String getStakeAccountAddress() {
        return stakeAccountAddress;
    }

    public void setStakeAccountAddress(String stakeAccountAddress) {
        this.stakeAccountAddress = stakeAccountAddress;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
} 