package com.hitwh.face.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author uto
 * @date 2022/4/18
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Equipment {

    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(length = 64)
    private String id;
    private String name;
    private String position;
    private String status;

    @ManyToOne
    private EquipmentGroup group;
}
