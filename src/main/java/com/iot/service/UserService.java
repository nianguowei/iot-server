package com.iot.service;

import com.iot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * 用户信息
 *
 * @author Nian Guowei
 */

public interface UserService {

    /**
     * 登录验证用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 插入用户
     * @param user
     */
    void insert(User user);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    List<User> queryList(User user);

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    User queryByUsername(String username);

    /**
     * 删除用户信息
     */
    void deleteById();

//    void settingUserRoles(Long[] rids, Long id);

}
