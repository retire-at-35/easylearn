package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.constant.Constant;
import com.easylearn.pojo.dto.AppUserDto;
import com.easylearn.pojo.entity.AppUser;
import com.easylearn.pojo.entity.AdminUserDetails;
import com.easylearn.pojo.vo.AppUserVO;
import com.easylearn.service.AppUserService;
import com.easylearn.mapper.AppUserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.AccountNotFoundException;

/**
* @author 31696
* @description 针对表【app_user】的数据库操作Service实现
* @createDate 2024-11-22 22:48:59
*/
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser>
    implements AppUserService{

    @Autowired
    private AppUserMapper appUserMapper;

    @Resource
    AuthenticationManager manager;
    @Override
    public void register(@RequestBody AppUserDto appUserDto, HttpSession session) {
        System.err.println("验证码为"+session.getAttribute(Constant.CHECK_CODE));
        if (!appUserDto.getCheckCode().equals(session.getAttribute(Constant.CHECK_CODE))) {
            throw new RuntimeException("验证码错误");
        }
        String username = appUserDto.getUsername();
        String password = appUserDto.getPassword();
        LambdaQueryWrapper<AppUser> l = new LambdaQueryWrapper<AppUser>()
                .eq(AppUser::getUsername,username);
        AppUser user = appUserMapper.selectOne(l);
        if(user!=null){
            throw new RuntimeException("用户名已存在");
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(password);
        System.err.println("注册用户信息"+appUser);
        appUserMapper.insert(appUser);
    }

    @Override
    public AppUserVO login(AppUserDto appUserDto, HttpSession session) {
        if (!appUserDto.getCheckCode().equals(session.getAttribute(Constant.CHECK_CODE))) {
            throw new RuntimeException("验证码错误");
        }
        String username = appUserDto.getUsername();
        String password = appUserDto.getPassword();

        //1、根据用户名查询数据库中的数据
        AppUser appUser = appUserMapper.selectOne(new LambdaQueryWrapper<AppUser>().eq(AppUser::getUsername, username));
        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (appUser == null) {
            //账号不存在
            throw new RuntimeException("账号不存在");
        }
        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(appUser.getPassword())) {
            //密码错误
            throw new RuntimeException("密码错误");
        }
        AppUserVO appUserVO = new AppUserVO();
        BeanUtils.copyProperties(appUser,appUserVO);
        return appUserVO;
    }


}




