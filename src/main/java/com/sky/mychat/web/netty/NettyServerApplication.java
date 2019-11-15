package com.sky.mychat.web.netty;

import com.sky.mychat.web.netty.core.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author tiankong
 */
@Component
@Scope("singleton")
@Slf4j
public class NettyServerApplication {
    @Resource
    private WebSocketServer webSocketServer;
    private Thread nettyThread;

    @PostConstruct
    public void init() {
        nettyThread = new Thread(webSocketServer);
        nettyThread.start();
    }


    @PreDestroy
    public void close() {
        webSocketServer.close();
        // 中断线程
        nettyThread.interrupt();
    }
}
