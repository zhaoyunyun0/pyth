package network.pyth.staking.utils.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EpochInfo {
    @JsonProperty("epoch")
    private long epoch;

    @JsonProperty("slotIndex")
    private long slotIndex;

    @JsonProperty("slotsInEpoch")
    private long slotsInEpoch;

    @JsonProperty("absoluteSlot")
    private long absoluteSlot;

    @JsonProperty("blockHeight")
    private long blockHeight;

    @JsonProperty("transactionCount")
    private long transactionCount;

    public long getEpoch() {
        return epoch;
    }

    public long getSlotIndex() {
        return slotIndex;
    }

    public long getSlotsInEpoch() {
        return slotsInEpoch;
    }

    public long getAbsoluteSlot() {
        return absoluteSlot;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public long getTransactionCount() {
        return transactionCount;
    }
} 