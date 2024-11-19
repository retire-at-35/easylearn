package com.easylearn.pojo.dto;

import lombok.Data;

@Data
public class UserPageDto {
    Integer page;
    Integer pageSize;
    String username;
}
