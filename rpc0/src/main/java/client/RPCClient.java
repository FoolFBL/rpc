package client;

import entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author shijiu
 */
public class RPCClient {
    public static void main(String[] args) {
        try  {
            Socket socket = new Socket("127.0.0.1", 8989);
            //对象输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //对象输入流
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //传送userid
            objectOutputStream.writeInt(3);
            objectOutputStream.flush();
            //服务器查询数据 返回结果
            User user = (User) objectInputStream.readObject();
            System.out.println("服务端返回的user"+user);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("客户端启动异常");
            e.printStackTrace();
        }
    }
}
