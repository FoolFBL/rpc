package client;

import config.RPCRequest;
import config.RPCResponse;

/**
 * @author shijiu
 */
public interface RPCClient {
  RPCResponse sendRequest(RPCRequest request);
}
