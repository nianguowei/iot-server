package com.iot.service;

import com.iot.domain.Role;
import com.iot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * 角色管理
 *
 * @author Nian Guowei
 */

public interface RoleService {

    /**
     *
     * @return
     */
    public List<Role> queryList();


}
