package com.iot.mapper;

import com.iot.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色信息实现类
 *
 * @author Nian Guowei
 */
@Mapper
@Repository
public interface RolesMapper {
    int addRoles(@Param("roles") String[] roles, @Param("uid") Long uid);

    /**
     * 根据用户名获取角色列表
     * @param username
     * @return
     */
    List<Role> getRolesByUsername(@Param("username") String username);

    List<Role> getAllRole();
}
