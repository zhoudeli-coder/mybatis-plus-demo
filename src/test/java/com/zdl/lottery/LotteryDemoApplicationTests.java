package com.zdl.lottery;

import com.zdl.lottery.entities.Lottery;
import com.zdl.lottery.service.LotteryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryDemoApplicationTests {

	@Autowired
	private LotteryService lotteryService;

	@Test
	public void contextLoads() {
		List<Lottery> lotteryList = lotteryService.list();
		System.out.println(lotteryList);
	}
}
