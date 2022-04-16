package com.face.dao;

import com.face.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangrong
 * @date 2022/4/16 17:59
 */
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
