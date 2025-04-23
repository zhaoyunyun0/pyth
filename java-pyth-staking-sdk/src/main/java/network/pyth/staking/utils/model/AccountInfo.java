package network.pyth.staking.utils.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountInfo {
    @JsonProperty("lamports")
    private long lamports;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("data")
    private String data;

    @JsonProperty("executable")
    private boolean executable;

    @JsonProperty("rentEpoch")
    private long rentEpoch;

    public long getLamports() {
        return lamports;
    }

    public void setLamports(long lamports) {
        this.lamports = lamports;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isExecutable() {
        return executable;
    }

    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    public long getRentEpoch() {
        return rentEpoch;
    }

    public void setRentEpoch(long rentEpoch) {
        this.rentEpoch = rentEpoch;
    }
} 