package com.zdl.lottery;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        QueryWrapper queryWrapper = new QueryWrapper<Lottery>();
        queryWrapper.le("l_id", 5265);
        List<Lottery> lotteryList = lotteryService.list();
        int[][] lotteryArray = new int[lotteryList.size()][7];
        for (int i = 0; i < lotteryList.size(); i++) {
            Lottery lottery = lotteryList.get(i);
            int[] arr = {lottery.getMon(), lottery.getTue(), lottery.getWed(), lottery.getThu(), lottery.getFri(), lottery.getSat(), lottery.getSun()};
            lotteryArray[i] = arr;
        }
//        List<KeyValueVo<Integer, Integer>> redCountVoList300 = printAndGetRedCountMsg(lotteryArray, 300);
//        sortAndPrint(redCountVoList300, false);
//        List<KeyValueVo<Integer, Integer>> redCountVoList100 = printAndGetRedCountMsg(lotteryArray, 100);
//        sortAndPrint(redCountVoList100, false);
//        List<KeyValueVo<Integer, Integer>> redCountVoList33 = printAndGetRedCountMsg(lotteryArray, 33);
//        sortAndPrint(redCountVoList33, false);
//        printMinAppearCount(5, redCountVoList100, redCountVoList33, redCountVoList300);
//
//        /* 奇偶数*/
//        oddAndEvenCalculate(lotteryArray, 33 * 7);
//        /* 差值*/
//        offsetCalculate(lotteryArray, 33);
//
//        List<KeyValueVo<Integer, Integer>> blueCountMsg300 = printAndGetBlueCountMsg(lotteryArray, 300);
//        List<KeyValueVo<Integer, Integer>> blueCountMsg100 = printAndGetBlueCountMsg(lotteryArray, 100);
//        List<KeyValueVo<Integer, Integer>> blueCountMsg16 =  printAndGetBlueCountMsg(lotteryArray, 16);
//
//        System.out.println("最近开奖结果：");
//        Arrays.stream(lotteryArray).skip(lotteryList.size() - 5).forEach(item -> System.out.println(JSON.toJSON(item)));
//        System.out.println("==========================================================================================");
//
//        /* 接近平均值范围的数字*/
//        List<KeyValueVo<Integer, Integer>> closeToAverageList300 = closeToAverage(0.3, redCountVoList300);
//        List<KeyValueVo<Integer, Integer>> closeToAverageList100 = closeToAverage(0.3, redCountVoList100);
//        List<KeyValueVo<Integer, Integer>> closeToAverageList33 = closeToAverage(0.3, redCountVoList33);
//
//        List<Integer> list300 = closeToAverageList300.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList());
//        List<Integer> list100 = closeToAverageList100.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList());
//        List<Integer> list33 = closeToAverageList33.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList());
//
//        /* 不同数组出现N次以上的数字*/
//        System.out.println("==========================================================================================");
//        System.out.println("红球");
//        List<Integer> list = numberAppearCountGtN(2, list300, list100, list33);
//        System.out.println(list);
//
//
//        List<KeyValueVo<Integer, Integer>> closeToAverage300 = closeToAverage(0.3, blueCountMsg300);
//        List<KeyValueVo<Integer, Integer>> closeToAverage100 = closeToAverage(0.3, blueCountMsg100);
//        List<KeyValueVo<Integer, Integer>> closeToAverage16 = closeToAverage(0.3, blueCountMsg16);
//        List<Integer> list2300 = closeToAverage300.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList());
//        List<Integer> list2100 = closeToAverage100.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList());
//        List<Integer> list233 = closeToAverage16.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList());
//
//        /* 不同数组出现N次以上的数字*/
//        System.out.println("==========================================================================================");
//        System.out.println("蓝球");
//        List<Integer> list2 = numberAppearCountGtN(2, list2300, list2100, list233);
//        System.out.println(list2);
        double[] rateList = {0.4};
        int[] rangeList = {33, 100, 300};
        for (int i = 300; i < lotteryArray.length; ) {
            System.out.println("从" + i + "期次开始统计");
            suanFaYanZheng(lotteryArray, rateList, rangeList, i);
            i += 100;
        }
    }

    private void suanFaYanZheng(int[][] totalLotteryArray, double[] rateList, int[] rangeList, int from) {
        int[][] lotteryArray = Arrays.stream(totalLotteryArray).limit(from).collect(Collectors.toList()).toArray(new int[][]{});
        List<List<KeyValueVo<Integer, Integer>>> redList = Lists.newArrayList();
        for (int range : rangeList) {
            List<KeyValueVo<Integer, Integer>> redCountVoList = printAndGetRedCountMsg(lotteryArray, range);
            redList.add(redCountVoList);
        }
        List<List<KeyValueVo<Integer, Integer>>> blueList = Lists.newArrayList();
        for (int range : rangeList) {
            List<KeyValueVo<Integer, Integer>> blueCountMsg = printAndGetBlueCountMsg(lotteryArray, range);
            blueList.add(blueCountMsg);
        }

        for (double rate : rateList) {
            System.out.println("========================================================================================== rate:" + rate);
            List<List<KeyValueVo<Integer, Integer>>> closeToAverageRedList = redList.stream().map(item -> closeToAverage(rate, item)).collect(Collectors.toList());
            List<List<KeyValueVo<Integer, Integer>>> closeToAverageBlueList = blueList.stream().map(item -> closeToAverage(rate, item)).collect(Collectors.toList());
            List<List<Integer>> closeToAverageRedArray = closeToAverageRedList.stream()
                    .map(item -> item.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList()))
                    .collect(Collectors.toList());
            List<List<Integer>> closeToAverageBlueArray = closeToAverageBlueList.stream()
                    .map(item -> item.stream().map(KeyValueVo::getKey).sorted().collect(Collectors.toList()))
                    .collect(Collectors.toList());

            /* 不同数组出现N次以上的数字*/
//            System.out.println("------------------------------------------------------------------------------------------");
//            System.out.println("红球");
            List<Integer> list = numberAppearCountGtN(rangeList.length - 1, closeToAverageRedArray);
            System.out.println(list);

            /* 不同数组出现N次以上的数字*/
//            System.out.println("------------------------------------------------------------------------------------------");
//            System.out.println("蓝球");
            List<Integer> list2 = numberAppearCountGtN(rangeList.length - 1, closeToAverageBlueArray);
            System.out.println(list2);
            System.out.println("------------------------------------------------------------------------------------------");
            int matchCount = 0;
            for (int j = from, totalLotteryArrayLength = totalLotteryArray.length; j < totalLotteryArrayLength; j++) {
                int[] array = totalLotteryArray[j];
                int count = 0;
                for (int i = 0, arrayLength = array.length; i < arrayLength - 1; i++) {
                    int k = array[i];
                    if (list.contains(k)) {
                        count += 1;
                    }
                }
                if (count >= 3) {
//                    System.out.println(JSON.toJSON(array));
                    matchCount += 1;
                } else if (list2.contains(array[6])) {
                    System.out.println(JSON.toJSON(array));
                    matchCount += 1;
                }
            }
            System.out.println(String.format("%d-%d符合条件的期次：%d", from, totalLotteryArray.length, matchCount));
        }
    }

    private List<Integer> numberAppearCountGtN(int n, List<List<Integer>> lists) {
        Map<Integer, Integer> countMap = Maps.newHashMap();
        for (List<Integer> list : lists) {
            for (Integer k : list) {
                Integer c = countMap.get(k);
                if (Objects.isNull(c)) {
                    c = 1;
                } else {
                    c += 1;
                }
                countMap.put(k, c);
            }
        }
        List<Integer> ret = Lists.newArrayList();
        countMap.forEach((k, v) -> {
            if (v >= n) {
                ret.add(k);
            }
        });
        return ret;
    }

    private List<KeyValueVo<Integer, Integer>> closeToAverage(double rate, List<KeyValueVo<Integer, Integer>> redCountVoList100) {
        int size = redCountVoList100.size();
        int coutSize = (int) (size * rate);
        redCountVoList100.sort(Comparator.comparing(KeyValueVo::getValue));

        List<KeyValueVo<Integer, Integer>> newList = Lists.newArrayList();
        for (int i = (size - coutSize) / 2; i < (size - ((size - coutSize) / 2)); i++) {
            newList.add(redCountVoList100.get(i));
        }
        return newList;
    }

    private void offsetCalculate(int[][] lotteryArray, int lastRange) {
//        System.out.println("------------------------------------------------------------------------------------------");
//        System.out.println(String.format("统计%d期次", lastRange));
//        System.out.println("------------------------------------------------------------------------------------------");

        Map<Integer, Integer> offsetMap = Maps.newLinkedHashMap();
        for (int lotteryArrayLength = lotteryArray.length, k = lotteryArrayLength - lastRange; k < lotteryArrayLength; k++) {
            int[] lottery = lotteryArray[k];
            for (int i = 1, lotteryLength = lottery.length - 1; i < lotteryLength; i++) {
                int l_c = lottery[i];
                int l_p = lottery[i - 1];
                int offset = l_c - l_p;

                Integer offsetCount = offsetMap.get(offset);
                if (Objects.isNull(offsetCount)) {
                    offsetCount = 1;
                } else {
                    offsetCount += 1;
                }
                offsetMap.put(offset, offsetCount);
            }
        }

        List<KeyValueVo<Integer, Integer>> ooffsetList = Lists.newArrayList();
        offsetMap.forEach((key, count) -> {
            KeyValueVo<Integer, Integer> vo = new KeyValueVo<>(key, count);
            ooffsetList.add(vo);
        });

//        System.out.println("差值统计：");
        ooffsetList.sort(Comparator.comparing(KeyValueVo::getKey));
        sortAndPrint(ooffsetList, false);
    }

    private void oddAndEvenCalculate(int[][] lotteryArray, int lastRange) {
//        System.out.println("------------------------------------------------------------------------------------------");
//        System.out.println(String.format("统计%d期次", lastRange));
//        System.out.println("------------------------------------------------------------------------------------------");

        Map<Integer, Integer> oddNumMap = Maps.newLinkedHashMap();
        Map<Integer, Integer> evenNumMap = Maps.newLinkedHashMap();
        for (int lotteryArrayLength = lotteryArray.length, k = lotteryArrayLength - lastRange; k < lotteryArrayLength; k++) {
            int[] lottery = lotteryArray[k];
            int oddNum = 0;
            int evenNum = 0;
            for (int l : lottery) {
                if (l % 2 == 0) {
                    evenNum += 1;
                } else {
                    oddNum += 1;
                }
            }
            Integer oddNumCount = oddNumMap.get(oddNum);
            Integer evenNumCount = evenNumMap.get(evenNum);
            if (Objects.isNull(oddNumCount)) {
                oddNumCount = 1;
            } else {
                oddNumCount += 1;
            }
            if (Objects.isNull(evenNumCount)) {
                evenNumCount = 1;
            } else {
                evenNumCount += 1;
            }
            oddNumMap.put(oddNum, oddNumCount);
            evenNumMap.put(evenNum, evenNumCount);
        }
        List<KeyValueVo<Integer, Integer>> oddNumMapList = Lists.newArrayList();
        oddNumMap.forEach((key, count) -> {
            KeyValueVo<Integer, Integer> vo = new KeyValueVo<>(key, count);
            oddNumMapList.add(vo);
        });
        List<KeyValueVo<Integer, Integer>> evenNumMapList = Lists.newArrayList();
        evenNumMap.forEach((key, count) -> {
            KeyValueVo<Integer, Integer> vo = new KeyValueVo<>(key, count);
            evenNumMapList.add(vo);
        });
//        System.out.println("奇数出现的次数：");
        oddNumMapList.sort(Comparator.comparing(KeyValueVo::getKey));
        sortAndPrint(oddNumMapList, false);
//        System.out.println("偶数出现的次数：");
        oddNumMapList.sort(Comparator.comparing(KeyValueVo::getKey));
        sortAndPrint(evenNumMapList, false);
    }

    /**
     * 各个红号出现次数统计
     */
    private List<KeyValueVo<Integer, Integer>> printAndGetRedCountMsg(int[][] lotteryArray, int lastRange) {
//        System.out.println("------------------------------------------------------------------------------------------");
//        System.out.println(String.format("统计%d期次", lastRange));
//        double avgCount = 6.0 * lastRange / 33.0;
//        System.out.println(String.format("每个红色号码平均出现次数：%s", avgCount));
//        System.out.println("------------------------------------------------------------------------------------------");

        Map<Integer, Integer> countRedMap = Maps.newLinkedHashMap();
        for (int i = 1; i < 34; i++) {
            countRedMap.put(i, 0);
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
//        System.out.println("==========================================================================================");
        return countBlueVoList;
    }

    /**
     * 各个蓝号出现次数统计
     */
    private List<KeyValueVo<Integer, Integer>> printAndGetBlueCountMsg(int[][] lotteryArray, int lastRange) {
//        System.out.println("------------------------------------------------------------------------------------------");
//        System.out.println(String.format("统计%d期次", lastRange));
//        System.out.println(String.format("每个蓝色号码平均出现次数：%s", lastRange / 16.0));
//        System.out.println("------------------------------------------------------------------------------------------");
        /* 出现次数*/
        Map<Integer, Integer> countBlueMap = Maps.newLinkedHashMap();
        for (int i = 1; i < 17; i++) {
            countBlueMap.put(i, 0);
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
//            System.out.print(String.format("%02d(%03d)\t", key, count));
            for (int i = 0; i < (count - minCount.get()); i++) {
//                System.out.print(" ");
            }
//            System.out.print("|");
//            System.out.println();
            KeyValueVo<Integer, Integer> vo = new KeyValueVo<>(key, count);
            countBlueVoList.add(vo);
        });
//        System.out.println("==========================================================================================");
        return countBlueVoList;
    }

    /**
     * 排序并打印
     */
    private void sortAndPrint(List<KeyValueVo<Integer, Integer>> voList, Boolean needValueSort) {
        if (needValueSort) {
            voList.sort(Comparator.comparing(KeyValueVo::getValue));
        }
        Optional<KeyValueVo<Integer, Integer>> minVo = voList.stream().min(Comparator.comparing(KeyValueVo::getValue));
        voList.forEach(item -> {
//            System.out.print(String.format("%02d(%03d)\t", item.getKey(), item.getValue()));
            for (int i = 0; i < (item.getValue() - minVo.get().getValue()); i++) {
//                System.out.print(" ");
            }
//            System.out.print("|");
//            System.out.println();

        });
//        System.out.println("==========================================================================================");
    }

    /**
     * 打印各个区间都出现的 n个数字
     */
    private void printMinAppearCount(int n, List<KeyValueVo<Integer, Integer>>... voListCollection) {
        List<Integer> resultList;
        int limit = n;
        do {
            resultList = voListCollection[0].stream().limit(limit).map(KeyValueVo::getKey).collect(Collectors.toList());
            for (int i = 1, voListCollectionLength = voListCollection.length; i < voListCollectionLength; i++) {
                List<KeyValueVo<Integer, Integer>> voList = voListCollection[i];
                voList.sort(Comparator.comparing(KeyValueVo::getValue));
                resultList.retainAll(voList.stream().limit(limit).map(KeyValueVo::getKey).collect(Collectors.toList()));
            }
            limit += 1;
        } while (resultList.size() < n);

//        System.out.println(String.format("前%d条记录中找出共同的%d个数字", limit, n));
//        System.out.println(JSON.toJSON(resultList.stream().sorted().collect(Collectors.toList())));
//        System.out.println("==========================================================================================");
    }
}
