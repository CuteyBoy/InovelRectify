package com.tv.inovelrectify;

import android.app.Application;

import com.tv.framework.AppHelper;

/**
 * 功能描述：矫正app
 * 开发状况：正在开发中
 * 开发作者：黎丝军
 * 开发时间：2017/5/8- 17:23
 */

public class RectifyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppHelper.instance().initCoreApp(this);
    }
}
