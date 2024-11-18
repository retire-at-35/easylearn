package com.easylearn.mapper;

import com.easylearn.pojo.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 31696
* @description 针对表【users】的数据库操作Mapper
* @createDate 2024-11-16 23:07:27
* @Entity com.easylearn.pojo.entity.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    List<Users> findByRole(@Param("role") String role);
}




