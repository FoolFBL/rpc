package server;

import config.RPCRequest;
import config.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author shijiu
 */
@AllArgsConstructor
public class WorkThread implements Runnable {
    private Socket socket;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RPCRequest request = (RPCRequest) objectInputStream.readObject();
            //反射调用方法
            RPCResponse response = getResponse(request);
            //写入到客户端
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("从IO中读取数据错误");
        }
    }

    private RPCResponse getResponse(RPCRequest request){
        //得到服务名
        String interfaceName = request.getInterfaceName();
        //得到服务端相应的实现类
        Object service = serviceProvider.getService(interfaceName);
        //反射调用方法
        Method method = null;
        try{
            method=service.getClass().getMethod(request.getMethodName(),request.getParamTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }

    }



}
