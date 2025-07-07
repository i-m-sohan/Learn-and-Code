package com.intimetec.newsportal.console.app.session;

public class UserSession {
    private static Long userId;

    public static void setUserId(Long id) {
        userId = id;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void clear() {
        userId = null;
    }

    public static boolean isSessionRunning(){
        if(userId==null){
            return false;
        }
        return true;
    }
}
