package com.sky.mychat.pojo.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/14 17:59
 */
@Data
public class UserGroupRelation implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer gid;

    private static final long serialVersionUID = 1L;

    public UserGroupRelation(Integer uid, Integer gid) {
        this.uid = uid;
        this.gid = gid;
    }
}
