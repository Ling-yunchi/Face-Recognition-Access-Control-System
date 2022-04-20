package com.face.dao;

import com.face.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uto
 * @date 2022/4/18
 * @description
 */
@Repository
public interface RoleDao extends JpaRepository<Role, String> {
}
