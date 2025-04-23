package network.pyth.staking.types;

import java.math.BigInteger;
import java.util.List;

public class PublisherData {
    private final String pubkey;
    private final String stakeAccount;
    private final BigInteger totalDelegation;
    private final BigInteger totalDelegationDelta;
    private final BigInteger selfDelegation;
    private final BigInteger selfDelegationDelta;
    private final BigInteger delegationFee;
    private final List<ApyHistoryEntry> apyHistory;

    public PublisherData(String pubkey, String stakeAccount, BigInteger totalDelegation,
                        BigInteger totalDelegationDelta, BigInteger selfDelegation,
                        BigInteger selfDelegationDelta, BigInteger delegationFee,
                        List<ApyHistoryEntry> apyHistory) {
        this.pubkey = pubkey;
        this.stakeAccount = stakeAccount;
        this.totalDelegation = totalDelegation;
        this.totalDelegationDelta = totalDelegationDelta;
        this.selfDelegation = selfDelegation;
        this.selfDelegationDelta = selfDelegationDelta;
        this.delegationFee = delegationFee;
        this.apyHistory = apyHistory;
    }

    public String getPubkey() {
        return pubkey;
    }

    public String getStakeAccount() {
        return stakeAccount;
    }

    public BigInteger getTotalDelegation() {
        return totalDelegation;
    }

    public BigInteger getTotalDelegationDelta() {
        return totalDelegationDelta;
    }

    public BigInteger getSelfDelegation() {
        return selfDelegation;
    }

    public BigInteger getSelfDelegationDelta() {
        return selfDelegationDelta;
    }

    public BigInteger getDelegationFee() {
        return delegationFee;
    }

    public List<ApyHistoryEntry> getApyHistory() {
        return apyHistory;
    }

    public static class ApyHistoryEntry {
        private final BigInteger epoch;
        private final double apy;
        private final double selfApy;

        public ApyHistoryEntry(BigInteger epoch, double apy, double selfApy) {
            this.epoch = epoch;
            this.apy = apy;
            this.selfApy = selfApy;
        }

        public BigInteger getEpoch() {
            return epoch;
        }

        public double getApy() {
            return apy;
        }

        public double getSelfApy() {
            return selfApy;
        }
    }
} 