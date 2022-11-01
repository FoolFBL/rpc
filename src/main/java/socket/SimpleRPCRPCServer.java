//package socket;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Map;
//
///**
// * @author shijiu
// */
//public class SimpleRPCRPCServer implements RPCServer{
//    private Map<String,Object> serviceProvide;
//
//    public SimpleRPCRPCServer(Map<String, Object> serviceProvide) {
//        this.serviceProvide = serviceProvide;
//    }
//
//    @Override
//    public void start(int port) {
//        try{
//            ServerSocket serverSocket = new ServerSocket(port);
//            System.out.println("服务端启动了");
//            //以BIO的方式监听socket
//            while(true){
//                Socket accept = serverSocket.accept();
//                //开启一个新线程去处理
//                new Thread(new WorkThread(accept,serviceProvide)).start();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("服务器启动失败");
//        }
//    }
//
//    @Override
//    public void stop() {
//
//    }
//}
