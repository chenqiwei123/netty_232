package com.example.netty.netty.Socket;

import com.example.netty.netty.Socket.Parsing.PackDealImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.text.SimpleDateFormat;
import java.util.*;

public class ThreadOpenPort implements Runnable{
    private String port;
    private String PortName;
    public static int AddPointNumber=0;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPortName() {
        return PortName;
    }

    public void setPortName(String portName) {
        PortName = portName;
    }

    /**
     * AddPoint port
     */
    public static void AddPointPort(String port,String PortName) {
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
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler()); //自定义handler
                        }
                    });//workerGroup的EventLoop对应的管道设置处理器
            System.out.println("启动添加的站点就绪...");
            int jj=AddPointNumber+1;
            AddPointNumber++;
            Map<String,String> map=new HashMap<>();
            List<Map> mapList=new ArrayList<>();
            int port1=Integer.parseInt(port);
            PackDealImpl.nodeId.put(port,port);
            PackDealImpl.ht_site.put(port,PortName);
            System.out.println("PORT:"+PackDealImpl.nodeId.get(port)+"---->启动起来了!!!!!");
            ChannelFuture sync = serverBootstrap.bind(port1).sync();
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    map.put("id", "Add"+jj);
                    map.put("name", String.valueOf(PackDealImpl.ht_site.get(port)));
                    map.put("port", port);
                    map.put("Protocol", "212");
                    map.put("way","GPRS");
                    map.put("time",sdf.format(new Date()));
                    map.put("ThreadID", String.valueOf(Thread.currentThread().getId()));
                    if (future.isSuccess()){
                        System.out.println("PORT:"+PackDealImpl.nodeId.get(port)+"---->启动起来了!!!!!");
                        map.put("status","启动成功!");
                    }else{
                        System.out.println("PORT:"+PackDealImpl.nodeId.get(port)+"---->启动失败了!!!!!");
                        map.put("status","启动失败,请查看"+port+"端口是否被占用!");
                    }
                }
            });
            mapList.add(map);
            SimpleServer.mapPointInfo.put(port,mapList);
            //channelFutures[i].channel().closeFuture().sync();
            //绑定一个端口并且同步,生成了一个channelFuture对象
            //对关闭通道进行监听
//            }
            sync.channel().closeFuture().sync();
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

    @Override
    public void run() {
        AddPointPort(port,PortName);
    }
}
