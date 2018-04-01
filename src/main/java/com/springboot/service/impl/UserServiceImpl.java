package com.springboot.service.impl;

import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import com.springboot.service.UserService;
import com.springboot.util.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 23:12 2018/3/27
 * @Modified By:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByMobilePhone(String mobilePhone) {

        return userRepository.findByMobilePhone(mobilePhone);
    }

    @Override
    public Optional<User> findById(Integer id){
        Optional<User> user = userRepository.findById(id);
        return  user;
    }


    @Override
    public boolean matchMobilePhone(String mobilePhone) {
        if (Pattern.matches(WebConfig.REGEX_MOBILE, mobilePhone)) {
            return true;
        } else {
            return false;
        }
    }



}
