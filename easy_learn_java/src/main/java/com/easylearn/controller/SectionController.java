package com.easylearn.controller;

import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.entity.Chapter;
import com.easylearn.pojo.entity.Section;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.ChapterService;
import com.easylearn.service.SectionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/common/section")
public class SectionController {

    @Resource
    private SectionService sectionService;

    @Resource
    private ChapterService chapterService;

    @GetMapping("/getAllChapter")
    public Result getAllChapter(){
        return Result.success(chapterService.getAllChapter());
    }


    @RequestMapping("/getPage")
    public Result getByPage(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            Integer cid, String name){
        PageBean pageResult = sectionService.pageSearch(page,pageSize,cid,name);
        return Result.success(pageResult);
    }

    @PostMapping("/addSection")
    public Result addSection(@RequestBody Section section){
        sectionService.addOne(section);
        return Result.success("添加成功");
    }

    @GetMapping("/getSectionByCid")
    public Result getSectionByCid(Integer id){
        List<Section> list = sectionService.getSectionByCid(id);
        return Result.success(list);
    }
}
