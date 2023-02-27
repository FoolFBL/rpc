package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.ws.Service;
import java.util.concurrent.TimeUnit;

/**
 * @author shijiu
 * 主要负责序列化的编码解码 需要解决nettry的粘包问题
 */
@AllArgsConstructor
@NoArgsConstructor
public class NettyServerIntializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
        //消息格式 长度 + 消息体
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        //计算当前消息发送的长度 写入到前四个字节中
        pipeline.addLast(new LengthFieldPrepender(4));
        //这里使用的还是java的原生序列化模式
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String className) throws ClassNotFoundException {
                return Class.forName(className);
            }
        }));
        pipeline.addLast(new HeartBeatServerHandler());
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));


    }
}
