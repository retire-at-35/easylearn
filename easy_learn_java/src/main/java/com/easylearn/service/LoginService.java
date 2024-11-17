package com.easylearn.service;

import com.easylearn.pojo.dto.UserDto;
import com.easylearn.pojo.entity.Users;
import jakarta.servlet.http.HttpSession;


public interface LoginService {
    UserDto login(Users user, HttpSession session) throws Exception;

}
