package com.sky.mychat.web.netty.core;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.common.CommonResult;
import com.sky.mychat.web.netty.core.service.ChatService;
import com.sky.mychat.web.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * WebSocket 请求处理器
 *
 * @author tiankong
 * @date 2019/11/14 12:35
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Resource
    private ChatService chatService;

    /**
     * 读取完连接的消息后，对消息进行处理。
     * 这里主要是处理WebSocket请求。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handlerWebSocketFrame(ctx, msg);
    }

    /**
     * 处理处理WebSocketFrame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 关闭请求
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handShaker = SessionUtil.webSocketServerHandshakerMap.get(ctx.channel().id().asLongText());
            if (handShaker == null) {
                sendErrorMessage(ctx, "不存在的客户端连接!");
            } else {
                handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        // ping请求
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 只支持文本格式，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, "仅支持文本（Text）格式，不支持二进制消息");
        }
        // 客户端发送过来的消息
        String request = ((TextWebSocketFrame) frame).text();
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON字符串转换出错！");
            e.printStackTrace();
        }
        if (param == null) {
            sendErrorMessage(ctx, "参数为空！");
            return;
        }
        String type = param.get("type").toString();
        switch (type) {
            // 注册
            case "REGISTER":
                chatService.register(param, ctx.channel());
                break;
            // 单聊
            case "SINGLE_MSG":
                chatService.singleMsg(param, ctx.channel());
                break;
            // 群组群聊
            case "CREATE_GROUP":
                chatService.createGroup(param, ctx.channel());
                break;
            // 查看群成员
            case "LIST_MEMBERS":
                chatService.listMembers(param, ctx.channel());
                break;
            // 查看在线群成员
            case "LIST_MEMBERS_ONLINE":
                chatService.listMemberOnline(param, ctx.channel());
                break;
            // 加入群组
            case "JOIN_GROUP":
                chatService.joinGroup(param, ctx.channel());
                break;
            // 退群
            case "quit_group":
                chatService.quitGroup(param, ctx.channel());
                break;
            // 群聊
            case "GROUP_MESSAGE":
                chatService.groupMessage(param, ctx.channel());
                break;
            default:
                System.out.println("指令错误");
                break;
        }
    }

    /**
     * 客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chatService.remove(ctx.channel());
    }

    /**
     * 异常处理：关闭channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void sendErrorMessage(ChannelHandlerContext ctx, String errorMsg) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(CommonResult.failde(errorMsg))));
    }
}
