package network.pyth.staking;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public interface PythStakingWallet {
    String getAddress();
    CompletableFuture<BigInteger> getBalance();
    CompletableFuture<String> signMessage(String message);
    CompletableFuture<String> sendTransaction(String transaction);
} 