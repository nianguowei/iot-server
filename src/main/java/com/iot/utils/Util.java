package com.iot.utils;

import com.iot.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by sang on 2017/12/20.
 */
public class Util {
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
