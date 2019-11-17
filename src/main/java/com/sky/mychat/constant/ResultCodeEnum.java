package com.sky.mychat.constant;

/**
 * @author tiankong
 * @date 2019/11/17 15:41
 */
public enum ResultCodeEnum implements IErrorCode {
    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(4004, "参数检验失败"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(4003, "没有相关权限"),
    /**
     * 暂未登录或token已过期
     */
    UNAUTHORIZED(4001, "暂未登录或token已过期"),
    /**
     * 失败
     */
    FAILED(4000, "操作失败"),
    /**
     * 成功
     */
    SUCCESS(2000, "操作成功");
    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
