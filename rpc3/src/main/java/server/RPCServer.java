package server;

/**
 * @author shijiu
 */
//把RPCServer抽象成接口 以后的服务端实现这个接口就好
public interface RPCServer {
    void start(int port);
    void stop();
}
