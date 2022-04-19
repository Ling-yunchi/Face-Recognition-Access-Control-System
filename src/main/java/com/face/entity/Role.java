package com.face.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
/**
 * @author uto
 * @date 2022/4/17
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(length = 64)
    private String id;
    private String name;

    @ManyToMany
    @ToString.Exclude
    private Set<EquipmentGroup> groups;

}
