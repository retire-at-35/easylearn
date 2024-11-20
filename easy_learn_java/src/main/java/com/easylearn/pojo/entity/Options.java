package com.easylearn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName options
 */
@TableName(value ="options")
@Data
public class Options implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer oid;

    /**
     * 
     */
    private Integer qid;

    /**
     * 
     */
    private String op;

    /**
     * 
     */
    private Integer pos;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}