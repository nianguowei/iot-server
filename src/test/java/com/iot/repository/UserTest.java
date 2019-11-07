package com.iot.repository;

import com.iot.domain.User;
import com.iot.mapper.UserMapper;
import com.iot.TmallApplicationTests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserTest extends TmallApplicationTests {
    @Autowired
    private UserMapper userMapper;
    //@Ignore("not ready yet")

    @Before
    public void testInsert() {
        User user = new User();
        user.setUsername("1");
        user.setNickname("地方");
        user.setPassword("222");
        userMapper.insert(user);
    }

    @Test
    public void testloadUserByUsername(){
        User useryz = userMapper.loadUserByUsername("1");
        Assert.assertEquals("地方",useryz.getNickname());
    }

    @Test
    public void testQueryList(){
        User user = new User();
        user.setIsDeleted(0);
        user.setUsername("1");
        List<User> list = userMapper.queryList(user);
        Assert.assertSame(1,list.size());
    }

}
