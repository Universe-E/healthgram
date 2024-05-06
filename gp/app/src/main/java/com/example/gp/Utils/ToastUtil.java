package com.example.gp.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * It is a util class for toasting information on the screen
 * eg. ToastUtil.show(context, "message")
 * @author : Yulong Chen
 * @since : 2024-04-30
 */
public class ToastUtil {

    public static void show(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }
    public static void showLong(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }
    public static void showToast(Context context, String msg, int length){
        Toast.makeText(context, msg, length).show();
    }
}
