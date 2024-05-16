package com.example.gp.Utils;

import com.google.firebase.Timestamp;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Util class to get time stamp
 * @author Xingchen Zhang
 */
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
    public static String convertTimestampToString(Timestamp timestamp) {
        // 从 Timestamp 对象获取秒和纳秒
        long seconds = timestamp.getSeconds();
        int nanoseconds = timestamp.getNanoseconds();

        // 将时间戳转换为 Instant 对象
        Instant instant = Instant.ofEpochSecond(seconds, nanoseconds);

        // 将 Instant 对象转换为 ZonedDateTime 对象
        ZonedDateTime dateTime = instant.atZone(ZoneId.of("UTC"));

        // 格式化输出
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return dateTime.format(formatter);
    }


}
