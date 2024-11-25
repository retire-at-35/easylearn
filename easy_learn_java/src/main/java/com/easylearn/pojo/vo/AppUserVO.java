package com.easylearn.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserVO {
    private Integer id;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String avatar;

    private String token;

    private static final long serialVersionUID = 1L;
}
