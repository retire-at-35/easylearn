package com.easylearn.pojo.vo;

import lombok.Data;

@Data
public class SingleQuestionVO {
    private Integer qid;
    private Integer cid;
    private Integer sid;
    private Integer tid;
    private String content;
    private String key;
    private String chapterName;  // 对应 select 中的 chapter_name
    private String sectionName;  // 对应 select 中的 section_name
} 