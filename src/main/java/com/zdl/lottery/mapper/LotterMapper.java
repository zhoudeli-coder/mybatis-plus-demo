package com.zdl.lottery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdl.lottery.entities.Lottery;
//import org.apache.ibatis.annotations.Select;

public interface LotterMapper extends BaseMapper<Lottery> {

//    @Select("select * from lotteries limit 1")
    Lottery firstLotter();
}
