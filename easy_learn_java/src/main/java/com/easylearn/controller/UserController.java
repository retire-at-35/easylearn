package com.easylearn.controller;

import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.UserPageDto;
import com.easylearn.pojo.entity.Users;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/super")
public class UserController {

    @Resource
    UsersService usersService;

    // 支持通过用户名进行模糊查询,以及可以分页
    @RequestMapping("/getUser")
    public Result getByPage(UserPageDto userPageDto){
        PageBean pageResult = usersService.getPage(userPageDto);
        return Result.success(pageResult);
    }

    // 新增普通管理员
    @PostMapping("/addUser")
    public Result addUser(@RequestBody Users user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        usersService.addOne(user);
        return Result.success("操作成功");
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody Users user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        usersService.updateById(user);
        return Result.success("修改成功");
    }


    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam(value = "id",required = true) Integer id){
        usersService.deleteById(id);
        return Result.success("删除成功");
    }
}
