package com.sky.mychat.service.ex;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tiankong
 * @date 2019/11/17 15:24
 */
public class ServiceException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -2879099986352308425L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
