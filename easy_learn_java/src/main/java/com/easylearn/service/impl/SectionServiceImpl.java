package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.constant.Constant;
import com.easylearn.mapper.QuestionMapper;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.entity.Chapter;
import com.easylearn.pojo.entity.Question;
import com.easylearn.pojo.entity.Section;
import com.easylearn.service.SectionService;
import com.easylearn.mapper.SectionMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 31696
* @description 针对表【section】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section>
    implements SectionService{


    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private QuestionMapper questionMapper;

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
        LambdaQueryWrapper<Section> sectionWrapper = new LambdaQueryWrapper<>();
        sectionWrapper.eq(Section::getPos,section.getPos()).eq(Section::getCid,section.getCid());
        Section s1 = sectionMapper.selectOne(sectionWrapper);
        if(s1 != null){
            throw new RuntimeException("题目位置有误，请检查");
        }
        sectionMapper.insert(section);
        cleanCache();
    }

    @Override
    public void updateIfNotConflictSection(Section section) {
        LambdaQueryWrapper<Section> sectionWrapper = new LambdaQueryWrapper<>();
        sectionWrapper.eq(Section::getPos,section.getPos()).eq(Section::getCid,section.getCid());
        Section s1 = sectionMapper.selectOne(sectionWrapper);
        if(s1 != null){
            throw new RuntimeException("题目位置有误，请检查");
        }
        sectionMapper.updateById(section);
        cleanCache();
    }

    @Override
    public void deleteById(Integer id) {
        // 删除题目
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new LambdaQueryWrapper<Question>()
                .eq(Question::getCid,id);
        questionMapper.delete(questionLambdaQueryWrapper);
        // 根据id删除
        sectionMapper.deleteById(id);
        cleanCache();
    }

    void cleanCache(){
        redisTemplate.opsForHash().delete(Constant.SINGLE_QUESTION_identifier);
        redisTemplate.opsForHash().delete(Constant.MULTIPLE_QUESTION_identifier);
        redisTemplate.opsForHash().delete(Constant.JUDGE_QUESTION_identifier);
    }
}




