package network.pyth.staking.types;

import org.web3j.crypto.Credentials;

public class PythStakingWallet {
    private final Credentials credentials;
    private final String address;

    public PythStakingWallet(Credentials credentials) {
        this.credentials = credentials;
        this.address = credentials.getAddress();
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getAddress() {
        return address;
    }
} 