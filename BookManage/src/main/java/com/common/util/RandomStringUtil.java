package com.common.util;

import java.util.Random;
/**
 * 生成随机字符串
 * */
public class RandomStringUtil {
    static char[] strs = {
            'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y',
            'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8',
            '9',};

    public static String genRandomNum(int num){
        int  maxNum = 36;
        int i;
        int count = 0;
        StringBuilder str = new StringBuilder(num);
        Random r = new Random();
        while(count < num){
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < strs.length) {
                str.append(strs[i]);
                count ++;
            }
        }
        return str.toString();
    }
}
