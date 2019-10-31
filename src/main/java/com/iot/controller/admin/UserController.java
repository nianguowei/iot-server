package com.iot.controller.admin;

import com.github.pagehelper.PageInfo;
import com.iot.domain.User;
import com.iot.service.UserService;
import com.iot.utils.BusinessException;
import com.iot.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @author Nian Guowei
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/{username}")
    public User queryByUsername(@PathVariable String username) {
        return userService.queryByUsername(username);
    }

    @PostMapping(value = "/pager")
    public PageInfo queryCompanyPager(@RequestBody User user,
                                      @RequestParam(value="pageNum", required = false) Integer pageNum,
                                      @RequestParam(value="pageSize", required = false) Integer pageSize){
        return userService.queryList(user,pageNum,pageSize);
    }


    @PostMapping
    public void insert(@RequestBody User user) throws BusinessException {
        user.setAddUser(Util.getCurrentUser().getUsername());
        userService.insert(user);
    }
//
//    @RequestMapping("/isAdmin")
//    public Boolean isAdmin() {
//        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
//        for (GrantedAuthority authority : authorities) {
//            if (authority.getAuthority().contains("超级管理员")) {
//                return true;
//            }
//        }
//        return false;
//    }

}
