package com.example.gp.Utils;

import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class MethodUtil {
    public static Method getMethod(Object obj, String methodName) throws NoSuchMethodException {
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            Log.d("MethodUtil", "method name: " + method.getName());
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
