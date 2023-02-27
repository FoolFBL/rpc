package client;

import config.RPCClientProxy;
import entity.Blog;
import entity.User;
import server.Service.BlogService;
import server.Service.UserService;

/**
 * @author shijiu
 */
public class TestClient {
    public static void main(String[] args) {
//        //构建一个使用java socket的客户端
//        SimpleRPCClient client = new SimpleRPCClient("127.0.0.1",9999);
//        //把这个客户端传入代理客户端
//        RPCClientProxy rpcClientProxy = new RPCClientProxy(client);
//        UserService userService = rpcClientProxy.getProxy(UserService.class);
//        User user = userService.selectUserById(1);
        // 构建一个使用java Socket/ netty/....传输的客户端
        RPCClient rpcClient = new NettyRPCClient("127.0.0.1", 9999);
        // 把这个客户端传入代理客户端
        RPCClientProxy rpcClientProxy = new RPCClientProxy(rpcClient);
        // 代理客户端根据不同的服务，获得一个代理类， 并且这个代理类的方法以或者增强（封装数据，发送请求）
        UserService userService = rpcClientProxy.getProxy(UserService.class);
        // 调用方法
//        User user1 = userService.selectUserById(1);
//        System.out.println("从服务端得到的user为：" + user1);
//
//        User user = User.builder().userName("张三").userId(100).build();
//        Integer integer = userService.insertUser(user);
//        System.out.println("向服务端插入数据："+integer);

        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);

        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);

    }
}
