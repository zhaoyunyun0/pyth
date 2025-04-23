package network.pyth.staking.utils.model;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String recentBlockhash;
    private List<Instruction> instructions = new ArrayList<>();

    public static class Instruction {
        private String programId;
        private List<String> accounts;
        private String data;

        public String getProgramId() {
            return programId;
        }

        public void setProgramId(String programId) {
            this.programId = programId;
        }

        public List<String> getAccounts() {
            return accounts;
        }

        public void setAccounts(List<String> accounts) {
            this.accounts = accounts;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public String getRecentBlockhash() {
        return recentBlockhash;
    }

    public void setRecentBlockhash(String recentBlockhash) {
        this.recentBlockhash = recentBlockhash;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
} 