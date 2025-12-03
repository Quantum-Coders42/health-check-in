package com.shiliuzi.healthcheckin.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tangzc.mpe.autotable.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用餐类型实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("meal_types")
public class MealType {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用餐类型名称
     */
    @TableField(value = "`type_name`")
    @Column(comment = "用餐类型名称", notNull = true, length = 50)
    private String typeName;
}