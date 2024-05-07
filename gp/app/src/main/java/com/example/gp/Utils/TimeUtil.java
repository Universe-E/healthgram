package com.example.gp.Utils;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Date getCurDate() {
        return Calendar.getInstance().getTime();
    }

    public static Timestamp getTimestamp() {
        return new Timestamp(Calendar.getInstance().getTime());
    }
}
