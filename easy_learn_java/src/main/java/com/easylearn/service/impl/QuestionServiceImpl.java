package com.easylearn.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.constant.Constant;
import com.easylearn.mapper.OptionsMapper;
import com.easylearn.pojo.dto.PageBean;
import com.easylearn.pojo.dto.QuestionDto;
import com.easylearn.pojo.entity.*;
import com.easylearn.service.QuestionService;
import com.easylearn.mapper.QuestionMapper;

import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 31696
* @description 针对表【question】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{


    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private OptionsMapper optionsMapper;
    @Override
    public PageBean getPage(Integer page, Integer pageSize, Integer cid, Integer sid, String content,Integer type) {
//        select question.qid,question.cid,question.sid,question.tid,
//                question.content,question.`key`,c.name,s.name
////        from question join chapter c on question.cid = c.cid join section s on question.sid = s.sid
        MPJLambdaWrapper<Question> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(Question.class,Question::getChapterName,Question::getSectionName,Question::getOptionsList)
                .selectAll(Chapter.class)
                .select(Section::getSectionName)
                .innerJoin(Chapter.class,Chapter::getCid,Question::getCid)
                .innerJoin(Section.class,Section::getSid,Question::getSid);
        if(sid != null)
        {
            wrapper.eq(Question::getSid,sid);
        }
        if(cid != null)
        {
            wrapper.eq(Question::getCid,cid);
        }
        if(content != null){
            wrapper.like(Question::getContent,content);
        }
        wrapper.eq(Question::getTid,type);
        Page<Question> p = new Page<Question>(page,pageSize);
        Page<Question> pageData = questionMapper.selectJoinPage(p, wrapper);
        List<Question> data = pageData.getRecords();
        List<Integer> qidList = data.stream().map(item -> item.getQid()).collect(Collectors.toList());
        //查选项
        if(qidList.isEmpty())
        {
            return new PageBean(0l,new ArrayList<Question>());
        }
        LambdaQueryWrapper<Options> opWrapper = new LambdaQueryWrapper<>();
        opWrapper.in(Options::getQid,qidList);
        List<Options> options = optionsMapper.selectList(opWrapper);
        for (Question q : data) {
            ArrayList<Options> optionList = new ArrayList<>();
            for (Options option : options) {
                if(q.getQid() == option.getQid())
                {
                    optionList.add(option);
                }
            }
            q.setOptionsList(optionList);
        }
        PageBean result = new PageBean();
        result.setTotal(pageData.getTotal());
        result.setRows(data);
        System.out.println(result.getRows());
        return result;
    }

    @Override
    @Transactional
    public void addQuestion(QuestionDto questionDto, Integer type) {
        Question question = new Question();
        question.setTid(type);
        BeanUtils.copyProperties(questionDto,question);
        questionMapper.insertOrUpdate(question);
        for (int i=0;i<(type==3?2:4);i++){
            Options options = questionDto.getOptionsList().get(i);
            System.err.println(options.getOid());
            options.setQid(question.getQid());
            optionsMapper.insertOrUpdate(options);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        questionMapper.deleteById(id);
        LambdaQueryWrapper<Options> wrapper = new LambdaQueryWrapper<Options>().eq(Options::getQid,id);
        optionsMapper.delete(wrapper);
    }



}




