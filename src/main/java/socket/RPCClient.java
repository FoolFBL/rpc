package socket;

import Proxy.ClientProxy;
import entity.Blog;
import entity.User;
import service.BlogService;
import service.UserService;

/**
 * @author shijiu
 */
public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1",8989);
        UserService userService = clientProxy.getProxy(UserService.class);
        //服务的方法一
        User userById = userService.getUserById(1);
        System.out.println("从服务端返回的user为"+userById);
        Integer integer = userService.insertUserId(new User());
        System.out.println("向服务端插入数据："+integer);

        BlogService blogService = clientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
