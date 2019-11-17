package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatGroupMessageDO;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatGroupMessageDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatGroupMessageDO record);

    int insertSelective(ChatGroupMessageDO record);

    ChatGroupMessageDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatGroupMessageDO record);

    int updateByPrimaryKey(ChatGroupMessageDO record);
}