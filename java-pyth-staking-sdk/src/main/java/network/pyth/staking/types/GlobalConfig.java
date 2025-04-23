package network.pyth.staking.types;

import java.math.BigInteger;

public class GlobalConfig {
    private final String authority;
    private final String pythTokenMint;
    private final String rewardCustody;
    private final BigInteger rewardRate;
    private final BigInteger rewardRateDenominator;
    private final BigInteger minStake;
    private final BigInteger minLockupDuration;
    private final BigInteger maxLockupDuration;
    private final BigInteger maxPositionsPerAccount;
    private final BigInteger maxUnstakeBatchSize;
    private final BigInteger maxUnstakeBatchDuration;
    private final BigInteger maxVoterWeight;
    private final BigInteger fractionPrecisionN;
    private final BigInteger oneYearInSeconds;
    private final BigInteger positionsAccountSize;

    public GlobalConfig(String authority, String pythTokenMint, String rewardCustody,
                       BigInteger rewardRate, BigInteger rewardRateDenominator,
                       BigInteger minStake, BigInteger minLockupDuration,
                       BigInteger maxLockupDuration, BigInteger maxPositionsPerAccount,
                       BigInteger maxUnstakeBatchSize, BigInteger maxUnstakeBatchDuration,
                       BigInteger maxVoterWeight, BigInteger fractionPrecisionN,
                       BigInteger oneYearInSeconds, BigInteger positionsAccountSize) {
        this.authority = authority;
        this.pythTokenMint = pythTokenMint;
        this.rewardCustody = rewardCustody;
        this.rewardRate = rewardRate;
        this.rewardRateDenominator = rewardRateDenominator;
        this.minStake = minStake;
        this.minLockupDuration = minLockupDuration;
        this.maxLockupDuration = maxLockupDuration;
        this.maxPositionsPerAccount = maxPositionsPerAccount;
        this.maxUnstakeBatchSize = maxUnstakeBatchSize;
        this.maxUnstakeBatchDuration = maxUnstakeBatchDuration;
        this.maxVoterWeight = maxVoterWeight;
        this.fractionPrecisionN = fractionPrecisionN;
        this.oneYearInSeconds = oneYearInSeconds;
        this.positionsAccountSize = positionsAccountSize;
    }

    public String getAuthority() {
        return authority;
    }

    public String getPythTokenMint() {
        return pythTokenMint;
    }

    public String getRewardCustody() {
        return rewardCustody;
    }

    public BigInteger getRewardRate() {
        return rewardRate;
    }

    public BigInteger getRewardRateDenominator() {
        return rewardRateDenominator;
    }

    public BigInteger getMinStake() {
        return minStake;
    }

    public BigInteger getMinLockupDuration() {
        return minLockupDuration;
    }

    public BigInteger getMaxLockupDuration() {
        return maxLockupDuration;
    }

    public BigInteger getMaxPositionsPerAccount() {
        return maxPositionsPerAccount;
    }

    public BigInteger getMaxUnstakeBatchSize() {
        return maxUnstakeBatchSize;
    }

    public BigInteger getMaxUnstakeBatchDuration() {
        return maxUnstakeBatchDuration;
    }

    public BigInteger getMaxVoterWeight() {
        return maxVoterWeight;
    }

    public BigInteger getFractionPrecisionN() {
        return fractionPrecisionN;
    }

    public BigInteger getOneYearInSeconds() {
        return oneYearInSeconds;
    }

    public BigInteger getPositionsAccountSize() {
        return positionsAccountSize;
    }
} 