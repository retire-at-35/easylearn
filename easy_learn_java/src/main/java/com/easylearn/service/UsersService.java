package com.easylearn.service;

import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.UserPageDto;
import com.easylearn.pojo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easylearn.pojo.vo.UserVO;

import java.util.List;

/**
* @author 31696
* @description 针对表【users】的数据库操作Service
* @createDate 2024-11-16 23:07:27
*/
public interface UsersService extends IService<Users> {

    public Users byUsername(String username);

    void RegUser(Users user, String roleUser);

    PageBean getPage(UserPageDto userPageDto);

    List<Users> findByRole(String role);

    void addOne(Users user);

    void deleteById(Integer id);
}
