package com.sky.mychat.web.netty.core;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 该类主要作用:各种处理器管理类
 *
 * @author tiankong
 */
@Component(value = "childChannelHandler")
public class WebSocketChildChannelHandler extends ChannelInitializer<SocketChannel> {
    @Resource
    private ChannelHandler httpRequestHandler;
    @Resource
    private ChannelHandler webSocketHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast("http-handler", httpRequestHandler);
        pipeline.addLast("webSocket-handler", webSocketHandler);
    }
}
