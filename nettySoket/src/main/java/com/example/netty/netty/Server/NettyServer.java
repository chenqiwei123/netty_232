package com.example.netty.netty.Server;

import com.example.netty.netty.Socket.Parsing.PackDealImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Iterator;

import static com.example.netty.netty.Socket.Parsing.PackDealImpl.ht_site;

@Slf4j
@Component
public class NettyServer implements Runnable{
    public static int address_port;
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    public static ChannelFuture[] channelFutures=new ChannelFuture[ht_site.size()];
//    public static ChannelFuture[] channelFutures1=new ChannelFuture[ht_site.size()];
    public void start(InetSocketAddress address) {
        address_port=address.getPort();
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)  // 绑定线程池
                    .channel(NioServerSocketChannel.class)
                    .localAddress(address)
                    .option(ChannelOption.SO_BACKLOG, 128)  //服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  //保持长连接，2小时无数据激活心跳机制

            // 绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(address).sync();
            log.info("netty服务器开始监听端口：" + address.getPort());
            //关闭channel和块，直到它被关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        System.out.println("启动端口"+address_port);
        InetSocketAddress address = new InetSocketAddress(address_port);
        this.start(address);
    }
}
