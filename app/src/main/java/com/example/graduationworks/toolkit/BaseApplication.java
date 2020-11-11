package com.example.graduationworks.toolkit;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //
        Bmob.initialize(this, "007add6a8dff0fdf5ba0dab707001801");
    }
}
