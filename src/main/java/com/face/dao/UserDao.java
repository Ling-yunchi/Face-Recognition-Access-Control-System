package com.face.dao;

import com.face.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangrong
 * @date 2022/4/16 17:59
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
