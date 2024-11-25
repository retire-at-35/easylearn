package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.constant.Constant;
import com.easylearn.context.BaseContext;
import com.easylearn.pojo.dto.AppUpdatePasswordDto;
import com.easylearn.pojo.dto.AppUserDto;
import com.easylearn.pojo.entity.AppUser;
import com.easylearn.pojo.vo.AppUserVO;
import com.easylearn.service.AppUserService;
import com.easylearn.mapper.AppUserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author 31696
* @description 针对表【app_user】的数据库操作Service实现
* @createDate 2024-11-22 22:48:59
*/
@Service
@Slf4j
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser>
    implements AppUserService{

    @Autowired
    private AppUserMapper appUserMapper;

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

    @Override
    public void updateAvatar(String url) {
        Long id = BaseContext.getCurrentId();
        appUserMapper.updateAvatar(id,url);
    }

    @Override
    public void updatePasswd(AppUpdatePasswordDto dto) {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<AppUser> appUserLambdaQueryWrapper = new LambdaQueryWrapper<AppUser>()
                .eq(AppUser::getId,userId);
        AppUser user = appUserMapper.selectOne(appUserLambdaQueryWrapper);
        String password = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes());
        String password1 = user.getPassword();
       if(!password.equals(password1)){
           throw new RuntimeException("原密码错误");
       }
       log.info("修改密码信息{},{}",userId,dto.getNewPassword());
       appUserMapper.updatePasswd(userId,DigestUtils.md5DigestAsHex(dto.getNewPassword().getBytes()));
    }


}




