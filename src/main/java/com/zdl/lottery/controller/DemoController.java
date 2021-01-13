package com.zdl.lottery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zdl.lottery.entities.Lottery;
import com.zdl.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/list")
    public IPage<Lottery> list(){
        IPage<Lottery> lotteryIPage = new Page<>(1, 5);
        return lotteryService.page(lotteryIPage);
    }

    @GetMapping("/add")
    public boolean add(){
        Lottery lottery = new Lottery();
        lottery.setMon(1);
        return lotteryService.save(lottery);
    }

    @GetMapping("/first")
    public Lottery first(){
        return lotteryService.firstLotter();
    }
}
