package com.jpmarket.domain.user;


import com.jpmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String picture;

    @Column(columnDefinition = "integer default 0")
    private Long heartTemp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Column(columnDefinition = "boolean default false")
    private boolean emailVerified;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    @Builder
    public User(String name, String email, String password, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
    }
    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public User heartTempUpdate(Long score) {
        this.heartTemp += (score - this.heartTemp) / 2;

        return this;
    }

    public User setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public User setEmailVerified(Boolean set){
        this.emailVerified = set;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void completeSignUp() {
        emailVerified = true;
    }



}
