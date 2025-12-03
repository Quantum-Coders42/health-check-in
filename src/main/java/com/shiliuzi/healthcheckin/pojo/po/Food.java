package com.shiliuzi.healthcheckin.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tangzc.autotable.annotation.Index;
import com.tangzc.autotable.annotation.enums.IndexTypeEnum;
import com.tangzc.mpe.autotable.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 食物实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("foods")
public class Food {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 饮食打卡记录ID
     */
    @TableField(value = "`diet_record_id`")
    @Column(comment = "饮食打卡记录ID", notNull = true)
    @Index(name = "idx_diet_record_id", type = IndexTypeEnum.NORMAL)
    private Long dietRecordId;

    /**
     * 食物名称
     */
    @TableField(value = "`food_name`")
    @Column(comment = "食物名称", notNull = true, length = 100)
    private String foodName;

    /**
     * 热量（卡路里）
     */
    @TableField(value = "`calories`")
    @Column(comment = "热量（卡路里）")
    private Double calories;

    /**
     * 重量（克）
     */
    @TableField(value = "`weight`")
    @Column(comment = "重量（克）")
    private Integer weight;

    /**
     * 食物类型ID
     */
    @TableField(value = "`food_type_id`")
    @Column(comment = "食物类型ID")
    private Long foodTypeId;

    /**
     * 创建时间
     */
    @TableField(value = "`created_at`")
    @Column(comment = "创建时间", notNull = true)
    private LocalDateTime createdAt;
}