package com.springboot.repository;

import com.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 22:58 2018/3/27
 * @Modified By:
 */

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByMobilePhone(String mobilePhone);

}
