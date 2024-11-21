package com.easylearn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName chapter
 */
@TableName(value ="chapter")
@Data
public class Chapter implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer cid;

    /**
     * 
     */
    private String chapterName;

    /**
     * 
     */
    private Integer pos;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}