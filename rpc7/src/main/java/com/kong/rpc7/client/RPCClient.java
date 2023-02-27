package com.kong.rpc7.client;



import com.kong.rpc7.common.RPCRequest;
import com.kong.rpc7.common.RPCResponse;


public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
