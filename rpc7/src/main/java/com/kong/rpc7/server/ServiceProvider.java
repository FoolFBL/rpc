package com.kong.rpc7.server;





import com.kong.rpc7.service.ServiceRegister;
import com.kong.rpc7.service.ZkServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时要暴露其相关的实现类
 * 根据request中的interface调用服务端中相关实现类
 */
public class ServiceProvider {
    /**
     * 一个实现类可能实现多个服务接口，
     */
    private Map<String, Object> interfaceProvider;

    private ServiceRegister serviceRegister;
    private String host;
    private int port;
    public ServiceProvider(String host,int port){
        this.interfaceProvider = new HashMap<>();
        this.host=host;
        this.port=port;
        this.serviceRegister=new ZkServiceRegister();
    }

    public void provideServiceInterface(Object service) throws Exception {
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for(Class clazz : interfaces){
            interfaceProvider.put(clazz.getName(),service);
            // 在注册中心注册服务
            serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port));
            System.out.println("正在注册"+clazz.getName());
            InetSocketAddress inetSocketAddress = serviceRegister.serviceDiscovery(clazz.getName());
            System.out.println("得到的ip地址为"+inetSocketAddress);
        }

    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
