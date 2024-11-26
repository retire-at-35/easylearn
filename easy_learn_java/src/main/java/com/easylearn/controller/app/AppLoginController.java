package com.easylearn.controller.app;


import com.easylearn.constant.Constant;
import com.easylearn.pojo.dto.AppUserDto;
import com.easylearn.pojo.vo.AppUserVO;
import com.easylearn.pojo.vo.Result;
import com.easylearn.properties.JwtProperties;
import com.easylearn.service.AppUserService;
import com.easylearn.util.CreateImageCode;
import com.easylearn.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// app端用户登录的接口
@RestController
@RequestMapping("/pub")
@Slf4j
public class AppLoginController {
    @Resource
    private AppUserService appUserService;



    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/getCheckCode")
    public void checkCode(HttpServletResponse response, HttpSession session) throws IOException {
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = vCode.getCode();
        session.setAttribute(Constant.CHECK_CODE, code);
        vCode.write(response.getOutputStream());
    }



    @PostMapping("/applogin")
    public Result login(@RequestBody AppUserDto appUserDto, HttpSession session) throws Exception {
        System.err.println(appUserDto);
        AppUserVO appUserVO = appUserService.login(appUserDto,session);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", appUserVO.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAppSecretKey(),
                jwtProperties.getAppTtl(),
                claims);
        appUserVO.setToken(token);
        return Result.success(appUserVO);
    }

    @PostMapping("/register")
    public Result register(@RequestBody AppUserDto appUserDto,HttpSession session){
        System.err.println("注册信息"+appUserDto);
        String password = appUserDto.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        appUserDto.setPassword(password);
        appUserService.register(appUserDto,session);
        return Result.success("注册成功");
    }



}
