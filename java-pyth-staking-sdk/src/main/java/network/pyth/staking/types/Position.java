package network.pyth.staking.types;

import java.math.BigInteger;

public class Position {
    private BigInteger amount;
    private BigInteger activationEpoch;
    private BigInteger deactivationEpoch;
    private String voterPubkey;
    private BigInteger lockupExpiry;
    private BigInteger unlockStart;
    private BigInteger unlockDuration;
    private TargetWithParameters targetWithParameters;

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getActivationEpoch() {
        return activationEpoch;
    }

    public void setActivationEpoch(BigInteger activationEpoch) {
        this.activationEpoch = activationEpoch;
    }

    public BigInteger getDeactivationEpoch() {
        return deactivationEpoch;
    }

    public void setDeactivationEpoch(BigInteger deactivationEpoch) {
        this.deactivationEpoch = deactivationEpoch;
    }

    public String getVoterPubkey() {
        return voterPubkey;
    }

    public void setVoterPubkey(String voterPubkey) {
        this.voterPubkey = voterPubkey;
    }

    public BigInteger getLockupExpiry() {
        return lockupExpiry;
    }

    public void setLockupExpiry(BigInteger lockupExpiry) {
        this.lockupExpiry = lockupExpiry;
    }

    public BigInteger getUnlockStart() {
        return unlockStart;
    }

    public void setUnlockStart(BigInteger unlockStart) {
        this.unlockStart = unlockStart;
    }

    public BigInteger getUnlockDuration() {
        return unlockDuration;
    }

    public void setUnlockDuration(BigInteger unlockDuration) {
        this.unlockDuration = unlockDuration;
    }

    public TargetWithParameters getTargetWithParameters() {
        return targetWithParameters;
    }

    public void setTargetWithParameters(TargetWithParameters targetWithParameters) {
        this.targetWithParameters = targetWithParameters;
    }

    public static class TargetWithParameters {
        private IntegrityPool integrityPool;

        public IntegrityPool getIntegrityPool() {
            return integrityPool;
        }

        public void setIntegrityPool(IntegrityPool integrityPool) {
            this.integrityPool = integrityPool;
        }

        public static class IntegrityPool {
            private String publisher;

            public String getPublisher() {
                return publisher;
            }

            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }
        }
    }
} 