package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.constant.Constant;
import com.easylearn.mapper.QuestionMapper;
import com.easylearn.mapper.SectionMapper;
import com.easylearn.pojo.dto.ChapterPageDto;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.entity.Chapter;
import com.easylearn.pojo.entity.Question;
import com.easylearn.pojo.entity.Section;
import com.easylearn.pojo.entity.Users;
import com.easylearn.service.ChapterService;
import com.easylearn.mapper.ChapterMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 31696
* @description 针对表【chapter】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
    implements ChapterService{

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private SectionMapper sectionMapper;

    @Resource
    private QuestionMapper questionMapper;
    @Override
    public PageBean getPage(ChapterPageDto pageDto) {
        if(pageDto.getPage()==null&&pageDto.getPageSize()==null){
            pageDto.setPage(1);
            pageDto.setPageSize(10);
        }
        Page<Chapter> chapterPage = new Page<>(pageDto.getPage(), pageDto.getPageSize());
        System.err.println(pageDto);
        LambdaQueryWrapper<Chapter> chapterWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(pageDto.getName())) {
            chapterWrapper.like(Chapter::getChapterName, pageDto.getName());
        }
        chapterWrapper.orderByAsc(Chapter::getPos);
        Page<Chapter> pageData = chapterMapper.selectPage(chapterPage, chapterWrapper);
        PageBean pageBean = new PageBean();
//        System.err.println(pageData.getRecords());
//        System.err.println(pageData.getTotal());
        pageBean.setRows(pageData.getRecords());
        pageBean.setTotal(pageData.getTotal());
        return pageBean;
    }

    @Override
    public void addOne(Chapter chapter) {
        LambdaQueryWrapper<Chapter> posWrapper = new LambdaQueryWrapper<Chapter>().eq(Chapter::getPos, chapter.getPos());
        Chapter c1 = chapterMapper.selectOne(posWrapper);
        if(c1 != null){
            throw new RuntimeException("请检查新增的章节次序");
        }
        LambdaQueryWrapper<Chapter> nameWrapper = new LambdaQueryWrapper<Chapter>().eq(Chapter::getChapterName, chapter.getChapterName());
        c1 = chapterMapper.selectOne(nameWrapper);
        if(c1 != null){
            throw new RuntimeException("请检查新增的章节名称");
        }
        chapterMapper.insert(chapter);
        // 把redis全删了
        cleanCache();
    }

    @Override
    public List<Chapter> getAllChapter() {
        return chapterMapper.selectList(new LambdaQueryWrapper<Chapter>());
    }

    @Override
    public void updateIfNotConflict(Chapter chapter) {
        LambdaQueryWrapper<Chapter> posWrapper = new LambdaQueryWrapper<Chapter>().eq(Chapter::getPos, chapter.getPos());
        Chapter c1 = chapterMapper.selectOne(posWrapper);
        if(c1 != null){
            throw new RuntimeException("请检查的章节次序");
        }
        chapterMapper.updateById(chapter);
        cleanCache();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // 删除题目
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new LambdaQueryWrapper<Question>()
                .eq(Question::getCid,id);
        questionMapper.delete(questionLambdaQueryWrapper);
        // 删除节
        LambdaQueryWrapper<Section> sectionLambdaQueryWrapper = new LambdaQueryWrapper<Section>()
                .eq(Section::getCid, id);
        sectionMapper.delete(sectionLambdaQueryWrapper);
        // 删除对应章
        chapterMapper.deleteById(id);
        cleanCache();
    }

    void cleanCache(){
        redisTemplate.opsForHash().delete(Constant.SINGLE_QUESTION_identifier);
        redisTemplate.opsForHash().delete(Constant.MULTIPLE_QUESTION_identifier);
        redisTemplate.opsForHash().delete(Constant.JUDGE_QUESTION_identifier);
    }
}




