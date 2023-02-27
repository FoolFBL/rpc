package com.kong.rpc7.server;


import com.kong.rpc7.service.BlogService;
import com.kong.rpc7.service.BlogServiceImpl;
import com.kong.rpc7.service.UserService;
import com.kong.rpc7.service.UserServiceImpl;

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