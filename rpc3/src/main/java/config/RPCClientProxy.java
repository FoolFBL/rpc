package config;

import client.RPCClient;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shijiu
 */
@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {
    private RPCClient client;

    //jdk动态代理 反射获取request对象 socket发送给客户端
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构建Request
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramTypes(method.getParameterTypes()).build();
        System.out.println(request);
        //数据传输
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }
    public <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o ;
    }

}
