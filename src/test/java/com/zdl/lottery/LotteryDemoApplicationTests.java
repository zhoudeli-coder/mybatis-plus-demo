package com.zdl.lottery;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zdl.lottery.entities.Lottery;
import com.zdl.lottery.service.LotteryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryDemoApplicationTests {

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void contextLoads() {
        List<Lottery> lotteryList = lotteryService.list();
        int[][] lotteryArray = new int[lotteryList.size()][7];
        for (int i = 0; i < lotteryList.size(); i++) {
            Lottery lottery = lotteryList.get(i);
            int[] arr = {lottery.getMon(), lottery.getTue(), lottery.getWed(), lottery.getThu(), lottery.getFri(), lottery.getSat(), lottery.getSun()};
            lotteryArray[i] = arr;
        }
        printRedCountMsg(lotteryArray, lotteryList.size());
        printRedCountMsg(lotteryArray, 33 * 7);
        printRedCountMsg(lotteryArray, 33);
        printBlueCountMsg(lotteryArray, lotteryList.size());
        printBlueCountMsg(lotteryArray, 16);
    }

    private void printRedCountMsg(int[][] lotteryArray, int lastRange) {
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println(String.format("统计%d期次", lastRange));
        System.out.println(String.format("每个红色号码平均出现次数：%s", 6.0 * lastRange / 33.0));
        System.out.println("------------------------------------------------------------------------------------------");
        /* 出现次数*/
        Map<Integer, Integer> countRedMap = Maps.newLinkedHashMap();
        for (int i = 1; i < 34; i++) {
            for (int lotteryArrayLength = lotteryArray.length, k = lotteryArrayLength - lastRange; k < lotteryArrayLength; k++) {
                int[] lottery = lotteryArray[k];
                for (int j = 0; j < lottery.length - 1; j++) {
                    if (lottery[j] == i) {
                        Integer count = countRedMap.get(i);
                        if (Objects.isNull(count)) {
                            countRedMap.put(i, 1);
                        } else {
                            countRedMap.put(i, count + 1);
                        }
                        break;
                    }
                }
            }
        }
        Optional<Integer> minCount = countRedMap.values().stream().min(Integer::compareTo);
        countRedMap.forEach((key, count) -> {
            System.out.print(String.format("%02d\t", key));
            for (int i = 0; i < (count - minCount.get()); i++) {
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println();
        });
        System.out.println("==========================================================================================");
    }

    private void printBlueCountMsg(int[][] lotteryArray, int lastRange) {
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println(String.format("统计%d期次", lastRange));
        System.out.println(String.format("每个蓝色号码平均出现次数：%s", lastRange / 16.0));
        System.out.println("------------------------------------------------------------------------------------------");
        /* 出现次数*/
        Map<Integer, Integer> countBlueMap = Maps.newLinkedHashMap();
        for (int i = 1; i < 34; i++) {
            for (int lotteryArrayLength = lotteryArray.length, k = lotteryArrayLength - lastRange; k < lotteryArrayLength; k++) {
                int[] lottery = lotteryArray[k];
                if (i <= 16) {
                    if (lottery[6] == i) {
                        Integer count = countBlueMap.get(i);
                        if (Objects.isNull(count)) {
                            countBlueMap.put(i, 1);
                        } else {
                            countBlueMap.put(i, count + 1);
                        }
                    }
                }
            }
        }
        Optional<Integer> minCount = countBlueMap.values().stream().min(Integer::compareTo);
        countBlueMap.forEach((key, count) -> {
            System.out.print(String.format("%02d\t", key));
            for (int i = 0; i < (count - minCount.get()); i++) {
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println();
        });
        System.out.println("==========================================================================================");
    }
}
