package com.iot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iot.domain.User;
import com.iot.mapper.UserMapper;
import com.iot.service.UserService;
import com.iot.utils.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * 用户信息实现类
 *
 * @author Nian Guowei
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;
//    @Autowired
//    RolesMapper rolesMapper;

    @Override
    public User queryByUsername(String username) {
        return userMapper.loadUserByUsername(username);
    }


    @Override
    public void insert(User user) throws BusinessException {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            //写日志，报异常
            log.error("用户名 {} 已经存在",user.getUsername());
            throw new BusinessException(0, user.getUsername() + "用户已经存在");
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);

        //配置用户的角色，默认都是普通用户
//        String[] roles = new String[]{"2"};
//        int i = rolesMapper.addRoles(roles, user.getId());

    }

    @Override
    public void update(User user) throws BusinessException {
        if (StringUtils.isBlank(user.getUsername())){
            throw new BusinessException(0, "输入用户为空");
        }
        if (StringUtils.isNotBlank(user.getPassword())){
            //密码加密
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        userMapper.update(user);
    }

    @Override
    public PageInfo<User> queryPage(User user, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageSize == null){
            PageHelper.startPage(1, Integer.MAX_VALUE);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }
        user.setIsDeleted(0);
        List<User> userList = userMapper.queryList(user);
        return new PageInfo<>(userList);
    }


    @Override
    public void deleteByUsername(String username) {
        userMapper.deleteByUsername(username);
    }
}
