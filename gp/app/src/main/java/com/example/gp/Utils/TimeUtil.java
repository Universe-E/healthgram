package com.example.gp.Utils;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Timestamp getTimestamp() {
        return new Timestamp(Calendar.getInstance().getTime());
    }

    public static Timestamp getTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return getTimestamp();
        }
        return timestamp;
    }
}
