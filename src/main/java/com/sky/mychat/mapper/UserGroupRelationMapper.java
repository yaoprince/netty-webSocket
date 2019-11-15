package com.sky.mychat.mapper;

import com.sky.mychat.pojo.dto.UserInfo;
import org.apache.ibatis.annotations.Param;

import com.sky.mychat.pojo.bean.UserGroupRelation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Vector;

/**
 * @author tiankong
 * @date 2019/11/14 17:59
 */
@CacheConfig(cacheNames = "chat_group")
public interface UserGroupRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGroupRelation record);

    int insertSelective(UserGroupRelation record);

    UserGroupRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGroupRelation record);

    int updateByPrimaryKey(UserGroupRelation record);

    /**
     * 根据群组ID查询该群所有用户
     *
     * @param groupId groupId
     * @return List<Integer>
     */
    List<Integer> getMembersByGroupId(Integer groupId);

    /**
     * 加入群组
     *
     * @param id           群组ID
     * @param userIdsArray 用户id Array
     * @return int
     */
    int joinGroup(@Param("id") Integer id, @Param("userIdsArray") int[] userIdsArray);

    /**
     * 查询群成员
     *
     * @param gid 群成员
     * @return List
     */
    Vector<UserInfo> selectByGid(@Param("gid") Integer gid);

    /**
     * 查询用户是否加入群组
     *
     * @param uid uid
     * @param gid gid
     * @return UserGroupRelation
     */
    UserGroupRelation findOneByUidAndGid(@Param("uid") Integer uid, @Param("gid") Integer gid);


    /**
     * 退出群组
     *
     * @param userId  userId
     * @param groupId groupId
     */
    @Caching(evict = {@CacheEvict(key = "'listMembers'+#p1")})
    void quitGroup(@Param("userId") Integer userId, @Param("groupId") Integer groupId);
}
