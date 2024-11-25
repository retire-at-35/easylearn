package com.easylearn.controller.app;

import com.easylearn.pojo.dto.AppUpdatePasswordDto;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.AppUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@Slf4j
public class AppUserController {

    @Resource
    private AppUserService appUserService;
    @PutMapping("/updatePassword")
    public Result updatePasswd(@RequestBody AppUpdatePasswordDto dto){
        log.info("app用户修改密码{}",dto);
        appUserService.updatePasswd(dto);
        return Result.success("修改成功");
    }
}
