package com.kong.rpc7.server;


public interface RPCServer {
    void start(int port);
    void stop();
}
