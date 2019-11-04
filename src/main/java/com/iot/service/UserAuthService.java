package com.iot.service;

import com.iot.domain.Role;
import com.iot.domain.User;
import com.iot.mapper.RolesMapper;
import com.iot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserAuthService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RolesMapper rolesMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            throw new UsernameNotFoundException("用户不存在");
        }
        //查询用户的角色信息，并返回存入user中
//        List<Role> roles = rolesMapper.getRolesByUsername(username);
        List<Role> roles = new ArrayList<>();
        user.setRoleList(roles);
        return user;
    }
}
