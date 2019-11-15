package com.sky.mychat.web.netty.core;

import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/14 14:02
 */
@Data
public class Session {
    private Integer userId;

    public Session(Integer userId) {
        this.userId = userId;
    }
}
