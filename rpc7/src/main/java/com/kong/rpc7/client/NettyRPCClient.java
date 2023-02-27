package com.kong.rpc7.client;


import com.kong.rpc7.common.RPCRequest;
import com.kong.rpc7.common.RPCResponse;
import com.kong.rpc7.service.ServiceRegister;
import com.kong.rpc7.service.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 实现RPCClient接口
 */
public class NettyRPCClient implements RPCClient {
    private static final io.netty.bootstrap.Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    private String host;
    private int port;
    private ServiceRegister serviceRegister;
    public NettyRPCClient() {
        this.serviceRegister = new ZkServiceRegister();
    }
    // netty客户端初始化，重复使用
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    /**
     * 这里需要操作一下，因为netty的传输都是异步的，你发送request，会立刻返回一个值， 而不是想要的相应的response
     */
//    @ExceptionRetry(retryTimes = 5, waitTimes = 2, retryExceptions = ArithmeticException.class, throwExceptions = NullPointerException.class)
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            
            InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
            host = address.getHostName();
            System.out.println("host="+host);
            port = address.getPort();
            System.out.println("port="+port);
            ChannelFuture channelFuture  = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            // 发送数据
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            // 阻塞的获得结果，通过给channel设计别名，获取特定名字下的channel中的内容（这个在hanlder中设置）
            // AttributeKey是，线程隔离的，不会由线程安全问题。
            // 实际上不应通过阻塞，可通过回调函数，后面可以再进行优化
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();

            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
