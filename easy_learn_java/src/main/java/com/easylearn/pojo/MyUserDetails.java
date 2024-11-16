package com.easylearn.pojo;

import com.easylearn.pojo.Role;
import com.easylearn.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails extends User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> lists=new ArrayList<>();

        for(Role role :getRoles()){
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(role.getRoleName());
            lists.add(authority);
        }

        return lists;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}
