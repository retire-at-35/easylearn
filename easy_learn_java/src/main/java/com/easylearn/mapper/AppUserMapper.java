package com.easylearn.mapper;

import com.easylearn.pojo.entity.AppUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
* @author 31696
* @description 针对表【app_user】的数据库操作Mapper
* @createDate 2024-11-22 22:48:59
* @Entity com.easylearn.pojo.entity.AppUser
*/
@Mapper
public interface AppUserMapper extends MPJBaseMapper<AppUser> {

    @Update("update app_user set avatar = #{url} where id =#{id}")
    void updateAvatar(Long id, String url);

    @Update("update app_user set password = #{newPassword} where id =#{userId}")
    void updatePasswd(Long userId, String newPassword);
}




