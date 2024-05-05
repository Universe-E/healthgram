package android.util;

public class Log {

    public static int d(String tag, String msg) {
        System.out.println("DEBUG: " + tag + ": " + msg);
        return 0;
    }

    public static int i(String tag, String msg) {
        System.out.println("INFO: " + tag + ": " + msg);
        return 0;
    }

    public static int w(String tag, String msg) {
        System.out.println("WARN: " + tag + ": " + msg);
        return 0;
    }

    public static int e(String tag, String msg) {
        System.out.println("ERROR: " + tag + ": " + msg);
        return 0;
    }

    public static int d(String tag, Object... args) {
        String msg = argsToString(args);
        System.out.println("DEBUG: " + tag + ": " + msg);
        return 0;
    }

    public static int i(String tag, Object... args) {
        String msg = argsToString(args);
        System.out.println("INFO: " + tag + ": " + msg);
        return 0;
    }

    public static int w(String tag, Object... args) {
        String msg = argsToString(args);
        System.out.println("WARN: " + tag + ": " + msg);
        return 0;
    }

    public static int e(String tag, Object... args) {
        String msg = argsToString(args);
        System.out.println("ERROR: " + tag + ": " + msg);
        return 0;
    }

    private static String argsToString(Object... args) {
        StringBuilder msg = new StringBuilder();
        for (Object arg : args) {
            if (arg instanceof String) {
                msg.append(" ").append(arg);
            } else {
                msg.append(" ").append(arg.toString());
            }
        }

        return msg.toString();
    }

    // add other methods if required...
}
