package com.easylearn.easy;

import com.easylearn.pojo.entity.Users;
import com.easylearn.service.LoginService;
import com.easylearn.service.QuestionService;
import com.easylearn.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class EasyLearnJavaApplicationTests {

    @Autowired
    UsersService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    QuestionService questionService;
    @Test
    void reg(){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        Users user=new Users();
        user.setUsername("jjjj");
        user.setPassword( encoder.encode( "123456"));
        userService.RegUser(user,"role_superadmin");

    }

    @Test
    void testJoin(){
        questionService.getPage(1,10,null,null,null,1);
    }


}
