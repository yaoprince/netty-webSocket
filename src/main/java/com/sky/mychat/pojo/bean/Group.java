package com.sky.mychat.pojo.bean;

import java.io.Serializable;
import lombok.Data;

@Data
public class Group implements Serializable {
    private Integer id;

    private String name;

    private String icon;

    private static final long serialVersionUID = 1L;
}