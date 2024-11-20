package com.easylearn.mapper;

import com.easylearn.pojo.entity.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 31696
* @description 针对表【chapter】的数据库操作Mapper
* @createDate 2024-11-16 23:07:27
* @Entity com.easylearn.pojo.entity.Chapter
*/
@Mapper
public interface ChapterMapper extends MPJBaseMapper<Chapter> {
    // 如果有自定义的分页查询方法
}




