package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatGroupDO;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatGroupDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatGroupDO record);

    int insertSelective(ChatGroupDO record);

    ChatGroupDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatGroupDO record);

    int updateByPrimaryKey(ChatGroupDO record);
}