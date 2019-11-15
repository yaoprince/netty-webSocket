package com.sky.mychat.common;

/**
 * @author tiankong
 */
public interface IErrorCode {
    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取消息
     *
     * @return 消息
     */
    String getMessage();
}
