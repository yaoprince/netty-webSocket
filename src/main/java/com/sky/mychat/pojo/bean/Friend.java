package com.sky.mychat.pojo.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Friend implements Serializable {
    private Integer id;

    private Integer uid;

    /**
    * 朋友id
    */
    private Integer fid;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}
