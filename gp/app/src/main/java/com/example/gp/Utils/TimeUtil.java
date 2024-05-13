package com.example.gp.Utils;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Timestamp getTimestamp() {
        return Timestamp.now();
    }

    public static Timestamp getTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            timestamp = Timestamp.now();
        }
        return timestamp;
    }
}
