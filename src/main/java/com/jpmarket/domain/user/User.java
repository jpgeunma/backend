package com.jpmarket.domain.user;


import com.jpmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import java.math.BigDecimal;


@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
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

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
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

    public String getRoleKey() {
        return this.role.getKey();
    }
}
