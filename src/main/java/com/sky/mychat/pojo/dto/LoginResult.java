package com.sky.mychat.pojo.dto;

import lombok.Data;

/**
 * @author tiankong
 */
@Data
public class LoginResult {
    private Integer id;
    private String username;
    private String icon;
    private String token;
}
