package me.pangshen.psapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import me.pangshen.psapp.util.DeviceUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        String className = DeviceUtil.getClassName(this);
        MobclickAgent.onPageStart(className);
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        String className = DeviceUtil.getClassName(this);
        MobclickAgent.onPageEnd(className);
        MobclickAgent.onPause(this);
    }
}
