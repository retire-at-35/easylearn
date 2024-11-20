package com.easylearn.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.easylearn.pojo.entity.Options;
import com.easylearn.pojo.entity.Question;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionDto  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer qid;
    private Integer cid;
    private Integer sid;
    private Integer tid;
    private String content;
    private String key;
    private List<Options> optionsList;
}
