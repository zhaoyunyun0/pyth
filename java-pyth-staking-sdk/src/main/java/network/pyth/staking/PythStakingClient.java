package network.pyth.staking;

import network.pyth.staking.types.*;
import network.pyth.staking.utils.SolanaUtils;
import network.pyth.staking.utils.SolanaClient;
import network.pyth.staking.utils.model.StakeAccountPosition;
import network.pyth.staking.utils.model.Transaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.io.IOException;

public class PythStakingClient {
    private final Web3j connection;
    private final network.pyth.staking.types.PythStakingWallet wallet;
    private final SolanaClient solanaClient;
    private final String programId;
    private final String stakingAccount;
    private final String stakingMint;
    private final String stakingVault;
    private final String governanceProgramId;
    private final String realm;
    private final String governanceMint;
    private final String governanceVault;
    private final String governanceTokenOwnerRecord;
    private final String governanceAuthority;

    public PythStakingClient(PythStakingClientConfig config) {
        this.connection = config.getConnection();
        this.wallet = config.getWallet();
        this.solanaClient = new SolanaClient(config.getRpcUrl(), config.getPrivateKey());
        this.programId = config.getProgramId();
        this.stakingAccount = config.getStakingAccount();
        this.stakingMint = config.getStakingMint();
        this.stakingVault = config.getStakingVault();
        this.governanceProgramId = config.getGovernanceProgramId();
        this.realm = config.getRealm();
        this.governanceMint = config.getGovernanceMint();
        this.governanceVault = config.getGovernanceVault();
        this.governanceTokenOwnerRecord = config.getGovernanceTokenOwnerRecord();
        this.governanceAuthority = config.getGovernanceAuthority();
    }

