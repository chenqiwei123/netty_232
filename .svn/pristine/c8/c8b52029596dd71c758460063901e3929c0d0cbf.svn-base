package com.example.netty.netty.Socket;

import com.example.netty.netty.Controller.ConfigBeanValue;
import com.example.netty.netty.Socket.Parsing.PackDealImpl;
import com.example.netty.netty.service.Impl.UserServiceImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.Endpoint;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Handler;

import static com.example.netty.netty.Socket.Parsing.PackDealImpl.ht_site;

/**
 * @author runwsh
 */
@Component
@Slf4j
public class SimpleServer{
    @Autowired
    ConfigBeanValue configBeanValue;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Map<String, List<Map>> mapPointInfo=new HashMap<>();
    public static int AddPointNumber=0;

    /**
     * //open port in  config.xml
     */
    public static void main() {
        //创建bossGroup , 只负责连接请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //创建workerGroup , 负责客户端业务处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建服务端启动对象,配置参数.
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup)//设置线程组
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务端的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接个数
//                    .option(ChannelOption.SO_KEEPALIVE,true)//保持连接  xp32位不支持
                    .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK,20*1024*1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler()); //自定义handler
                        }
                    });//workerGroup的EventLoop对应的管道设置处理器
            System.out.println("服务端准备就绪...");
            Iterator iterator = ht_site.keySet().iterator();
            ChannelFuture[] channelFutures=new ChannelFuture[ht_site.size()];
            for (int i=0;i<ht_site.size();i++){
                int jj=i+1;
                Map<String,String> map=new HashMap<>();
                List<Map> mapList=new ArrayList<>();
                Object next1 =iterator.next();
                int port=Integer.parseInt(next1.toString());
                System.out.println("PORT:"+PackDealImpl.nodeId.get(String.valueOf(port))+"---->启动起来了!!!!!");
                channelFutures[i] = serverBootstrap.bind(port).sync();
                channelFutures[i].addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        map.put("id", String.valueOf(jj));
                        map.put("name", String.valueOf(PackDealImpl.ht_site.get(String.valueOf(port))));
                        map.put("port", String.valueOf(port));
                        map.put("Protocol", "212");
                        map.put("way","GPRS");
                        map.put("time",sdf.format(new Date()));
                        map.put("ThreadID", String.valueOf(Thread.currentThread().getId()));
                        if (future.isSuccess()){
                            System.out.println("PORT:"+PackDealImpl.nodeId.get(String.valueOf(port))+"---->启动起来了!!!!!");
                            map.put("status","启动成功!");
                        }else{
                            System.out.println("PORT:"+PackDealImpl.nodeId.get(String.valueOf(port))+"---->启动失败了!!!!!");
                            map.put("status","启动失败,请查看"+port+"端口是否被占用!");
                        }
                    }
                });
                mapList.add(map);
                mapPointInfo.put(String.valueOf(port),mapList);
                //channelFutures[i].channel().closeFuture().sync();
                //绑定一个端口并且同步,生成了一个channelFuture对象
                //对关闭通道进行监听
            }

            for (ChannelFuture c:channelFutures){

                c.channel().closeFuture().sync();
            }
/*
            //绑定一个端口并且同步,生成了一个channelFuture对象
            ChannelFuture cf = serverBootstrap.bind(9001).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


//    @Override
//    public void run() {
//        main();
//    }
}