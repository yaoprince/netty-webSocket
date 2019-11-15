package com.sky.mychat.web.netty.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tiankong
 */
@Slf4j
@Component
public class WebSocketServer implements Runnable {

    @Value("${webSocket.server.port}")
    private Integer port;
    @Resource
    private ChannelHandler childChannelHandler;

    @Resource(name = "bossGroup")
    private EventLoopGroup bossGroup;

    @Resource(name = "workerGroup")
    private EventLoopGroup workerGroup;

    @Resource
    private ServerBootstrap serverBootstrap;
    private ChannelFuture serverChannelFuture;


    @Override
    public void run() {
        try {
            long begin = System.currentTimeMillis();
            // boos负责客户端的tcp连接请求 worker负责与客户端之间的读写操作
            serverBootstrap.group(bossGroup, workerGroup)
                    // 配置客户端的channel类型
                    .channel(NioServerSocketChannel.class)
                    // 配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 开启心跳包活机制， 客户端，服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //配置固定长度接收缓存区分配器
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                    // 绑定I/O事件的处理类，WebSocketChildChannelHandler中定义
                    .childHandler(childChannelHandler);
            long end = System.currentTimeMillis();
            log.info("Netty WebSocket服务器启动完成，耗时{}ms,已绑定端口{}阻塞式等候客户端连接", end - begin, port);
            serverChannelFuture = serverBootstrap.bind(port).sync();
        } catch (Exception e) {
            log.info(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public void close() {
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();
        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
