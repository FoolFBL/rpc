package socket;

import impl.BlogServiceImpl;
import impl.UserServiceImpl;

/**
 * @author shijiu
 */
public class TestServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.providerServiceInterface(userService);
        serviceProvider.providerServiceInterface(blogService);
        ThreadPoolRPCRPCServer RPCServer = new ThreadPoolRPCRPCServer(serviceProvider);
        RPCServer.start(8989);
    }
}