    public CompletableFuture<GlobalConfig> getGlobalConfig() {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<List<String>> getAllStakeAccountPositions(String owner) {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<StakeAccountPositions> getStakeAccountPositions(String stakeAccountAddress) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Get raw stake positions from Solana
                List<StakeAccountPosition> positions = solanaClient.getStakeAccountPositions(stakeAccountAddress);
                
                // Convert to StakeAccountPositions object
                StakeAccountPositions result = new StakeAccountPositions();
                result.setStakeAccountAddress(stakeAccountAddress);
                
                // Convert each position to Position object
                List<Position> convertedPositions = positions.stream()
                    .map(position -> {
                        Position p = new Position();
                        p.setAmount(BigInteger.valueOf(position.getStakeAmount()));
                        p.setActivationEpoch(BigInteger.valueOf(position.getActivationEpoch()));
                        p.setDeactivationEpoch(BigInteger.valueOf(position.getDeactivationEpoch()));
                        p.setVoterPubkey(position.getVoterPubkey());
                        return p;
                    })
                    .collect(Collectors.toList());
                
                result.setPositions(convertedPositions);
                return result;
            } catch (IOException e) {
                throw new RuntimeException("Failed to get stake account positions", e);
            }
        });
    }

    public CompletableFuture<TransactionReceipt> stakeToGovernance(String stakeAccountPositions, BigInteger amount) {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<TransactionReceipt> unstakeFromGovernance(
            String stakeAccountPositions,
            PositionState positionState,
            BigInteger amount) {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<TransactionReceipt> stakeToPublisher(
            String stakeAccountPositions,
            String publisher,
            BigInteger amount) {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<TransactionReceipt> unstakeFromPublisher(
            String stakeAccountPositions,
            String publisher,
            PositionState positionState,
            BigInteger amount) {
        return getStakeAccountPositions(stakeAccountPositions)
                .thenCompose(stakeAccountPositionsData -> {
                    // 获取当前epoch
                    return getCurrentEpoch()
                            .thenCompose(currentEpoch -> {
                                BigInteger remainingAmount = amount;
                                List<CompletableFuture<String>> instructionPromises = new ArrayList<>();

                                // 获取符合条件的positions
                                List<Position> positions = stakeAccountPositionsData.getData().getPositions();
                                List<PositionWithIndex> eligiblePositions = IntStream.range(0, positions.size())
                                        .mapToObj(i -> new PositionWithIndex(positions.get(i), i))
                                        .collect(Collectors.toList());

                                // 反转列表以从后往前处理
                                java.util.Collections.reverse(eligiblePositions);

                                // 过滤符合条件的positions
                                eligiblePositions = eligiblePositions.stream()
                                        .filter(pwi -> {
                                            Position position = pwi.getPosition();
                                            return position.getTargetWithParameters() != null &&
                                                    position.getTargetWithParameters().getIntegrityPool() != null &&
                                                    position.getTargetWithParameters().getIntegrityPool().getPublisher()
                                                            .equals(publisher) &&
                                                    positionState == getPositionState(position, currentEpoch);
                                        })
                                        .collect(Collectors.toList());

                                // 处理每个符合条件的position
                                for (PositionWithIndex pwi : eligiblePositions) {
                                    Position position = pwi.getPosition();
                                    if (position.getAmount().compareTo(remainingAmount) < 0) {
                                        // 如果position的amount小于remainingAmount，全部解质押
                                        instructionPromises.add(createUndelegateInstruction(
                                                stakeAccountPositions,
                                                publisher,
                                                pwi.getIndex(),
                                                position.getAmount()));
                                        remainingAmount = remainingAmount.subtract(position.getAmount());
                                    } else {
                                        // 如果position的amount大于等于remainingAmount，只解质押需要的数量
                                        instructionPromises.add(createUndelegateInstruction(
                                                stakeAccountPositions,
                                                publisher,
                                                pwi.getIndex(),
                                                remainingAmount));
                                        break;
                                    }
                                }

                                // 等待所有指令创建完成
                                return CompletableFuture.allOf(
                                        instructionPromises.toArray(new CompletableFuture[0]))
                                        .thenApply(v -> instructionPromises.stream()
                                                .map(CompletableFuture::join)
                                                .collect(Collectors.toList()))
                                        .thenCompose(instructions -> sendTransaction(instructions));
                            });
                });
    }

    private CompletableFuture<BigInteger> getCurrentEpoch() {
        return SolanaUtils.getCurrentEpoch();
    }

    private PositionState getPositionState(Position position, BigInteger currentEpoch) {
        BigInteger lockupExpiry = position.getLockupExpiry();
        BigInteger unlockStart = position.getUnlockStart();
        BigInteger unlockDuration = position.getUnlockDuration();

        if (lockupExpiry.compareTo(currentEpoch) <= 0) {
            return PositionState.UNLOCKED;
        }

        if (unlockStart.compareTo(BigInteger.ZERO) > 0) {
            if (currentEpoch.compareTo(unlockStart) < 0) {
                return PositionState.LOCKED;
            } else if (currentEpoch.compareTo(unlockStart.add(unlockDuration)) < 0) {
                return PositionState.UNLOCKING;
            } else {
                return PositionState.UNLOCKED;
            }
        }

        return PositionState.LOCKING;
    }

    private CompletableFuture<String> createUndelegateInstruction(
            String stakeAccountPositions,
            String publisher,
            int index,
            BigInteger amount) {
        return CompletableFuture.completedFuture(
                SolanaUtils.createUndelegateInstruction(
                        stakeAccountPositions,
                        publisher,
                        index,
                        amount));
    }

    private CompletableFuture<TransactionReceipt> sendTransaction(List<String> instructions) {
        return SolanaUtils.sendTransaction(instructions)
                .thenCompose(txHash -> {
                    // 等待交易确认
                    return CompletableFuture.supplyAsync(() -> {
                        // TODO: 实现交易确认的逻辑
                        return new TransactionReceipt();
                    });
                });
    }

    public CompletableFuture<UnlockSchedule> getUnlockSchedule(
            String stakeAccountPositions,
            boolean includePastPeriods) {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<BigInteger> getCirculatingSupply() {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<BigInteger> getVoterWeight(String owner) {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<List<PublisherData>> getAllStakeAccountPositionsAllOwners() {
        // TODO: Implement
        return CompletableFuture.completedFuture(null);
    }

    public String stake(BigInteger amount) throws IOException {
        List<String> accounts = new ArrayList<>();
        accounts.add(stakingAccount);
        accounts.add(stakingMint);
        accounts.add(stakingVault);
        accounts.add(governanceProgramId);
        accounts.add(realm);
        accounts.add(governanceMint);
        accounts.add(governanceVault);
        accounts.add(governanceTokenOwnerRecord);
        accounts.add(governanceAuthority);

        // Convert amount to bytes (assuming 8 bytes for u64)
        byte[] amountBytes = new byte[8];
        byte[] amountArray = amount.toByteArray();
        System.arraycopy(amountArray, 0, amountBytes, 8 - amountArray.length, amountArray.length);

        Transaction transaction = solanaClient.buildTransaction(
            programId,
            accounts,
            "stake" + new String(amountBytes)
        );

        return solanaClient.signAndSendTransaction(transaction);
    }

    public String unstake(BigInteger amount) throws IOException {
        List<String> accounts = new ArrayList<>();
        accounts.add(stakingAccount);
        accounts.add(stakingMint);
        accounts.add(stakingVault);
        accounts.add(governanceProgramId);
        accounts.add(realm);
        accounts.add(governanceMint);
        accounts.add(governanceVault);
        accounts.add(governanceTokenOwnerRecord);
        accounts.add(governanceAuthority);

        // Convert amount to bytes (assuming 8 bytes for u64)
        byte[] amountBytes = new byte[8];
        byte[] amountArray = amount.toByteArray();
        System.arraycopy(amountArray, 0, amountBytes, 8 - amountArray.length, amountArray.length);

        Transaction transaction = solanaClient.buildTransaction(
            programId,
            accounts,
            "unstake" + new String(amountBytes)
        );

        return solanaClient.signAndSendTransaction(transaction);
    }

    public String claimRewards() throws IOException {
        List<String> accounts = new ArrayList<>();
        accounts.add(stakingAccount);
        accounts.add(stakingMint);
        accounts.add(stakingVault);
        accounts.add(governanceProgramId);
        accounts.add(realm);
        accounts.add(governanceMint);
        accounts.add(governanceVault);
        accounts.add(governanceTokenOwnerRecord);
        accounts.add(governanceAuthority);

        Transaction transaction = solanaClient.buildTransaction(
            programId,
            accounts,
            "claim_rewards"
        );

        return solanaClient.signAndSendTransaction(transaction);
    }

    public String vote(String proposal, boolean approve) throws IOException {
        List<String> accounts = new ArrayList<>();
        accounts.add(stakingAccount);
        accounts.add(stakingMint);
        accounts.add(stakingVault);
        accounts.add(governanceProgramId);
        accounts.add(realm);
        accounts.add(governanceMint);
        accounts.add(governanceVault);
        accounts.add(governanceTokenOwnerRecord);
        accounts.add(governanceAuthority);
        accounts.add(proposal);

        Transaction transaction = solanaClient.buildTransaction(
            programId,
            accounts,
            "vote" + (approve ? "1" : "0")
        );

        return solanaClient.signAndSendTransaction(transaction);
    }
} 