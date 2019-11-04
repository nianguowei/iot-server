package com.iot.service;

import com.github.pagehelper.PageInfo;
import com.iot.domain.User;
import com.iot.utils.BusinessException;
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
     * 插入用户
     * @param user
     */
    void insert(User user) throws BusinessException;

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user) throws BusinessException;

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    PageInfo<User> queryPage(User user, Integer pageNum, Integer pageSize);

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    User queryByUsername(String username);

    /**
     * 删除用户信息
     */
    void deleteByUsername(String username);

    /**
     * 设置用户角色
     * @param rids
     * @param id
     */
//    void settingUserRoles(Long[] rids, Long id);

}
