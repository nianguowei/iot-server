package com.iot.mapper;

import com.iot.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户管理mapper
 *
 * @author Nian Guowei
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User loadUserByUsername(@Param("username") String username);

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
     * 删除用户信息
     */
    void deleteByUsername(@Param("username") String username);

}
