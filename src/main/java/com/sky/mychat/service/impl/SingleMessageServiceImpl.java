package com.sky.mychat.service.impl;

import com.sky.mychat.mapper.SingleMessageMapper;
import com.sky.mychat.pojo.bean.SingleMessage;
import com.sky.mychat.service.SingleMessageService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by sky on 2019/11/14 13:43
 *
 * @author tiankong
 */
@Service
@Scope(value = "prototype")
public class SingleMessageServiceImpl implements SingleMessageService {
    @Resource
    private SingleMessageMapper singleMessageMapper;

    @Override
    public void save(SingleMessage message) {
        message.setCreateTime(new Date());
        singleMessageMapper.insert(message);
    }
}
