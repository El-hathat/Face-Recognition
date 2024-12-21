package com.presentation.admin.users;

public class SharedData {

    public static long userId = 0;

    public static void setUserId(long id) {
        userId = id;
    }

    public static long getUserId() {
        return userId;
    }
}
