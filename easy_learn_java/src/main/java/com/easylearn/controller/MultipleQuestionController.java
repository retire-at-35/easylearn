package com.easylearn.controller;

import com.easylearn.constant.Constant;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.QuestionDto;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/common/multiple"))
public class MultipleQuestionController {
    @Resource
    private QuestionService questionService;

    @GetMapping("/getPage")
    public Result getPage(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Integer cid,
                          Integer sid,
                          String content){
        System.err.println(sid);
        PageBean pageBean=questionService.getPage(page,pageSize,cid,sid,content, Constant.MULTIPLE_QUESTION_TYPE);
        return Result.success(pageBean);
    }

    @PostMapping("/addOrUpdateQuestion")
    public Result addSingleQuestion(@RequestBody QuestionDto questionDto){
        questionService.addQuestion(questionDto, Constant.MULTIPLE_QUESTION_TYPE);
        return Result.success("操作成功");
    }

    @DeleteMapping("/deleteMultipleQuestion")
    public Result deleteById(Integer id){
        questionService.deleteById(id,Constant.MULTIPLE_QUESTION_TYPE);
        return Result.success("删除成功");
    }
}
