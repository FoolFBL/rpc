package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shijiu
 */
public class ThreadPoolRPCServer implements RPCServer{
   private final ThreadPoolExecutor threadPool;
   private ServiceProvider serviceProvider;

    public ThreadPoolRPCServer(ServiceProvider serviceProvider) {
        threadPool=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),1000,
                60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
        this.serviceProvider = serviceProvider;
    }

    public ThreadPoolRPCServer(ServiceProvider serviceProvider, int corePoolSize,
                               int maxinumPoolSize, long keepAliveTime,
                               TimeUnit unit, BlockingQueue<Runnable>workQueue){
        threadPool=new ThreadPoolExecutor(corePoolSize,maxinumPoolSize,keepAliveTime,unit,workQueue);
        this.serviceProvider=serviceProvider;
    }

    @Override
    public void start(int port) {
        System.out.println("服务端启动了");
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket accept = serverSocket.accept();
                threadPool.execute(new WorkThread(accept,serviceProvider));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
