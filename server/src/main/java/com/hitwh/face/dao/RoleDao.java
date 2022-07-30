package com.hitwh.face.dao;

import com.hitwh.face.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author uto
 * @date 2022/4/18
 * @description
 */
@Repository
public interface RoleDao extends JpaRepository<Role, String> {

    Set<Role> findAllByNameIn(Set<String> roles);
}
