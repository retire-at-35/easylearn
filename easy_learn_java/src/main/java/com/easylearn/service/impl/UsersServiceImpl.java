package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.mapper.RoleMapper;
import com.easylearn.pojo.entity.Role;
import com.easylearn.pojo.entity.Users;
import com.easylearn.service.UsersService;
import com.easylearn.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 31696
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UsersMapper usersMapper;

    public Users byUsername(String username) {
        LambdaQueryWrapper<Users> uwrapper = new LambdaQueryWrapper<Users>().eq(Users::getUsername, username);
        Users user = usersMapper.selectOne(uwrapper);
        LambdaQueryWrapper<Role> rwrapper = new LambdaQueryWrapper<Role>().eq(Role::getUserId, user.getId());
        List<Role> roles = roleMapper.selectList(rwrapper);
        user.setRoleList(roles);
        return user;
    }

    @Transactional
    public void RegUser(Users user, String roleName) {
        usersMapper.insert(user);
        Integer userId = user.getId();
        Role role = Role.builder()
                .userId(userId).rolename(roleName).build();
        roleMapper.insert(role);
    }

}




