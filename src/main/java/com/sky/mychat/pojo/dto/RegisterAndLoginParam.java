package com.sky.mychat.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tiankong
 */
@Data
public class RegisterAndLoginParam {
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能空")
    private String password;

    /**
     * 是否验证密码
     */
    private boolean checkPassword = true;
}
