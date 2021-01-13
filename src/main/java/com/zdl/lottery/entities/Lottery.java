package com.zdl.lottery.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "lotteries")
public class Lottery {
    @TableId(value = "l_id", type = IdType.AUTO)
    private int id;

    @TableField("period")
    private Integer period;

    @TableField("mon")
    private Integer mon;

    @TableField("tue")
    private Integer tue;

    @TableField("wed")
    private Integer wed;

    @TableField("thu")
    private Integer thu;

    @TableField("fri")
    private Integer fri;

    @TableField("sat")
    private Integer sat;

    @TableField("sun")
    private Integer sun;
}


