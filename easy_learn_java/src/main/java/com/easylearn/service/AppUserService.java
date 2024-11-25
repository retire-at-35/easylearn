package com.easylearn.service;

import com.easylearn.pojo.dto.AppUpdatePasswordDto;
import com.easylearn.pojo.dto.AppUserDto;
import com.easylearn.pojo.entity.AppUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easylearn.pojo.vo.AppUserVO;
import jakarta.servlet.http.HttpSession;

/**
* @author 31696
* @description 针对表【app_user】的数据库操作Service
* @createDate 2024-11-22 22:48:59
*/
public interface AppUserService extends IService<AppUser> {

    void register(AppUserDto appUser, HttpSession session);


    AppUserVO login(AppUserDto appUserDto, HttpSession session);

    void updateAvatar(String url);

    void updatePasswd(AppUpdatePasswordDto appUser);
}
