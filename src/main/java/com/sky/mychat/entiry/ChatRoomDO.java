package com.sky.mychat.entiry;

import java.io.Serializable;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
@Data
public class ChatRoomDO implements Serializable {
    private Integer id;

    private String name;

    private String explain;

    private String icon;

    private Boolean isHot;

    /**
    * 房间类型
    */
    private Byte roomType;

    private Integer sort;

    /**
    * 是否记录聊天数据
    */
    private Boolean saveDb;

    /**
    * 是否禁言
    */
    private Boolean noSay;

    /**
    * 管理员列表 使用逗号隔开
    */
    private String admins;

    /**
    * 是否开启房间
    */
    private Boolean state;

    /**
    * 用户加入类型 0 游客可进 1不可进
    */
    private Boolean joinType;

    /**
    * 玩家等级限制
    */
    private Byte userLevel;

    /**
    * 代理等级限制
    */
    private Byte agentLevel;

    private static final long serialVersionUID = 1L;
}