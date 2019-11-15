package com.sky.mychat.mapper;

import com.sky.mychat.pojo.bean.SingleMessage;

import java.util.List;

public interface SingleMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SingleMessage record);

    int insertSelective(SingleMessage record);

    SingleMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SingleMessage record);

    int updateByPrimaryKey(SingleMessage record);

    List<SingleMessage> selectByAll(SingleMessage singleMessage);



}
