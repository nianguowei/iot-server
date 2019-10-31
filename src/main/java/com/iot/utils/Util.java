package com.iot.utils;

import com.iot.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 公共类
 *
 * @author Nian Guowei
 */
public class Util {
    /**
     * 获取当前用户信息
     * @return
     */
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
