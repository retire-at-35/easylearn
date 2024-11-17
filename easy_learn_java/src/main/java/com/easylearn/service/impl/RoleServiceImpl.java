package com.easylearn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.pojo.entity.Role;
import com.easylearn.service.RoleService;
import com.easylearn.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 31696
* @description 针对表【role】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




