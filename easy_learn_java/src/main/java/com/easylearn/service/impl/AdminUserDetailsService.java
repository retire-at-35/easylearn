package com.easylearn.service.impl;

import com.easylearn.pojo.entity.AdminUserDetails;
import com.easylearn.pojo.entity.Users;
import com.easylearn.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class AdminUserDetailsService implements UserDetailsService {
    @Resource
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersService.byUsername(username);
        AdminUserDetails md = new AdminUserDetails();
        BeanUtils.copyProperties(user, md);
        return md;
    }
}
