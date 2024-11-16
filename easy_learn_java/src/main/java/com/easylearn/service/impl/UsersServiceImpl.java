package com.easylearn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.pojo.Users;
import com.easylearn.service.UsersService;
import com.easylearn.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author 31696
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




