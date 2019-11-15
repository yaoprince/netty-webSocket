package com.sky.mychat.service.impl;

import com.sky.mychat.mapper.GroupMapper;
import com.sky.mychat.mapper.UserGroupRelationMapper;
import com.sky.mychat.pojo.bean.Group;
import com.sky.mychat.pojo.bean.UserGroupRelation;
import com.sky.mychat.pojo.dto.UserInfo;
import com.sky.mychat.service.GroupService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Vector;

/**
 * @author tiankong
 */
@Service
@Scope(value = "prototype")
public class GroupServiceImpl implements GroupService {
    @Resource
    private UserGroupRelationMapper userGroupRelationMapper;

    @Resource
    private GroupMapper groupMapper;

    @Override
    public List<Integer> getMembersByGroupId(Integer groupId) {
        return userGroupRelationMapper.getMembersByGroupId(groupId);
    }

    @Override
    public void save(Group group) {
        groupMapper.insert(group);
    }

    @Override
    public List<UserInfo> joinGroup(Integer id, int[] userIdsArray) {
        userGroupRelationMapper.joinGroup(id, userIdsArray);
        return userGroupRelationMapper.selectByGid(id);
    }

    @Override
    public List<UserInfo> listMembers(Integer groupId) {
        return userGroupRelationMapper.selectByGid(groupId);
    }
}
