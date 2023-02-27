package client;

import config.ClientProxy;
import entity.Blog;
import entity.User;
import server.Service.BlogService;
import server.Service.UserService;

/**
 * @author shijiu
 */
public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 9999);
        UserService proxy = clientProxy.getProxy(UserService.class);
        System.out.println("客户端启动了");
        User user = proxy.selectUserById(1);
        System.out.println(user);
        //服务方法一
        System.out.println("查询的结果为"+user);
        User user1 = new User();
        user1.setUserId(1);
        user1.setUserName("不为谁而作的歌");
        Integer i = proxy.insertUser(user1);
        System.out.println("插入数据成功"+i);
        BlogService blogService = clientProxy.getProxy(BlogService.class);
        System.out.println("okkk");

        Blog blog = blogService.getBlogById(10);

        System.out.println(blog);
        System.out.println("从服务端得到的blog为"+blog);
    }
}
