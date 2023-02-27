package service;

import java.net.InetSocketAddress;

/**
 * @author shijiu
 */
public interface ServiceRegister {
    //服务注册接口 两大功能 注册  发现（查询）
    void register(String serviceName, InetSocketAddress severAddress) throws Exception;
    InetSocketAddress serviceDiscovery(String serviceName);
}
