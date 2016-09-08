package com.example.project;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by TangRen on 2016/7/22.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
