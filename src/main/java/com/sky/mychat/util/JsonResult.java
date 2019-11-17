package com.sky.mychat.util;

import com.sky.mychat.constant.IErrorCode;
import com.sky.mychat.constant.ResultCodeEnum;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 15:27
 */
@Data
public class JsonResult<T> {
    private Integer state;
    private String message;
    private T data;

    public JsonResult() {
        super();
    }

    public JsonResult(Integer state) {
        super();
        this.state = state;
    }

    public JsonResult(Throwable throwable) {
        super();
        this.message = throwable.getMessage();
    }

    public JsonResult(Integer state, T data) {
        super();
        this.state = state;
        this.data = data;
    }

    private JsonResult(IErrorCode code) {
        this.state = code.getCode();
        this.message = code.getMessage();

    }

    /**
     * 未授权
     */
    public static JsonResult forbidden() {
        return new JsonResult(ResultCodeEnum.FORBIDDEN);
    }

    /**
     * 未登录
     */
    public static JsonResult unauthorized() {
        return new JsonResult(ResultCodeEnum.UNAUTHORIZED);
    }
}
