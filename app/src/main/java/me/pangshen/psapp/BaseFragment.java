package me.pangshen.psapp;


import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

import me.pangshen.psapp.util.DeviceUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public void onResume() {
        super.onResume();
        String className = DeviceUtil.getClassName(getActivity());
        MobclickAgent.onPageStart(className); //统计页面，"MainScreen"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        String className = DeviceUtil.getClassName(getActivity());
        MobclickAgent.onPageEnd(className);
    }

}
