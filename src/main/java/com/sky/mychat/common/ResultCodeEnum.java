package com.sky.mychat.common;

/**
 * @author tiankong
 */

public enum ResultCodeEnum implements IErrorCode {


    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(404, "参数检验失败"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限"),
    /**
     * 暂未登录或token已过期
     */
    UNAUTHORIZED(401, "暂未登录或token已过期"),
    /**
     * 失败
     */
    FAILED(0, "操作失败"),
    /**
     * 成功
     */
    SUCCESS(1, "操作成功");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
