package config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author shijiu
 */
public class IOClient {
    //这里负责底层与服务端的通信 发起的是Request 收到的是Response
    //客户端发起一次请求调用 Socket建立连接 发起请求Request 得到响应Response
    //这里的Request是封装好的 不同的Service需要不同的封装 客户端只知道Service接口 需要一层动态代理来反射封装不同的Service
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) throws IOException {
        try{
            Socket socket = new Socket(host,port);
            System.out.println(port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            RPCResponse response = (RPCResponse) objectInputStream.readObject();
            System.out.println(response);
            return response;
        }catch (Exception e){
            System.out.println("底层通信出现异常");
            e.printStackTrace();
            return null;
        }
    }

}
