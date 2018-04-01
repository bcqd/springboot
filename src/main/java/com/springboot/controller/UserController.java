package com.springboot.controller;

import com.springboot.annotation.SLLog;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import com.springboot.service.UserService;
import com.springboot.util.WebConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: chuan.bai
 * @Description 用户模块api接口
 * @Date: Created on 19:53 2018/4/1
 * @Modified By:
 */

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /**
     * 注册接口
     * @param data
     * @return
     */
    @PostMapping(value = "register")
    @SLLog
    public Map<String, Object> register(@RequestBody String data) {
        JSONObject jo = new JSONObject(data);
        Map<String,Object> map = new HashMap<>();
        if (jo.isNull("mobilePhone") || jo.isNull("password")) {
            return WebConfig.PARM_ERR();
        }
        String mobilePhone = jo.getString("mobilePhone");
        String password = jo.getString("password");

        if (!userService.matchMobilePhone(mobilePhone)) {
            return WebConfig.ERR("手机号码格式不正确");
        }
        if (userService.findByMobilePhone(mobilePhone) != null) {
            return WebConfig.USER_ISNULL();
        }
        User user = new User();
        user.setMobilePhone(mobilePhone);
        user.setPassword(password);

        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        userRepository.save(user);
        map.put("nickName", user.getNickName());
        map.put("headPicture", user.getHeadPicture());
        map.put("mobilePhone",user.getMobilePhone());
        map.put("userId",String.valueOf(user.getId()));
        return WebConfig.SUCCESS(map);
    }


    /**
     * 登录接口
     * @param data
     * @return
     */
    @PostMapping(value = "login")
    @SLLog
    public Map<String, Object> login(@RequestBody String data) {
        JSONObject jo = new JSONObject(data);
        Map<String, Object> map = new HashMap<>();
        if (!WebConfig.checkJson(new String[]{"mobilePhone", "password"}, data)) {
            return WebConfig.PARM_ERR();
        }
        String mobilePhone = jo.getString("mobilePhone");
        String password = jo.getString("password");
        Optional <User> user = userService.findByMobilePhone(mobilePhone);
        if (!user.isPresent()) {
            return WebConfig.USER_ISNULL();
        }
        String truePassword = user.get().getPassword();
        if (!truePassword.equals(password)) {
            return WebConfig.ERR("密码错误");
        }
        String nickName = user.get().getNickName();
        String headPicture = user.get().getHeadPicture();

        map.put("nickName", nickName);
        map.put("headPicture", headPicture);
        map.put("userId",String.valueOf(user.get().getId()));
        return WebConfig.SUCCESS(map);
    }


}
