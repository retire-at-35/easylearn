package com.easylearn.service;

import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.entity.Chapter;
import com.easylearn.pojo.entity.Section;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 31696
* @description 针对表【section】的数据库操作Service
* @createDate 2024-11-16 23:07:27
*/
public interface SectionService extends IService<Section> {

    PageBean pageSearch(Integer page, Integer pageSize, Integer cid, String name);

    void addOne(Section section);

    List<Section> getSectionByCid(Integer id);
}
