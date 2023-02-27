package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @author shijiu
 * 这个实现类代表java原始的BIO监听模式 来一个任务 就new一个线程去处理
 * 处理任务的线程在workThread中
 */
public class SimpleRPCServer implements RPCServer{
    //存着服务接口名 比如userService blogService
    private ServiceProvider serviceProvider;

    public SimpleRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动了");
            System.out.println("监听端口为"+port);
            //BIO方式监听端口
            while(true){
                System.out.println(serverSocket.accept());
                Socket accept = serverSocket.accept();
                new Thread(new WorkThread(accept,serviceProvider)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }

    @Override
    public void stop() {

    }
}
