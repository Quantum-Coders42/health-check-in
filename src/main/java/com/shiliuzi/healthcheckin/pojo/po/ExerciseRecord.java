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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 运动打卡记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("exercise_records")
public class ExerciseRecord {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "`user_id`")
    @Column(comment = "用户ID", notNull = true)
    @Index(name = "idx_user_id", type = IndexTypeEnum.NORMAL)
    private Long userId;

    /**
     * 打卡日期
     */
    @TableField(value = "`record_date`")
    @Column(comment = "打卡日期", notNull = true)
    private LocalDate recordDate;

    /**
     * 运动描述
     */
    @TableField(value = "`description`")
    @Column(comment = "运动描述", length = 500)
    private String description;

    /**
     * 运动类型ID
     */
    @TableField(value = "`exercise_type_id`")
    @Column(comment = "运动类型ID", notNull = true)
    private Long exerciseTypeId;

    /**
     * 运动时长（分钟）
     */
    @TableField(value = "`duration`")
    @Column(comment = "运动时长（分钟）", notNull = true)
    private Integer duration;

    /**
     * 运动强度（1-5）
     */
    @TableField(value = "`intensity`")
    @Column(comment = "运动强度（1-5）", notNull = true)
    private Integer intensity;

    /**
     * 创建时间
     */
    @TableField(value = "`created_at`")
    @Column(comment = "创建时间", notNull = true)
    private LocalDateTime createdAt;
}