package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatRoomDO;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatRoomDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatRoomDO record);

    int insertSelective(ChatRoomDO record);

    ChatRoomDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatRoomDO record);

    int updateByPrimaryKey(ChatRoomDO record);
}