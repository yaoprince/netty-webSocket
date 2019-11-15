package com.sky.mychat.common;

import lombok.Data;

/**
 * @author tiankong
 */
@Data
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    private CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private CommonResult(IErrorCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    private CommonResult(IErrorCode code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    private CommonResult(IErrorCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

    private CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResult success() {
        return new CommonResult(ResultCodeEnum.SUCCESS);
    }


    public static CommonResult success(String message) {
        return new CommonResult(ResultCodeEnum.SUCCESS.getCode(), message);
    }

    public static CommonResult success(IErrorCode errorCode) {
        return new CommonResult(errorCode);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCodeEnum.SUCCESS, data);
    }

    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static CommonResult failde() {
        return new CommonResult(ResultCodeEnum.FAILED);
    }

    public static CommonResult failde(String message) {
        return new CommonResult(ResultCodeEnum.FAILED, message);
    }

    //未登录
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCodeEnum.UNAUTHORIZED, data);
    }

    //未授权
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCodeEnum.FORBIDDEN, data);
    }
}
