package com.easylearn.service;

import com.easylearn.pojo.entity.Tmtype;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 31696
* @description 针对表【tmtype】的数据库操作Service
* @createDate 2024-11-16 23:07:27
*/
public interface TmtypeService extends IService<Tmtype> {

    List<Tmtype> getAll();

    void updateOrSave(Tmtype tmtype);

    void deleteById(Integer id);
}
