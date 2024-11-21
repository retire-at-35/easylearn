package com.easylearn.service;

import com.easylearn.pojo.dto.ChapterPageDto;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 31696
* @description 针对表【chapter】的数据库操作Service
* @createDate 2024-11-16 23:07:27
*/
public interface ChapterService extends IService<Chapter> {

    PageBean getPage(ChapterPageDto pageDto);

    void addOne(Chapter chapter);

    List<Chapter> getAllChapter();

    void updateIfNotConflict(Chapter chapter);

    void deleteById(Integer id);
}
