package com.lot;

import com.iot.domain.User;
import com.iot.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest extends TmallApplicationTests{
    @Autowired
    private UserMapper userMapper;
    //@Ignore("not ready yet")
    @Test
    public void testReg(){
        User user = new User();
        user.setUsername("1");
        user.setNickname("地方");
        user.setPassword("222");
        userMapper.reg(user);

        User useryz = userMapper.loadUserByUsername("1");

        Assert.assertEquals("地方",useryz.getNickname());
    }

//    @Test
//    public void testGetEntFileList(){
//        Assert.assertSame("企业数量不为10",10,entFileService.getEntFileList());
//    }

}
