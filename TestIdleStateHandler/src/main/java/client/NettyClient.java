package client;

import hook.ShutdownHook;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author shijiu
 */
public class NettyClient {
    public static void main(String[] args) {
        // 客户端就只需要创建一个 线程组了
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        // 创建 启动器
        Bootstrap bootstrap = new Bootstrap();
        try{
            // 设置相关的参数
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(new StringEncoder());
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new IdleStateHandler(5,5,0, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(new HeartBeatClientHandler());
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            // 连接服务
            ChannelFuture future = bootstrap.connect("localhost",10001).sync();

//            ShutdownHook.getShutdownHook().addClearAllHook();
            // 对服务关闭 监听
            future.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            loopGroup.shutdownGracefully();
        }

    }
}
