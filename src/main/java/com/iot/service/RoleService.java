package com.iot.service;

import com.iot.domain.Role;
import com.iot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */

public interface RoleService {

    public List<Role> getAllRole();

}
