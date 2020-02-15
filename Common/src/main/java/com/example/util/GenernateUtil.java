package com.example.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class GenernateUtil {
    public static long genernateDate(Date date){
        Random random = new Random();
        int[] days  = new int[]{3,4,5,6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,-days[random.nextInt(days.length)]);
        long resultTime = calendar.getTime().getTime();
        return resultTime;
    }
}
