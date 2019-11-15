package com.sky.mychat.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author tiankong
 * @date 2019/11/14 18:59
 */
@Data
public class UserInfo  implements Serializable {
    private Integer id;
    private String username;
    private String icon;
}
