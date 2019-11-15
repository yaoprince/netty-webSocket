package com.sky.mychat.web.controller;

import com.sky.mychat.common.CommonResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tiankong
 */
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public CommonResult exceptionHandler(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            String msg = e.getMessage();
            String message = msg.substring(msg.lastIndexOf("[") + 1, msg.lastIndexOf("]]"));
            return CommonResult.failde(message);
        }
        e.printStackTrace();
        return CommonResult.failde(e.getMessage());
    }
}
