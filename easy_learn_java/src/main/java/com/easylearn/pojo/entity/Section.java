package com.easylearn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName section
 */
@TableName(value ="section")
@Data
public class Section implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer sid;

    /**
     * 
     */
    private Integer cid;

    /**
     * 
     */
    private String sectionName;

    /**
     * 
     */
    private Integer pos;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}