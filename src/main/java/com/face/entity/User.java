package com.face.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author wangrong
 * @date 2022/4/14 18:12
 */
@Entity
@Data
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
}
