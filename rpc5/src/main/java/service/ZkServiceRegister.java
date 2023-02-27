package service;

import loadbalance.LoadBalance;
import loadbalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author shijiu
 */
public class ZkServiceRegister implements ServiceRegister{
    //curator提供的zookeeper客户端
    private CuratorFramework client;
    //zookeeper根路径结点
    private static final String ROOT_PATH = "MyRPC";
    // 初始化负载均衡器， 这里用的是随机， 一般通过构造函数传入
    private LoadBalance loadBalance = new RandomLoadBalance();

    public ZkServiceRegister() {
        //指数时间重试
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(1000, 3);
        //zookeeper的地址固定 不管是服务消费方还是服务提供方都需要与之建立连接
        //zk会根据minSessionTimeout和maxSessionTimeout两个参数调整最后的超时值
        //使用心跳监听状态
        this.client= CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zookeeper连接成功");
    }

    @Override
    public void register(String serviceName, InetSocketAddress severAddress) throws Exception {
        //serviceName创建成永久结点 服务提供者下线时 不删服务名 只删地址
        if(client.checkExists().forPath("/"+serviceName)==null){
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
        }
        //路径地址
        String path = "/"+serviceName+"/"+getServiceAddress(severAddress);
        //临时结点 服务器下线就删除结点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
    }

    //根据服务名返回地址
    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            //这里默认用的第一个 后面加负载均衡
            String string = loadBalance.balance(strings);
            return parseAddress(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //地址转化为ip地址
    private String getServiceAddress(InetSocketAddress serverAddress){
        return serverAddress.getHostName()+":"+serverAddress.getPort();
    }
    //字符串解析为地址
    private InetSocketAddress parseAddress(String address){
        String[] result = address.split(":");
        return new InetSocketAddress(result[0],Integer.parseInt(result[1]));
    }



}
