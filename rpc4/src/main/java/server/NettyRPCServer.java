package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

/**
 * @author shijiu
 */
@AllArgsConstructor
public class NettyRPCServer implements  RPCServer{
    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {
        //netty服务线程组 boss负责建立连接 work负责处理逻辑
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(8);
        try {
            System.out.println("netty服务器启动了");
            //启动netty服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //初始化
            // 初始化
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerIntializer(serviceProvider));
            // 同步阻塞
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 死循环监听
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}
