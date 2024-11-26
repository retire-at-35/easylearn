package com.easylearn.controller.app;

import com.easylearn.pojo.entity.Question;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppQuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getAllQuestion")
    public Result getAllQuestionByType(Integer type){
        Map<Integer, Question> questionMap = questionService.getAllQuestionByType(type);
        return Result.success(questionMap);
    }
}
