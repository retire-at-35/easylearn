package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.entity.Chapter;
import com.easylearn.pojo.entity.Section;
import com.easylearn.service.SectionService;
import com.easylearn.mapper.SectionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 31696
* @description 针对表【section】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section>
    implements SectionService{

    @Resource
    private SectionMapper sectionMapper;

    @Override
    public PageBean pageSearch(Integer page, Integer pageSize, Integer cid, String name) {
        Page<Section> sectionPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Section> lqw = new LambdaQueryWrapper<>();
        if(StringUtils.hasText(name)){
            lqw.like(Section::getSectionName,name);
        }
        if(cid != null){
            lqw.eq(Section::getCid,cid);
        }
        Page<Section> pageData = sectionMapper.selectPage(sectionPage, lqw);
        PageBean pageBean = new PageBean();
        pageBean.setRows(pageData.getRecords());
        pageBean.setTotal(pageData.getTotal());
        return pageBean;

    }

    @Override
    public void addOne(Section section) {
        LambdaQueryWrapper<Section> sectionWrapper = new LambdaQueryWrapper<Section>();
        sectionWrapper.eq(Section::getPos,section.getPos()).eq(Section::getCid,section.getCid());
        Section s1 = sectionMapper.selectOne(sectionWrapper);
        if(s1 != null){
            throw new RuntimeException("题目位置有误，请检查");
        }
        sectionMapper.insert(section);
    }

    @Override
    public List<Section> getSectionByCid(Integer id) {
        LambdaQueryWrapper<Section> sectionWrapper = new LambdaQueryWrapper<Section>()
                .eq(Section::getCid,id);
        List<Section> sections = sectionMapper.selectList(sectionWrapper);
        return sections;
    }
}




