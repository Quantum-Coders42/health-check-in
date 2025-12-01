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
 * 睡眠打卡记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sleep_records")
public class SleepRecord {

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
     * 睡眠日期（记录的是哪一天的睡眠）
     */
    @TableField(value = "`record_date`")
    @Column(comment = "睡眠日期", notNull = true)
    private LocalDate recordDate;

    /**
     * 睡眠时长（分钟）
     */
    @TableField(value = "`sleep_duration_minutes`")
    @Column(comment = "睡眠时长（分钟）", notNull = true)
    private Integer sleepDurationMinutes;

    /**
     * 入睡时间
     */
    @TableField(value = "`sleep_time`")
    @Column(comment = "入睡时间", notNull = true)
    private LocalDateTime sleepTime;

    /**
     * 起床时间
     */
    @TableField(value = "`wake_time`")
    @Column(comment = "起床时间", notNull = true)
    private LocalDateTime wakeTime;

    /**
     * 睡眠质量描述
     */
    @TableField(value = "`description`")
    @Column(comment = "睡眠质量描述", length = 500)
    private String description;

    /**
     * 创建时间
     */
    @TableField(value = "`created_at`")
    @Column(comment = "创建时间", notNull = true)
    private LocalDateTime createdAt;
}