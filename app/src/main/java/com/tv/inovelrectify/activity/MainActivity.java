package com.tv.inovelrectify.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;

import com.droidlogic.app.tv.TvControlManager;
import com.tv.framework.AppHelper;
import com.tv.framework.activity.AbsBaseActivity;
import com.tv.framework.utils.ToastUtil;
import com.tv.inovelrectify.R;
import com.tv.inovelrectify.widget.TvRectifyView;

public class MainActivity extends AbsBaseActivity {

    //保存退出间隔时间
    private long mExitTime = 0;
    //tv矫正视图
    private TvRectifyView mRectifyView;
    //tv控制器
    private TvControlManager mControlMgr;

    @Override
    public void onCreateView() {
        setContentView(R.layout.activity_main);
        mRectifyView = (TvRectifyView) findViewById(R.id.rv_rectify);
    }

    @Override
    public void onInitObjects() {
        mControlMgr = TvControlManager.getInstance();
    }

    @Override
    public void onSetListeners() {
        mRectifyView.setRectifyCallback(new TvRectifyView.IRectifyCallback() {

            @Override
            public void onLeftChange(TvRectifyView.Direction dir) {
                mControlMgr.ExecuteTuneXRDirect16Pixel();
            }

            @Override
            public void onRightChange(TvRectifyView.Direction dir) {
                mControlMgr.ExecuteTuneXLDirect16Pixel();
            }

            @Override
            public void onUpChange(TvRectifyView.Direction dir) {
                mControlMgr.ExecuteTuneYDDirect16Pixel();
            }

            @Override
            public void onDownChange(TvRectifyView.Direction dir) {
                mControlMgr.ExecuteTuneYUDirect16Pixel();
            }

            @Override
            public void onDirChange(TvRectifyView.DirType dirType) {
                mControlMgr.SelectPositionArround();
            }
        });
    }

    @Override
    public void onInitData(Bundle bundle) {
        mControlMgr.Set4CornerMenuInit();
        registerHomeReceiver();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - mExitTime < 2000) {
                exitApplication();
            } else {
                mExitTime = currentTime;
                ToastUtil.toast(this,"真的要退出嘛？");
            }
            return true;
        } else {
            mRectifyView.onKeyDown(keyCode,event);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用程序
     */
    private void exitApplication() {
        if(mControlMgr != null) {
            mControlMgr.Exit4CornerMenu();
        }
        mControlMgr = null;
        unregisterReceiver(mHomeReceiver);
        AppHelper.instance().exitApp();

    }

    /**
     * 注册监听Home的广播
     */
    private void registerHomeReceiver() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeReceiver, intentFilter);
    }

    /**
     * Home键接收器
     */
    private final BroadcastReceiver mHomeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                String reason = intent.getStringExtra("reason");
                if(reason != null) {
                    if (reason.equals("homekey")) {
                        exitApplication();
                    }
                }
            }
        }
    };
}
