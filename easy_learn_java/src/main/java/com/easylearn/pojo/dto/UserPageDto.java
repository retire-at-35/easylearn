package com.easylearn.pojo.dto;

import lombok.Data;

@Data
public class UserPageDto {
    int page;
    int pageSize;
    String username;
}
