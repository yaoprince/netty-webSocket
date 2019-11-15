package com.sky.mychat.web.netty.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.mapper.GroupMessageMapper;
import com.sky.mychat.mapper.UserGroupRelationMapper;
import com.sky.mychat.mapper.UserMapper;
import com.sky.mychat.pojo.bean.*;
import com.sky.mychat.pojo.dto.UserInfo;
import com.sky.mychat.service.GroupService;
import com.sky.mychat.service.SingleMessageService;
import com.sky.mychat.util.ThreadUtil;
import com.sky.mychat.web.netty.core.Attributes;
import com.sky.mychat.web.netty.core.Session;
import com.sky.mychat.web.netty.core.service.ChatService;
import com.sky.mychat.web.netty.util.ChatType;
import com.sky.mychat.web.netty.util.ResponseJson;
import com.sky.mychat.web.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tiankong
 */
@Service
@Slf4j
@Transactional
public class ChatServiceImpl implements ChatService {
    @Resource
    private SingleMessageService singleMessageService;
    @Resource
    private GroupService groupService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserGroupRelationMapper userGroupRelationMapper;
    @Resource
    private GroupMessageMapper groupMessageMapper;

    @Override
    public void groupMessage(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            Integer groupId = param.getInteger("group_id");
            String content = param.getString("content");
            Integer fromUserId = channel.attr(Attributes.SESSION).get().getUserId();
            GroupMessage groupMessage = new GroupMessage(groupId, fromUserId, content);
            groupMessageMapper.insert(groupMessage);
            List<UserInfo> userInfoList = groupService.listMembers(groupId);
            for (UserInfo userInfo : userInfoList) {
                Channel userChannel = SessionUtil.onlineUserMap.get(userInfo.getId());
                if (userChannel != null) {
                    sendMessage(userChannel, JSONObject.toJSONString(groupMessage));
                }
            }
        });
    }

    @Override
    public void quitGroup(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            List<Integer> ids = getIds(param);
            Integer groupId = getGroupId(param);
            for (Integer userId : ids) {
                UserGroupRelation userGroupRelation = userGroupRelationMapper.findOneByUidAndGid(userId, groupId);
                if (userGroupRelation == null) {
                    sendMessage(channel, new ResponseJson().error(StrUtil.format("用户{}不在{}群组内", userId, groupId)));
                    return;
                }
                userGroupRelationMapper.quitGroup(userId, groupId);
            }
            sendMessage(channel, new ResponseJson().success("退群成功"));
        });
    }

    @Override
    public void remove(Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            SessionUtil.webSocketServerHandshakerMap.remove(channel.id().asLongText());
            Session session = channel.attr(Attributes.SESSION).get();
            log.info("已移除握手实例");
            if (session == null) {
                channel.close();
                return;
            }
            SessionUtil.onlineUserMap.remove(session.getUserId());
            log.info("已移除握手实例,当前握手实例总数为:{}", SessionUtil.webSocketServerHandshakerMap.size());
            log.info("userId为{}的用户已经退出聊天,当前在线人数为{}", channel.attr(Attributes.SESSION).get().getUserId(), SessionUtil.onlineUserMap.size());
            channel.close();
        });
