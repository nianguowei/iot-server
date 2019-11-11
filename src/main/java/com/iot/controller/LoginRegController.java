package com.iot.controller;

import com.iot.domain.RespBean;
import com.iot.utils.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author Nian Guowei
 */
@RestController
public class LoginRegController {

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     * @return
     */
    @RequestMapping("/login_page")
    public RespBean loginPage() throws BusinessException {
        throw new BusinessException(0, "尚未登录，请登录!");
    }
}
