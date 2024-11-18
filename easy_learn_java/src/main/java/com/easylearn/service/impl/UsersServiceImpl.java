package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.constant.Constant;
import com.easylearn.mapper.RoleMapper;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.UserPageDto;
import com.easylearn.pojo.entity.Users;
import com.easylearn.pojo.vo.UserVO;
import com.easylearn.service.UsersService;
import com.easylearn.mapper.UsersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.easylearn.pojo.entity.Role;
import java.util.ArrayList;
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
                .userId(userId).roleName(roleName).build();
        roleMapper.insert(role);
    }


    @Override
    public PageBean getPage(UserPageDto userPageDto) {
        LambdaQueryWrapper<Users> userWrapper = new LambdaQueryWrapper<>();
        Page<Users> page = new Page<>(userPageDto.getPage(), userPageDto.getPageSize());
        if (StringUtils.hasText(userPageDto.getUsername())) {
            userWrapper.like(Users::getUsername, userPageDto.getUsername());
        }
        Page<Users> pageData = usersMapper.selectPage(page, userWrapper);
        System.out.println("Total records: " + pageData.getTotal());
        System.out.println("Current page: " + pageData.getCurrent());
        System.out.println("Page size: " + pageData.getSize());
        System.out.println("Records in current page: " + pageData.getRecords().size());
        List<Users> users = pageData.getRecords();
        List<Integer> userIds = new ArrayList<>();
        for (Users user : users) {
            userIds.add(user.getId());
        }
        if (!userIds.isEmpty()) {
            List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getUserId, userIds));
            for (Users user : users) {
                for (Role r : roles) {
                    if(user.getId() == r.getUserId()) {
                        ArrayList<Role> list = new ArrayList<>();
                        list.add(r);
                        user.setRoleList(list);
                    }
                }
            }
        }
        List<UserVO> data = new ArrayList<>();
        for (Users u : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(u, userVO);
            data.add(userVO);
        }
        PageBean pageBean = new PageBean();
        pageBean.setRows(data);
        pageBean.setTotal(pageData.getTotal());
        return pageBean;
    }

    @Override
    public List<Users> findByRole(String role) {
        return usersMapper.findByRole(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOne(Users user) {
        Role role = new Role();
        try {
            usersMapper.insert(user);
        }catch (Exception e){
            throw new RuntimeException("新增失败,账号已经存在");
        }
        role.setUserId(user.getId());
        role.setRoleName(Constant.ADMIN_Permissions);
        roleMapper.insert(role);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // 删除用户表以及他的权限表
        usersMapper.deleteById(id);
        LambdaQueryWrapper<Role> lw = new LambdaQueryWrapper<Role>().eq(Role::getUserId, id);
        roleMapper.delete(lw);
    }

}