//        Iterator<Map.Entry<Integer, Channel>> iterator = SessionUtil.onlineUserMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, Channel> next = iterator.next();
//            if (next.getValue() == ctx.channel()) {
//                log.info("正在移除握手实例...");
//                SessionUtil.webSocketServerHandshakerMap.remove(ctx.channel().id().asLongText());
//                log.info("已移除握手实例,当前握手实例总数为:{}", SessionUtil.webSocketServerHandshakerMap.size());
//                iterator.remove();
//                log.info("userId为{}的用户已经退出聊天,当前在线人数为{}", next.getKey(), SessionUtil.onlineUserMap.size());
//                break;
//            }
//        }
    }

    @Override
    public void register(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            int userId = Integer.parseInt(param.get("userId").toString());
            boolean checkOnline = checkOnline(userId, channel);
            if (checkOnline) {
                sendMessage(channel, new ResponseJson().error("请不要重复登录!"));
                channel.close();
                return;
            }
            // TODO 这里可以做用户消息判断
            // 添加用户channel到 在线列表
            SessionUtil.onlineUserMap.put(userId, channel);
            // 绑定用户id到 channel
            channel.attr(Attributes.SESSION).set(new Session(userId));
            // 构建返回消息
            ResponseJson responseJson = new ResponseJson().setData("type", ChatType.REGISTER).setData("msg", "恭喜您花费了两天时间Spring Boot整合Netty WebSocket 连接成功了!开始您的征程吧!!!");
            // 发送返回消息
            sendMessage(channel, responseJson);
            log.info("userId为{}的用户登记到在线用户表,当前在线人数为:{}", userId, SessionUtil.onlineUserMap.size());
        });
    }


    @Override
    public void singleMsg(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            int fromUserId = channel.attr(Attributes.SESSION).get().getUserId();
            int toUserId = Integer.parseInt(param.get("toUserId").toString());
            String content = param.get("content").toString();
            Channel toUserChannel = SessionUtil.onlineUserMap.get(toUserId);
            if (toUserChannel == null) {
                sendMessage(channel, new ResponseJson().error(StrUtil.format("userId为{}的用户没有登录!", toUserId)));
            } else {
                ResponseJson success = new ResponseJson().success();
                success.setData("fromUserId", fromUserId);
                success.setData("content", content);
                success.setData("type", ChatType.SINGLE_MSG);
                sendMessage(toUserChannel, success);
                SingleMessage singleMessage = JSONObject.toJavaObject(param, SingleMessage.class);
                singleMessage.setFromUserId(fromUserId);
                singleMessageService.save(singleMessage);
            }
        });

    }

    @Override
    public void createGroup(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            String name = param.getString("group_name");
            String userIds = param.getString("ids");
            if (name == null) {
                sendMessage(channel, new ResponseJson().error("房间名不能为空"));
                return;
            }
            if (userIds == null) {
                sendMessage(channel, new ResponseJson().error("加入群组的用户id不能为空"));
                return;
            }
            Group group = new Group();
            group.setIcon("/test.png");
            group.setName(name);
            System.out.println(group);
            groupService.save(group);
            int[] userIdsArray = StrUtil.splitToInt(userIds, ",");
            int[] newIds = Arrays.copyOf(userIdsArray, userIdsArray.length + 1);
            newIds[newIds.length - 1] = channel.attr(Attributes.SESSION).get().getUserId();
            List<UserInfo> userInfoList = groupService.joinGroup(group.getId(), newIds);
            sendMessage(channel, new ResponseJson().setData("msg", "创建成功").setData("members", JSONObject.toJSON(userInfoList)));
        });
    }

    @Override
    public void listMembers(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            List<UserInfo> list = groupService.listMembers(param.getInteger("groupId"));
            sendMessage(channel, new ResponseJson().setData("members", JSONObject.toJSON(list)));
        });
    }

    @Override
    public void listMemberOnline(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
        List<UserInfo> list = groupService.listMembers(param.getInteger("groupId"));
        list.removeIf(userInfo -> SessionUtil.onlineUserMap.get(userInfo.getId()) == null);
        sendMessage(channel, new ResponseJson().setData("members", JSONObject.toJSON(list)));
        });
    }

    @Override
    public void joinGroup(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            int[] ids = getInts(param);
            Integer groupId = getGroupId(param);
            for (int userId : ids) {
                User user = userMapper.selectByPrimaryKey(userId);
                if (user == null) {
                    sendMessage(channel, new ResponseJson().error(StrUtil.format("用户{}不存在", userId)));
                    return;
                }
                UserGroupRelation oneByUidAndGid = userGroupRelationMapper.findOneByUidAndGid(userId, groupId);
                if (oneByUidAndGid != null) {
                    sendMessage(channel, new ResponseJson().error(StrUtil.format("请不要重复加入", userId)));
                    return;
                }
            }
            List<UserInfo> userInfoList = groupService.joinGroup(groupId, ids);
            sendMessage(channel, new ResponseJson().setData("members", JSONObject.toJSON(userInfoList)));
        });
    }


    private Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 检查用户是否在线
     *
     * @param userId  用户ID
     * @param channel channel
     * @return boolean
     */
    private boolean checkOnline(int userId, Channel channel) {
        Session session = channel.attr(Attributes.SESSION).get();
        if (session != null && session.getUserId() == userId) {
            return true;
        } else if (session != null) {
            SessionUtil.onlineUserMap.remove(session.getUserId());
        }
        return SessionUtil.onlineUserMap.get(userId) != null;
    }

    private Integer getGroupId(JSONObject param) {
        return param.getInteger("groupId");
    }

    private List<Integer> getIds(JSONObject param) {
        int[] ids = StrUtil.splitToInt(param.getString("ids"), ",");
        return Arrays.stream(ids).boxed().collect(Collectors.toList());
    }

    private int[] getInts(JSONObject param) {
        return StrUtil.splitToInt(param.getString("ids"), ",");
    }

    private void sendMessage(Channel channel, Object data) {
        channel.writeAndFlush(new TextWebSocketFrame(data.toString()));
    }
}
