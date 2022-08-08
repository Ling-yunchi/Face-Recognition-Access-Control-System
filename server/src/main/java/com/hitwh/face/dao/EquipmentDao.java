package com.hitwh.face.dao;

import com.hitwh.face.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author uto
 * @date 2022/4/18
 * @description
 */
@Repository
public interface EquipmentDao extends JpaRepository<Equipment, String> {
    @Query(value = "select e from Equipment e where e.name like %?1%")
    Page<Equipment> findByNameLikePageable(String name, Pageable pageable);
}
