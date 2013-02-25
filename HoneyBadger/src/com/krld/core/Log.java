package com.krld.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
    public Log() {
    }

    public static void i(String message) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime()) + " " + message);
    }
}