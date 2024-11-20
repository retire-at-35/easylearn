package com.easylearn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer qid;

    /**
     * 
     */
    private Integer cid;

    /**
     * 
     */
    private Integer sid;

    /**
     * 
     */
    private Integer tid;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    @TableField("`key`")
    private String key;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



    @TableField(exist = false)
    private String chapterName;

    @TableField(exist = false)
    private String sectionName;

    @TableField(exist = false)
    private List<Options> optionsList;
}