package com.fh.shop.api.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String Y_M_D = "yyyy-MM-dd";

    public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static String date2string(Date date,String pattern){
        if(date == null){
            return "";
        }

        SimpleDateFormat sim = new SimpleDateFormat(pattern);
        return sim.format(date);
    }

    public static Date string2date(String date,String pattern){
        if(StringUtils.isEmpty(date)){
            return null;
        }

        Date date2 = null;
        try {
            SimpleDateFormat sim = new SimpleDateFormat(pattern);
            date2 = sim.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date2;
    }
}
