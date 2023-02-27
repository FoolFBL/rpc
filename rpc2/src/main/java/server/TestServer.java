package server;

import server.Service.BlogServiceImpl;
import server.Service.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shijiu
 */
public class TestServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
//        HashMap<String, Object> serviceProvider = new HashMap<>();
//        //暴露两个服务接口
//        serviceProvider.put("server.Service.UserServiceImpl",userService);
//        serviceProvider.put("server.Service.BlogServiceImpl",blogService);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);
        RPCServer server = new ThreadPoolRPCServer(serviceProvider);
        server.start(9999);
    }
}
