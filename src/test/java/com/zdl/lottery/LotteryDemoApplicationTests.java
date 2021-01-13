package com.zdl.lottery;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zdl.lottery.entities.Lottery;
import com.zdl.lottery.service.LotteryService;
import com.zdl.lottery.vo.KeyValueVo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

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
        List<KeyValueVo<Integer, Integer>> redCountVoList = printAndGetRedCountMsg(lotteryArray, lotteryList.size());
        List<KeyValueVo<Integer, Integer>> redCountVoList2 = printAndGetRedCountMsg(lotteryArray, 33 * 7);
        List<KeyValueVo<Integer, Integer>> redCountVoList3 = printAndGetRedCountMsg(lotteryArray, 33);
        List<KeyValueVo<Integer, Integer>> redCountVoList4 = printAndGetRedCountMsg(lotteryArray, 3);
        printMinAppearCount(6, redCountVoList, redCountVoList2, redCountVoList3, redCountVoList4);
    }

    private List<KeyValueVo<Integer, Integer>> printAndGetRedCountMsg(int[][] lotteryArray, int lastRange) {
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println(String.format("统计%d期次", lastRange));
        double avgCount = 6.0 * lastRange / 33.0;
        System.out.println(String.format("每个红色号码平均出现次数：%s", avgCount));
        System.out.println("------------------------------------------------------------------------------------------");

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
        List<KeyValueVo<Integer, Integer>> countBlueVoList = Lists.newArrayList();
        countRedMap.forEach((key, count) -> {
            KeyValueVo<Integer, Integer> vo = new KeyValueVo<>(key, count);
            countBlueVoList.add(vo);
        });
        System.out.println("==========================================================================================");
        return countBlueVoList;
    }

    private List<KeyValueVo<Integer, Integer>> printAndGetBlueCountMsg(int[][] lotteryArray, int lastRange) {
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
        List<KeyValueVo<Integer, Integer>> countBlueVoList = Lists.newArrayList();
        countBlueMap.forEach((key, count) -> {
            System.out.print(String.format("%02d(%03d)\t", key, count));
            for (int i = 0; i < (count - minCount.get()); i++) {
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println();
            KeyValueVo<Integer, Integer> vo = new KeyValueVo<>(key, count);
            countBlueVoList.add(vo);
        });
        System.out.println("==========================================================================================");
        return countBlueVoList;
    }

    private void sortAndPrint(List<KeyValueVo<Integer, Integer>> voList) {
        voList.sort(Comparator.comparing(KeyValueVo::getValue));
        KeyValueVo<Integer, Integer> minVo = voList.get(0);
        voList.forEach(item -> {
            System.out.print(String.format("%02d(%03d)\t", item.getKey(), item.getValue()));
            for (int i = 0; i < (item.getValue() - minVo.getValue()); i++) {
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println();

        });
        System.out.println("==========================================================================================");
    }

    /**
     * 打印各个区间都出现的 n个数字
     */
    private void printMinAppearCount(int n, List<KeyValueVo<Integer, Integer>>... voListCollection) {
        for (List<KeyValueVo<Integer, Integer>> voList : voListCollection) {
            sortAndPrint(voList);
        }

        List<Integer> resultList;
        int limit = n;
        do {
            resultList = voListCollection[0].stream().limit(limit).map(KeyValueVo::getKey).collect(Collectors.toList());
            for (int i = 1, voListCollectionLength = voListCollection.length; i < voListCollectionLength; i++) {
                List<KeyValueVo<Integer, Integer>> voList = voListCollection[i];
                resultList.retainAll(voList.stream().limit(limit).map(KeyValueVo::getKey).collect(Collectors.toList()));
            }
            limit += 1;
        } while (resultList.size() < n);

        System.out.println(String.format("前%d条记录中找出共同的%d个数字", limit, n));
        System.out.println(JSON.toJSON(resultList));
        System.out.println("==========================================================================================");
    }
}
