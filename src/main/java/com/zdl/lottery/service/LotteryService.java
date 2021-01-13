package com.zdl.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdl.lottery.entities.Lottery;
import org.springframework.stereotype.Service;

@Service
public interface LotteryService extends IService<Lottery> {
    Lottery firstLotter();
}
