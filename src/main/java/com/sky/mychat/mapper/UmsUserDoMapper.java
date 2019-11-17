package com.sky.mychat.mapper;

import org.apache.ibatis.annotations.Param;

import com.sky.mychat.entiry.UmsUserDO;

/**
 * @author tiankong
 * @date 2019/11/17 16:05
 */
public interface UmsUserDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UmsUserDO record);

    int insertSelective(UmsUserDO record);

    UmsUserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UmsUserDO record);

    int updateByPrimaryKey(UmsUserDO record);

    UmsUserDO selectOneByUsername(@Param("username") String username);


}
