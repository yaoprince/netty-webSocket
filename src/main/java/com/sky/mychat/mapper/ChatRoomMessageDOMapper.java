package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatRoomMessageDO;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatRoomMessageDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatRoomMessageDO record);

    int insertSelective(ChatRoomMessageDO record);

    ChatRoomMessageDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatRoomMessageDO record);

    int updateByPrimaryKey(ChatRoomMessageDO record);
}