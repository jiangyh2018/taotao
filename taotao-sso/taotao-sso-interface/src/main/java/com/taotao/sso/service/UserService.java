package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 **/
public interface UserService {

    /**
     * 检查数据是否可用，
     * 格式如：zhangsan/ 1，其中zhangsan是校验的数据，type为类型，可选参数1、2、3分别代表username、phone、email
     * 可选参数callback：如果有此参数表示此方法为jsonp请求，需要支持jsonp。
     *
     * @param param
     * @param type
     * @return
     */
    TaotaoResult checkData(String param, Integer type);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    TaotaoResult register(TbUser user);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    TaotaoResult login(String username, String password);


    /**
     * 通过token查询用户信息
     *
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 安全退出
     *
     * @return
     */
    TaotaoResult logout(String token);

}