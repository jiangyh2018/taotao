package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @description: sso接口的实现类
 * @author:
 * @create: 2018-10-05 10:33
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String param, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();

        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            return TaotaoResult.build(400, "参数不合法!");
        }

        List<TbUser> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            return TaotaoResult.ok(false);
        }

        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult register(TbUser user) {
        //检查数据是否可用
        if (StringUtils.isBlank(user.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空!");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "密码不能为空!");
        }

        TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
        if (!(boolean) taotaoResult.getData()) {
            return TaotaoResult.build(400, "用户名已存在!");
        }

        if (StringUtils.isNotBlank(user.getPhone())) {
            taotaoResult = checkData(user.getPhone(), 2);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "手机号已存在!");
            }
        }

        if (StringUtils.isNotBlank(user.getEmail())) {
            taotaoResult = checkData(user.getEmail(), 3);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "邮箱已存在!");
            }
        }
        if ("".equals(StringUtils.trim(user.getEmail()))) {
            user.setEmail(null);
        }

        //补全对象属性
        user.setCreated(new Date());
        user.setUpdated(new Date());

        //密码MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);

        userMapper.insertSelective(user);

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
        // 1、判断用户名密码是否正确。
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            TbUserExample example = new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(username);
            List<TbUser> userList = userMapper.selectByExample(example);
            if (userList == null || userList.size() == 0) {
                return TaotaoResult.build(400, "用户名不存在!");
            }
            TbUser user = userList.get(0);
            //校验密码
            if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
                return TaotaoResult.build(400, "用户名或密码错误!");
            }
            // 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
            String token = UUID.randomUUID().toString();
            System.out.println("token:" + token);
            // 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
            // 4、使用String类型保存Session信息。可以使用“前缀:token”为key
            user.setPassword(null);
            jedisClient.set(USER_INFO + ":" + token, JsonUtils.objectToJson(user));
            // 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
            jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
            // 6、返回TaotaoResult包装token。
            return TaotaoResult.ok(token);
        }
        return null;
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String userStr = jedisClient.get(USER_INFO + ":" + token);
        // 3、如果查询不到数据。返回用户已经过期。
        if (StringUtils.isBlank(userStr)) {
            return TaotaoResult.build(400, "用户登录已过期，请重新登录!");
        }
        // 4、如果查询到数据，说明用户已经登录。
        // 5、需要重置key的过期时间。
        jedisClient.expire(USER_INFO + ":" + token, SESSION_EXPIRE);
        // 6、把json数据转换成TbUser对象，然后使用TaotaoResult包装并返回。
        TbUser user = JsonUtils.jsonToPojo(userStr, TbUser.class);
        return TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult logout(String token) {
        jedisClient.expire(USER_INFO + ":" + token, 0);

        return TaotaoResult.ok();
    }
}