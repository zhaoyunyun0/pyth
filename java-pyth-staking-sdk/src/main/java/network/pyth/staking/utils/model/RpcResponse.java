package network.pyth.staking.utils.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RpcResponse<T> {
    @JsonProperty("jsonrpc")
    private String jsonrpc;

    @JsonProperty("id")
    private int id;

    @JsonProperty("result")
    private T result;

    @JsonProperty("error")
    private RpcError error;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public int getId() {
        return id;
    }

    public T getResult() {
        return result;
    }

    public RpcError getError() {
        return error;
    }

    public static class RpcError {
        @JsonProperty("code")
        private int code;

        @JsonProperty("message")
        private String message;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
} 