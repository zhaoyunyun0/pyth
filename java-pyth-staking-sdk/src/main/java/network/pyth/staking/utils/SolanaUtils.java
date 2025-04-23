package network.pyth.staking.utils;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SolanaUtils {
    private static final SolanaClient solanaClient = new SolanaClient();

    public static CompletableFuture<BigInteger> getCurrentEpoch() {
        return solanaClient.getEpochInfo();
    }

    public static String createUndelegateInstruction(
            String stakeAccountPositions,
            String publisher,
            int index,
            BigInteger amount) {
        // 构建解质押指令
        // 这里需要根据 Solana 的指令格式构建
        return String.format(
                "{\"programId\":\"pyth_staking\",\"accounts\":[{\"pubkey\":\"%s\",\"isSigner\":false,\"isWritable\":true},{\"pubkey\":\"%s\",\"isSigner\":false,\"isWritable\":true}],\"data\":{\"index\":%d,\"amount\":\"%s\"}}",
                stakeAccountPositions,
                publisher,
                index,
                amount.toString());
    }

    public static CompletableFuture<String> sendTransaction(List<String> instructions) {
        return solanaClient.sendTransaction(instructions);
    }
} 