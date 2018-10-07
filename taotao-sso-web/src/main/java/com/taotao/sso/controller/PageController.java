package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 跳转登录和注册页面
 * @author:
 * @create: 2018-10-06 21:33
 **/
@Controller
public class PageController {

    @RequestMapping(value = "/page/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/page/register")
    public String showRegister() {
        return "register";
    }

}