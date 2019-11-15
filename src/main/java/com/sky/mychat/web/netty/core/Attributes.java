package com.sky.mychat.web.netty.core;

import io.netty.util.AttributeKey;

/**
 * @author tiankong
 * @date 2019/11/14 14:01
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
