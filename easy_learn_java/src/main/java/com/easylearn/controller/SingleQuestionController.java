package com.easylearn.controller;

import com.easylearn.constant.Constant;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.QuestionDto;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common/single")
public class SingleQuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/getPage")
    public Result getPage(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Integer cid,
                          Integer sid,
                          String content){
        System.err.println(sid);
        PageBean pageBean=questionService.getPage(page,pageSize,cid,sid,content, Constant.SINGLE_QUESTION_TYPE);
        return Result.success(pageBean);
    }

    @PostMapping("/addOrUpdateQuestion")
    public Result addSingleQuestion(@RequestBody QuestionDto questionDto){
        questionService.addQuestion(questionDto,Constant.SINGLE_QUESTION_TYPE);
        return Result.success("操作成功");
    }

    @DeleteMapping("/deleteSingleQuestion")
    public Result deleteById(Integer id){
        questionService.deleteById(id,Constant.SINGLE_QUESTION_TYPE);
        return Result.success("删除成功");
    }
}
