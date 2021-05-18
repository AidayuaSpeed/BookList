package com.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
    * 格式化日期
    */
    public static String parseDateToStr(Date date, String pattern) {
        DateFormat dateFormat=new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
    /**
     * 增加天
     * */
    public static Date addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,day);
        return calendar.getTime();
    }
    
}
