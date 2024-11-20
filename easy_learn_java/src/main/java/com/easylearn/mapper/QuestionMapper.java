package com.easylearn.mapper;

import com.easylearn.pojo.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 31696
* @description 针对表【question】的数据库操作Mapper
* @createDate 2024-11-16 23:07:27
* @Entity com.easylearn.pojo.entity.Question
*/
@Mapper
public interface QuestionMapper extends MPJBaseMapper<Question> {

//    Page<Question> pageQuery(Integer cid, Integer sid, String content, Integer singleQuestionType);
}




