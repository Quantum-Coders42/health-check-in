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

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户名
    @TableField(value = "`username`")
    @Column(comment = "用户名", notNull = true)
    @Index(name = "username", type = IndexTypeEnum.UNIQUE)
    private String username;

    // 密码（MD5加密）
    @TableField(value = "`password`")
    @Column(comment = "密码", notNull = true)
    private String password;
}
