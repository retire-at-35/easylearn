package com.easylearn.controller;

import com.easylearn.pojo.dto.ChapterPageDto;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.entity.Chapter;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.ChapterService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common/chapter")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @GetMapping("/getByPage")
    public Result getByPage(ChapterPageDto pageDto)
    {
        PageBean pageResult = chapterService.getPage(pageDto);
        return Result.success(pageResult);
    }

    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody Chapter chapter){
        chapterService.addOne(chapter);
        return Result.success("增加成功");
    }


}
