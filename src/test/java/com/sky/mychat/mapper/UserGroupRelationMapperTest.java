package com.sky.mychat.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileNotFoundException;

/**
 * @author tiankong
 * @date 2019/11/15 12:03
 */
public class UserGroupRelationMapperTest {
    private static UserGroupRelationMapper mapper;

    @org.junit.BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(UserGroupRelationMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/UserGroupRelationMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(UserGroupRelationMapper.class, builder.openSession(true));
    }

    @org.junit.Test
    public void testFindOneByUidAndGid() throws FileNotFoundException {
        System.out.println(mapper.findOneByUidAndGid(4,25));
    }
}
