package com.easylearn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easylearn.pojo.Chapter;
import com.easylearn.service.ChapterService;
import com.easylearn.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

/**
* @author 31696
* @description 针对表【chapter】的数据库操作Service实现
* @createDate 2024-11-16 23:07:27
*/
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
    implements ChapterService{

}




