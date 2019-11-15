package com.sky.mychat.mapper;

import org.apache.ibatis.annotations.Param;

import com.sky.mychat.pojo.bean.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author tiankong
 */
@CacheConfig(cacheNames = "user")
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    /**
     * 根据用户ID查询
     * 此方法使用了缓存,在根据用户信息时需要
     * 根据缓存的key做缓存数据更新.
     *
     * @param id 用户ID
     * @return User
     */
    @Cacheable(key = "'selectByPrimaryKey_'+#p0")
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名查询用户
     * 此方法使用了缓存,在更新用户消息时需要
     * 根据缓存的key做缓存数据更新
     *
     * @param username 用户名
     * @return User
     */
    @Cacheable(key = "'selectOneByUsername_'+#p0")
    User selectOneByUsername(@Param("username") String username);

}
