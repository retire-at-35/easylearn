package com.easylearn.pojo.dto;

import com.easylearn.pojo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String username;
    List<Role> roleList;
}
