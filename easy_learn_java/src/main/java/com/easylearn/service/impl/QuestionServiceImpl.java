package com.easylearn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.pojo.Question;
import com.easylearn.service.QuestionService;
import com.easylearn.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 31696
* @description 针对表【question】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




