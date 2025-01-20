package com.presentation.outils;

import java.util.HashMap;
import java.util.Map;

public class SharedData {

    private static Map<String, Object> data;

    static {
        data = new HashMap<>();
    }

    public static void putObject(String key, Object value) {
        data.put(key, value);
    }

    public static Object getObject(String key) {
        return data.get(key);
    }

    public static void put(String key, String value) {
        data.put(key, value);
    }

    public static String get(String key) {
        return (String) data.get(key);
    }

    public static String getAndRemove(String key) {
        return (String) data.remove(key);
    }

    public static void putLong(String key, long value) {
        data.put(key, value);
    }

    public static long getLong(String key) {
        return (Long) data.get(key);
    }

    public static long getLongAndRemove(String key) {
        return (Long) data.remove(key);
    }
}
