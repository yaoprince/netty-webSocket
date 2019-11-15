package com.sky.mychat.pojo.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author tiankong
 */
@Data
public class SingleMessage implements Serializable {
    private Integer id;

    private Integer fromUserId;

    private Integer toUserId;

    private String content;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}
