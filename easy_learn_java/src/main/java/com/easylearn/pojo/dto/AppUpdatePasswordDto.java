package com.easylearn.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppUpdatePasswordDto {
    private String username;

    /**
     *
     */
    private String password;

    private String newPassword;
}
