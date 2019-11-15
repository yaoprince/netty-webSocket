package com.sky.mychat.pojo.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * @author tiankong
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String icon;

    private static final long serialVersionUID = 1L;
}
