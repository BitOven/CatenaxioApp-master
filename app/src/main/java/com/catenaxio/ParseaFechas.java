package com.catenaxio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Antonio on 12/11/2015.
 */
public class ParseaFechas {

    public static String parsearDateToString(Date fecha){
        SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
        return postFormater.format(fecha);
    }

    public static String parsearDateToStringUnix(Date fecha){
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
        return postFormater.format(fecha)+"T00:00:00";
    }

    public static String parsearStringToStringUnix(String fechaStr){
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = null;
        try {
            dateObj = dateFormater.parse(fechaStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
        return postFormater.format(dateObj)+"T00:00:00";
    }

    public static Date parsearStringToDate(String dateStr){
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = null;
        try {
            dateObj = dateFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObj;
    }

    public static String parsearStringSemiUnixToString(String semiUnix){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = dateFormater.parse(semiUnix);
            SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
            return postFormater.format(dateObj);

        } catch (ParseException e) {
            e.printStackTrace();
            return "01/01/1900";
        }
    }

    public static String getFechaHoyString(){
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
        return postFormater.format(calendar.getTime());
    }

    public static String getFechaHoyUnixString(){
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
        return postFormater.format(calendar.getTime())+"T00:00:00";
    }

    public static String parsearLongToString(long fechaMod){
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(fechaMod);

        SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
        return postFormater.format(calendar.getTime());
    }
}
