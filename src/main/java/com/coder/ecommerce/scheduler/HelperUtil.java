package com.coder.ecommerce.scheduler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperUtil {

    static String findDifference(String start_date, String end_date) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);
            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;

            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

            long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;


            return  difference_In_Years+"," + difference_In_Days + "," + difference_In_Hours + "," + difference_In_Minutes + "," + difference_In_Seconds;
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
