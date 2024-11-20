package com.easylearn.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tmtype
 */
@TableName(value ="tmtype")
@Data
public class Tmtype implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer tid;

    /**
     * 
     */
    private String tytypeName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}