package com.sky.mychat.web.netty.core.service;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.pojo.bean.UserGroupRelation;
import io.netty.channel.Channel;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author tiankong
 */
public interface ChatService {
    /**
     * 断开客户端连接
     */
    void remove(Channel channel);


    /**
     * 注册客户端
     *
     * @param param   param
     * @param channel channel
     */
    void register(JSONObject param, Channel channel);

    /**
     * 单聊
     *
     * @param param   param
     * @param channel channel
     */
    void singleMsg(JSONObject param, Channel channel);

    /**
     * 创建群聊
     *
     * @param param   param
     * @param channel channel
     */
    void createGroup(JSONObject param, Channel channel);

    /**
     * 查看群成员
     *
     * @param param   param
     * @param channel channel
     */
    void listMembers(JSONObject param, Channel channel);

    /**
     * 加入群组
     *
     * @param param   param
     * @param channel channel
     */
    void joinGroup(JSONObject param, Channel channel);

    /**
     * 退群
     *
     * @param param   param
     * @param channel channel
     */
    void quitGroup(JSONObject param, Channel channel);

    /**
     * 群聊
     *
     * @param param   param
     * @param channel channel
     */
    void groupMessage(JSONObject param, Channel channel);

    /**
     * 查看在线群成员
     *
     * @param param   param
     * @param channel channel
     */
    void listMemberOnline(JSONObject param, Channel channel);
}
