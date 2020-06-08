package com.example.applicationloginusephp;

import android.app.Application;

public class Globalv extends Application {
    private String user_name ;
    private int total_thread ;



    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getTotal_thread() {
        return total_thread;
    }

    public void setTotal_thread(int total_thread) {
        this.total_thread = total_thread;
    }
}
