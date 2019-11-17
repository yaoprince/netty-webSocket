package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatSingleMessageDO;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatSingleMessageDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatSingleMessageDO record);

    int insertSelective(ChatSingleMessageDO record);

    ChatSingleMessageDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatSingleMessageDO record);

    int updateByPrimaryKey(ChatSingleMessageDO record);
}