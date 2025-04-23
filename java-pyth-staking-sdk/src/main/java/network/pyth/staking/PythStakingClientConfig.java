package network.pyth.staking;

import org.web3j.protocol.Web3j;
import network.pyth.staking.types.PythStakingWallet;

public class PythStakingClientConfig {
    private final Web3j connection;
    private final PythStakingWallet wallet;
    private final String rpcUrl;
    private final String privateKey;
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

    private PythStakingClientConfig(Builder builder) {
        this.connection = builder.connection;
        this.wallet = builder.wallet;
        this.rpcUrl = builder.rpcUrl;
        this.privateKey = builder.privateKey;
        this.programId = builder.programId;
        this.stakingAccount = builder.stakingAccount;
        this.stakingMint = builder.stakingMint;
        this.stakingVault = builder.stakingVault;
        this.governanceProgramId = builder.governanceProgramId;
        this.realm = builder.realm;
        this.governanceMint = builder.governanceMint;
        this.governanceVault = builder.governanceVault;
        this.governanceTokenOwnerRecord = builder.governanceTokenOwnerRecord;
        this.governanceAuthority = builder.governanceAuthority;
    }

    public Web3j getConnection() {
        return connection;
    }

    public PythStakingWallet getWallet() {
        return wallet;
    }

    public String getRpcUrl() {
        return rpcUrl;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getProgramId() {
        return programId;
    }

    public String getStakingAccount() {
        return stakingAccount;
    }

    public String getStakingMint() {
        return stakingMint;
    }

    public String getStakingVault() {
        return stakingVault;
    }

    public String getGovernanceProgramId() {
        return governanceProgramId;
    }

    public String getRealm() {
        return realm;
    }

    public String getGovernanceMint() {
        return governanceMint;
    }

    public String getGovernanceVault() {
        return governanceVault;
    }

    public String getGovernanceTokenOwnerRecord() {
        return governanceTokenOwnerRecord;
    }

    public String getGovernanceAuthority() {
        return governanceAuthority;
    }

    public static class Builder {
        private Web3j connection;
        private PythStakingWallet wallet;
        private String rpcUrl;
        private String privateKey;
        private String programId;
        private String stakingAccount;
        private String stakingMint;
        private String stakingVault;
        private String governanceProgramId;
        private String realm;
        private String governanceMint;
        private String governanceVault;
        private String governanceTokenOwnerRecord;
        private String governanceAuthority;

        public Builder setConnection(Web3j connection) {
            this.connection = connection;
            return this;
        }

        public Builder setWallet(PythStakingWallet wallet) {
            this.wallet = wallet;
            return this;
        }

        public Builder setRpcUrl(String rpcUrl) {
            this.rpcUrl = rpcUrl;
            return this;
        }

        public Builder setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Builder setProgramId(String programId) {
            this.programId = programId;
            return this;
        }

        public Builder setStakingAccount(String stakingAccount) {
            this.stakingAccount = stakingAccount;
            return this;
        }

        public Builder setStakingMint(String stakingMint) {
            this.stakingMint = stakingMint;
            return this;
        }

        public Builder setStakingVault(String stakingVault) {
            this.stakingVault = stakingVault;
            return this;
        }

        public Builder setGovernanceProgramId(String governanceProgramId) {
            this.governanceProgramId = governanceProgramId;
            return this;
        }

        public Builder setRealm(String realm) {
            this.realm = realm;
            return this;
        }

        public Builder setGovernanceMint(String governanceMint) {
            this.governanceMint = governanceMint;
            return this;
        }

        public Builder setGovernanceVault(String governanceVault) {
            this.governanceVault = governanceVault;
            return this;
        }

        public Builder setGovernanceTokenOwnerRecord(String governanceTokenOwnerRecord) {
            this.governanceTokenOwnerRecord = governanceTokenOwnerRecord;
            return this;
        }

        public Builder setGovernanceAuthority(String governanceAuthority) {
            this.governanceAuthority = governanceAuthority;
            return this;
        }

        public PythStakingClientConfig build() {
            return new PythStakingClientConfig(this);
        }
    }
} 