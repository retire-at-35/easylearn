package com.easylearn.controller.app;

import com.easylearn.pojo.vo.Result;
import com.easylearn.service.AppUserService;
import com.easylearn.util.AliOssUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping ("/app")
@Slf4j
public class CommonController {

    @Resource
    private AliOssUtil aliOssUtil;

    @Autowired
    private AppUserService appUserService;
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        log.info("文件信息{}",file);
        String url = aliOssUtil.upload(file);
        appUserService.updateAvatar(url);
        return Result.success(url);
    }
}
