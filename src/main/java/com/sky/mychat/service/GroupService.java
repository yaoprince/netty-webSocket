package com.sky.mychat.service;

import com.sky.mychat.pojo.bean.Group;
import com.sky.mychat.pojo.bean.UserGroupRelation;
import com.sky.mychat.pojo.dto.UserInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author tiankong
 */
@CacheConfig(cacheNames = "chat_group")
public interface GroupService {
    List<Integer> getMembersByGroupId(Integer groupId);

    /**
     * 创建群组
     */
    void save(Group group);


    /**
     * 加入群组
     *
     * @param id           群组ID
     * @param userIdsArray 用户ids
     * @return 该群用户成员
     */
    @CachePut(key = "'listMembers'+#p0")
    List<UserInfo> joinGroup(Integer id, int[] userIdsArray);

    /**
     * 查看群成员
     *
     * @param groupId groupId
     * @return 该群成员
     */
    @Cacheable(key = "'listMembers'+#p0")
    List<UserInfo> listMembers(Integer groupId);
}
