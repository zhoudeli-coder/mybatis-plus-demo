package com.zdl.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdl.lottery.entities.Lottery;
import com.zdl.lottery.mapper.LotterMapper;
import com.zdl.lottery.service.LotteryService;
import org.springframework.stereotype.Service;

@Service
public class LotteryServiceImpl extends ServiceImpl<LotterMapper, Lottery> implements LotteryService {
}
