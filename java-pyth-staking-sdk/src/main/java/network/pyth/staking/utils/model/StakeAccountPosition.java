package network.pyth.staking.utils.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StakeAccountPosition {
    @JsonProperty("stakeAmount")
    private long stakeAmount;

    @JsonProperty("activationEpoch")
    private long activationEpoch;

    @JsonProperty("deactivationEpoch")
    private long deactivationEpoch;

    @JsonProperty("voterPubkey")
    private String voterPubkey;

    public long getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(long stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public long getActivationEpoch() {
        return activationEpoch;
    }

    public void setActivationEpoch(long activationEpoch) {
        this.activationEpoch = activationEpoch;
    }

    public long getDeactivationEpoch() {
        return deactivationEpoch;
    }

    public void setDeactivationEpoch(long deactivationEpoch) {
        this.deactivationEpoch = deactivationEpoch;
    }

    public String getVoterPubkey() {
        return voterPubkey;
    }

    public void setVoterPubkey(String voterPubkey) {
        this.voterPubkey = voterPubkey;
    }
} 