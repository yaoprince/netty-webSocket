package com.sky.mychat.pojo.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author tiankong
 */
@Data
public class GroupMessage implements Serializable {
    private Integer id;

    private Integer groupId;

    private Integer fromUserId;

    private String content;

    private Date createTime;

    public GroupMessage(Integer groupId, Integer fromUserId, String content) {
        this.groupId = groupId;
        this.fromUserId = fromUserId;
        this.content = content;
        this.createTime = new Date();
    }

    private static final long serialVersionUID = 1L;
}
