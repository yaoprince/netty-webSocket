package com.sky.mychat.controller;

import com.sky.mychat.service.ex.ServiceException;
import com.sky.mychat.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author tiankong
 * @date 2019/11/17 15:17
 */
public abstract class BaseController {
    /**
     * 响应状态:成功
     */
    protected static final int ok = 2000;

    @ExceptionHandler({ServiceException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> jr = new JsonResult<>();
        if (e instanceof ServiceException) {
            jr.setState(4000);
        }
        return jr;
    }
}
