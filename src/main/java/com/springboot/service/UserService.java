package com.springboot.service;



import com.springboot.model.User;

import java.util.Optional;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 23:10 2018/3/27
 * @Modified By:
 */
public interface UserService {



    Optional<User> findByMobilePhone(String mobilePhone);

    Optional<User> findById(Integer id);

    boolean matchMobilePhone(String mobilePhone);

}
