package com.gxh.sell.utils;


import java.util.Random;

public class KeyUtils {
    /**
     * 生成唯一的主键
     * 格式:时间+随机数
     */
    public static synchronized String getUniqueKey(){

        Random random = new Random();
        System.currentTimeMillis();
        Integer number = random.nextInt(900000) + 1000;

        return System.currentTimeMillis()+String.valueOf(number);
    }
}
