package com.easylearn.controller;

import com.easylearn.constant.Constant;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.UserDto;
import com.easylearn.pojo.dto.UserPageDto;
import com.easylearn.pojo.entity.Users;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.LoginService;
import com.easylearn.service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.easylearn.util.CreateImageCode;
import java.io.IOException;


@RestController
@RequestMapping("/pub")
public class LoginController {


    @Autowired
    UsersService usersService;
    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public Result login(@RequestBody Users user, HttpSession session) throws Exception {
        UserDto userDto= loginService.login(user,session);
        return Result.success(userDto);
    }

    @GetMapping("/checkCode")
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

    @RequestMapping("/getUser")
    public Result getByPage(UserPageDto userPageDto){
        PageBean pageResult = usersService.getPage(userPageDto);
        return Result.success(pageResult);
    }

}
