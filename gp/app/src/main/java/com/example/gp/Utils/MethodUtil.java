package com.example.gp.Utils;

import android.nfc.Tag;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Objects;

public class MethodUtil {
    private static final String TAG = "MethodUtil";

    private static Method getMethod(Object obj, String methodName) {
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            Log.d("MethodUtil", "method name: " + method.getName());
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static Method getMethod(Object obj, String methodName, Object... args) throws NoSuchMethodException {
        // Get args type
        Class[] argsType = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsType[i] = args[i].getClass();
        }

        // When there are args left out for further filling in
        try {
            // Get the method if args is all the method's need
            return obj.getClass().getMethod(methodName, argsType);
        } catch (Exception e) {
            // When there are args left out for further filling in
            if (e.getClass().getName().equals("java.lang.NoSuchMethodException")) {
                // try use less efficient way to get method
                return getMethod(obj, methodName);
            }
        }

        throw new NoSuchMethodException();
    }

    public static void invokeMethod(Object obj, String methodName, Object... args) {
        if (obj == null) {
            return;
        }
        try {
            Objects.requireNonNull(getMethod(obj, methodName)).invoke(obj, args);
        } catch (Exception e) {
            Log.e(TAG, "invoke method error: " + e.getMessage());
        }
    }
}
