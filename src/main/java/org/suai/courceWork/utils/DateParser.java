package org.suai.courceWork.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class DateParser {

    public static Date getDateWithTime(String dateString){

        StringTokenizer st = new StringTokenizer(dateString, "T");
        if(st.countTokens() != 2)
            return null;
        String dateStr = st.nextToken();
        String timeString = st.nextToken();


        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(dateStr + " " + timeString);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public static Date getDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }

        return date;
    }
}
