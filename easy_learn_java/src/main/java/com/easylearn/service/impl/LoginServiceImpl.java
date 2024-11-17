package com.easylearn.service.impl;
import com.easylearn.constant.SessionCode;
import com.easylearn.pojo.dto.UserDto;
import com.easylearn.pojo.entity.MyUserDetails;
import com.easylearn.pojo.entity.Users;
import com.easylearn.service.LoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    AuthenticationManager manager;
    public UserDto login(Users user, HttpSession session)  {
        // 先验证验证码
//        String code = (String)session.getAttribute(SessionCode.CHECK_CODE);
//        System.err.println("验证码为"+code);
//        System.err.println("正确验证码为"+user.getCheckCode());
        if (!user.getCheckCode().equals(session.getAttribute(SessionCode.CHECK_CODE))) {
            throw new RuntimeException("验证码错误");
        }
        UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication auth = manager.authenticate(uToken);
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        //使用session保存登录状态,注意改变跨域设置，使能够在前后端分离的项目中使用 sesssion
        //同时axios使用Credentials=true属性
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(myUserDetails,userDto);
        return userDto;
    }
}

