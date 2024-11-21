package com.easylearn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.mapper.QuestionMapper;
import com.easylearn.pojo.entity.Question;
import com.easylearn.pojo.entity.Tmtype;
import com.easylearn.service.TmtypeService;
import com.easylearn.mapper.TmtypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 31696
* @description 针对表【tmtype】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class TmtypeServiceImpl extends ServiceImpl<TmtypeMapper, Tmtype>
    implements TmtypeService{

    @Autowired
    private TmtypeMapper tmtypeMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Tmtype> getAll() {
        List<Tmtype> typeList = tmtypeMapper.selectList(new LambdaQueryWrapper<Tmtype>());
        return typeList;
    }

    @Override
    public void updateOrSave(Tmtype tmtype) {
        System.err.println(tmtype);
        tmtypeMapper.insertOrUpdate(tmtype);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // 删除这个类型的所有题目
        LambdaQueryWrapper<Question> questionWrapper = new LambdaQueryWrapper<Question>().eq(Question::getTid, id);
        questionMapper.delete(questionWrapper);
        // 删除这个类型
        tmtypeMapper.deleteById(id);
    }
}




