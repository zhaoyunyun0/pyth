package network.pyth.staking.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.pyth.staking.utils.model.*;
import okhttp3.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

public class SolanaClient {
    private final String rpcUrl;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Credentials credentials;

    public SolanaClient(String rpcUrl, String privateKey) {
        this.rpcUrl = rpcUrl;
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        this.credentials = Credentials.create(privateKey);
    }

    public String getRecentBlockhash() throws IOException {
        Request request = new Request.Builder()
                .url(rpcUrl)
                .post(RequestBody.create(
                        MediaType.parse("application/json"),
                        "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getRecentBlockhash\"}"
                ))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }

            String responseBody = response.body().string();
            return objectMapper.readTree(responseBody)
                    .path("result")
                    .path("value")
                    .path("blockhash")
                    .asText();
        }
    }

    public Transaction buildTransaction(String programId, List<String> accounts, String data) throws IOException {
        Transaction transaction = new Transaction();
        transaction.setRecentBlockhash(getRecentBlockhash());

        Transaction.Instruction instruction = new Transaction.Instruction();
        instruction.setProgramId(programId);
        instruction.setAccounts(accounts);
        instruction.setData(data);

        List<Transaction.Instruction> instructions = new ArrayList<>();
        instructions.add(instruction);
        transaction.setInstructions(instructions);

        return transaction;
    }

    public String signAndSendTransaction(Transaction transaction) throws IOException {
        String serializedTransaction = objectMapper.writeValueAsString(transaction);
        byte[] message = serializedTransaction.getBytes();

        Sign.SignatureData signature = Sign.signMessage(message, ECKeyPair.create(credentials.getEcKeyPair().getPrivateKey()), false);
        String signatureHex = Numeric.toHexString(signature.getR())
                + Numeric.toHexString(signature.getS()).substring(2);

        Request request = new Request.Builder()
                .url(rpcUrl)
                .post(RequestBody.create(
                        MediaType.parse("application/json"),
                        String.format(
                                "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"sendTransaction\",\"params\":[\"%s\",{\"encoding\":\"base58\"}]}",
                                signatureHex
                        )
                ))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }

            return response.body().string();
        }
    }

    public List<StakeAccountPosition> getStakeAccountPositions(String stakeAccountAddress) throws IOException {
        Request request = new Request.Builder()
                .url(rpcUrl)
                .post(RequestBody.create(
                        MediaType.parse("application/json"),
                        String.format(
                                "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"getAccountInfo\",\"params\":[\"%s\",{\"encoding\":\"base64\"}]}",
                                stakeAccountAddress
                        )
                ))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }

            String responseBody = response.body().string();
            RpcResponse<AccountInfo> rpcResponse = objectMapper.readValue(
                    responseBody,
                    objectMapper.getTypeFactory().constructParametricType(
                            RpcResponse.class,
                            AccountInfo.class
                    )
            );

            if (rpcResponse.getError() != null) {
                throw new IOException("RPC error: " + rpcResponse.getError().getMessage());
            }

            AccountInfo accountInfo = rpcResponse.getResult();
            if (accountInfo == null) {
                throw new IOException("Account not found");
            }

            // Parse the account data to get stake positions
            List<StakeAccountPosition> positions = new ArrayList<>();
            
            // Decode the base64 data
            byte[] decodedData = Base64.getDecoder().decode(accountInfo.getData());
            
            // The stake account data structure in Solana is:
            // - 4 bytes: version
            // - 4 bytes: state
            // - 8 bytes: rent exempt reserve
            // - 8 bytes: stake amount
            // - 8 bytes: activation epoch
            // - 8 bytes: deactivation epoch
            // - 32 bytes: voter pubkey
            
            if (decodedData.length >= 72) { // Minimum length for a valid stake account
                StakeAccountPosition position = new StakeAccountPosition();
                
                // Parse stake amount (8 bytes starting at offset 16)
                long stakeAmount = 0;
                for (int i = 0; i < 8; i++) {
                    stakeAmount |= (decodedData[16 + i] & 0xFFL) << (i * 8);
                }
                position.setStakeAmount(stakeAmount);
                
                // Parse activation epoch (8 bytes starting at offset 24)
                long activationEpoch = 0;
                for (int i = 0; i < 8; i++) {
                    activationEpoch |= (decodedData[24 + i] & 0xFFL) << (i * 8);
                }
                position.setActivationEpoch(activationEpoch);
                
                // Parse deactivation epoch (8 bytes starting at offset 32)
                long deactivationEpoch = 0;
                for (int i = 0; i < 8; i++) {
                    deactivationEpoch |= (decodedData[32 + i] & 0xFFL) << (i * 8);
                }
                position.setDeactivationEpoch(deactivationEpoch);
                
                // Parse voter pubkey (32 bytes starting at offset 40)
                byte[] voterPubkeyBytes = new byte[32];
                System.arraycopy(decodedData, 40, voterPubkeyBytes, 0, 32);
                position.setVoterPubkey(Numeric.toHexString(voterPubkeyBytes));
                
                positions.add(position);
            }
            
            return positions;
        }
    }
} 