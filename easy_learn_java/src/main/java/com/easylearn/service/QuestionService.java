package com.easylearn.service;

import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.QuestionDto;
import com.easylearn.pojo.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 31696
* @description 针对表【question】的数据库操作Service
* @createDate 2024-11-16 23:07:27
*/
public interface QuestionService extends IService<Question> {

    PageBean getPage(Integer page, Integer pageSize, Integer cid, Integer sid, String content,Integer type);


    void addQuestion(QuestionDto questionDto, Integer type);

    void deleteById(Integer id,Integer type);

    public Map<Integer,Question> getAllQuestionByType(Integer type);

}
