package server;


import service.BlogService;
import service.BlogServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) throws Exception {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1",9999);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(9999);
    }
}