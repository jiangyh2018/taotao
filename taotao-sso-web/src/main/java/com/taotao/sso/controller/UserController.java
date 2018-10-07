package com.taotao.sso.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: sso表现层
 * @author:
 * @create: 2018-10-05 10:47
 **/
@Controller
public class UserController {
    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @Autowired
    private UserService userService;

    /**
     * 检查数据是否可用(type:1、2、3分别代表username、phone、email)
     *
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type) {
        TaotaoResult taotaoResult = userService.checkData(param, type);

        return taotaoResult;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/register")
    @ResponseBody
    public TaotaoResult register(TbUser user) {
        TaotaoResult taotaoResult = userService.register(user);

        return taotaoResult;
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public TaotaoResult login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult taotaoResult = userService.login(username, password);
        //登录成功后才写cookie
        if (taotaoResult.getStatus() == 200) {
            // 3、从返回结果中取token，写入cookie。Cookie要跨域。
            String token = taotaoResult.getData().toString();
            CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
        }

        return taotaoResult;
    }

    /**
     * 通过token查询用户信息
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult taotaoResult = userService.getUserByToken(token);
        //判断是否jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            return callback + "(" + JsonUtils.objectToJson(taotaoResult) + ");";
        }

        return JsonUtils.objectToJson(taotaoResult);
    }

    /**
     * 安全退出
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/logout/{token}")
    @ResponseBody
    public String logout(@PathVariable String token, String callback) {
        TaotaoResult taotaoResult = userService.logout(token);
        //判断是否jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            return callback + "(" + JsonUtils.objectToJson(taotaoResult) + ");";
        }

        return JsonUtils.objectToJson(taotaoResult);
    }
}