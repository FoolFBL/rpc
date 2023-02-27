package server;

import config.RPCRequest;
import config.RPCResponse;
import entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            ServerSocket serverSocket = new ServerSocket(9888);
            System.out.println("服务端启动了 正在监听端口"+ serverSocket.getLocalPort());
            //BIO的方式监听端口 即不断轮询
            while (true){
                Socket accept = serverSocket.accept();
                //开启新线程去处理
                new Thread(()->{
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                        //读取客户端传过来的Request类型数据
                        RPCRequest request = (RPCRequest) objectInputStream.readObject();
                        //服务端调用自己的本地方法 通过反射
                        Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamTypes());
                        Object invoke = method.invoke(userService, request.getParams());
                        //封装写入对象
                        objectOutputStream.writeObject(RPCResponse.success(invoke));
                        objectOutputStream.flush();
                    } catch (IOException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("从服务端读取数据失败");
        }
    }
}
