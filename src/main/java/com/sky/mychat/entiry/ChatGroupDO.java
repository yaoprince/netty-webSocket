package com.sky.mychat.entiry;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
@Data
public class ChatGroupDO implements Serializable {
    private Integer id;

    private String name;

    private String icon;

    private String description;

    /**
    * 群类型
    */
    private Byte type;

    /**
    * 加群人数最大值
    */
    private Short maxNumberOfPeople;

    private Date createTime;

    private Date modifiedTime;

    private static final long serialVersionUID = 1L;
}