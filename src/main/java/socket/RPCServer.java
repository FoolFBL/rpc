package socket;

import config.RPCRequest;
import config.RPCResponse;
import impl.UserServiceImpl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author shijiu
 */
//抽象成接口
public interface RPCServer {
    void start(int port);
    void stop();
}
