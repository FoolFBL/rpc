package client;

import config.ClientProxy;
import entity.User;
import server.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author shijiu
 */
public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 9888);
        UserService proxy = clientProxy.getProxy(UserService.class);
        //服务方法一
        User user = proxy.selectUserById(1);
        System.out.println("查询的结果为"+user);
        User user1 = new User();
        user1.setUserId(1);
        user1.setUserName("不为谁而作的歌");
        Integer i = proxy.insertUser(user1);
        System.out.println("插入数据成功"+i);
    }
}
