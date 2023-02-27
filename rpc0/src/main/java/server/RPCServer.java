package server;

import entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author shijiu
 */
public class RPCServer {
    public static void main(String[] args) throws IOException {
        UserServiceImpl userService = new UserServiceImpl();
        //监听端口
        try  {
            ServerSocket serverSocket = new ServerSocket(8989);
            System.out.println("服务端启动了 正在监听端口"+ serverSocket.getLocalPort());
            //BIO的方式监听端口 即不断轮询
            while (true){
                Socket accept = serverSocket.accept();
                //开启新线程去处理
                new Thread(()->{
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                        //读取客户端传过来的userid
                        int i = objectInputStream.readInt();
                        //服务端调用自己的本地方法
                        User user = userService.selectUserById(i);
                        //写入user对象给客户端
                        objectOutputStream.writeObject(user);
                        objectOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }catch (Exception e){
            System.out.println("从服务端读取数据失败");
        }
    }
}
