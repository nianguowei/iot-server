package com.iot.controller;

import com.github.pagehelper.PageInfo;
import com.iot.domain.User;
import com.iot.service.UserService;
import com.iot.utils.BusinessException;
import com.iot.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @author Nian Guowei
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户管理"})
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户信息")
    @GetMapping(value = "/{username}")
    public User queryByUsername(@PathVariable String username) {
        return userService.queryByUsername(username);
    }

    @ApiOperation(value = "用户分页列表")
    @PostMapping(value = "/pager")
    public PageInfo queryPager(@RequestBody User user,
                                      @RequestParam(value="pageNum", required = false) Integer pageNum,
                                      @RequestParam(value="pageSize", required = false) Integer pageSize){
        return userService.queryPage(user,pageNum,pageSize);
    }

    @ApiOperation(value = "新增用户")
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
