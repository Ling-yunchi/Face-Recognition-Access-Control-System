package com.face.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * @author wangrong
 * @date 2022/4/14 18:12
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(length = 64)
    private String id;
    private String username;
    private String password;
    private String phoneNum;

    @ManyToMany
    @ToString.Exclude
    private Set<Role> roles;
}
