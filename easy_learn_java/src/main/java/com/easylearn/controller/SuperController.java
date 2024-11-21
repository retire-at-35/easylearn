package com.easylearn.controller;

import com.easylearn.pojo.entity.Chapter;
import com.easylearn.pojo.entity.Section;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.ChapterService;
import com.easylearn.service.SectionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super")
public class SuperController {
    @Resource
    private ChapterService chapterService;
    @Resource
    private SectionService sectionService;
    // 专门来管理修改和删除章节
    @PutMapping("/updateChapter")
    public Result updateChapter(Chapter chapter){
        chapterService.updateIfNotConflict(chapter);
        return Result.success("修改成功");
    }
    @DeleteMapping("/deleteChapter")
    public Result deleteChapter(Integer id){
        chapterService.deleteById(id);
        return Result.success("删除成功");
    }


    @PutMapping("/updateSection")
    public Result updateSection(Section section){
        sectionService.updateIfNotConflictSection(section);
        return Result.success("修改成功");
    }
    @DeleteMapping("/deleteSection")
    public Result deleteSection(Integer id){
        sectionService.deleteById(id);
        return Result.success("删除成功");
    }

}
