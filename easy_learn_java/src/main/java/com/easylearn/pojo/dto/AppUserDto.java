package com.easylearn.pojo.dto;

import com.easylearn.pojo.entity.AppUser;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AppUserDto {

    private String username;

    /**
     *
     */
    private String password;
    private String checkCode;
}
