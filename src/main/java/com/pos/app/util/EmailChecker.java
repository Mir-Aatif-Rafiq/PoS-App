package com.pos.app.util;

public class EmailChecker {
    public static boolean checkAdminEmail(String email){
        return (email.contains("@increff.com"));
    }
}
