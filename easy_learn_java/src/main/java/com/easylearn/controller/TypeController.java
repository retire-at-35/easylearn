package com.easylearn.controller;

import com.easylearn.pojo.entity.Tmtype;
import com.easylearn.pojo.vo.Result;
import com.easylearn.service.TmtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypeController {

    @Autowired
    private TmtypeService tmtypeService;
    @GetMapping("/common/type/getAllType")
    public Result getAllType(){
        List<Tmtype> list= tmtypeService.getAll();
        return Result.success(list);
    }

    @PostMapping("/common/type/updateOrSave")
    public Result updateOrSave(@RequestBody Tmtype tmtype){
        tmtypeService.updateOrSave(tmtype);
        return Result.success("操作成功");
    }

    @DeleteMapping("super/type/deleteById")
    public Result deleteById(@RequestParam Integer id){
        tmtypeService.deleteById(id);
        return Result.success("删除成功");
    }
}
