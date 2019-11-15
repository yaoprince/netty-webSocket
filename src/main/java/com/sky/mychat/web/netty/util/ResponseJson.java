package com.sky.mychat.web.netty.util;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author tiankong
 */
public class ResponseJson extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    private static final Integer SUCCESS_STATUS = 200;
    private static final Integer ERROR_STATUS = -1;
    private static final String SUCCESS_MSG = "一切正常";

    public ResponseJson setData(String key, Object object) {
        HashMap<String, Object> data = (HashMap<String, Object>) get("data");
        if (data == null) {
            data = new HashMap<>();
            put("data", data);
        }
        data.put(key, object);
        return this;
    }


    public ResponseJson error(String msg) {
        put("status", ERROR_STATUS);
        put("msg", msg);
        return this;
    }

    public ResponseJson success() {
        put("status", SUCCESS_STATUS);
        put("msg", SUCCESS_MSG);
        return this;
    }

    public ResponseJson success(String msg) {
        put("status", SUCCESS_STATUS);
        put("msg", msg);
        return this;
    }
}
