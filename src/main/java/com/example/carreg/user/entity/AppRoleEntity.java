package com.example.carreg.user.entity;

import com.example.carreg.core.entity.CoreEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "app_role")
@Entity
public class AppRoleEntity extends CoreEntity implements GrantedAuthority {
    //ROLE_USER, ROLE_ADMIN
    @Column(name = "authority")
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
