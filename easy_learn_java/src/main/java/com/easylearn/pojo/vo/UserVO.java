package com.easylearn.pojo.vo;

import com.easylearn.pojo.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {
    private Integer id;
    private String username;

    private List<Role> roleList;
}
