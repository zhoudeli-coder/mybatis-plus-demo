package com.zdl.lottery.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyValueVo<K, V> {

    private K key;

    private V value;
}
