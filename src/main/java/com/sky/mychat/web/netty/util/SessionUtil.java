package com.sky.mychat.web.netty.util;

import com.sky.mychat.service.GroupService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tiankong
 */
@Component
public class SessionUtil {
    public static Map<String, WebSocketServerHandshaker> webSocketServerHandshakerMap = new ConcurrentHashMap<>();
    /**
     * 在线用户Channel
     */
    public static Map<Integer, Channel> onlineUserMap = new ConcurrentHashMap<>();

    @Resource
    private GroupService groupService;

    /**
     * 添加用户Channel到在线列表
     *
     * @param uid     用户ID
     * @param channel 用户Channel
     */
    public void registerUserChannel(Integer uid, Channel channel) {
        onlineUserMap.put(uid, channel);
    }

    /**
     * 获取用户Channel
     *
     * @param userId 用户ID
     * @return Channel
     */
    public Channel getChannelByUserId(String userId) {
        return onlineUserMap.get(userId);
    }

    /**
     * 获取群用户Channel
     *
     * @param groupId 群ID
     * @return List Channel
     */
    public List<Channel> getMembersByGroupId(Integer groupId) {
        List<Integer> members = groupService.getMembersByGroupId(groupId);
        List<Channel> list = new ArrayList<>();
        members.forEach(uid -> list.add(onlineUserMap.get(uid)));
        return list;
    }
}
