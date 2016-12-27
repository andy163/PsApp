package me.pangshen.psapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by ps_an on 2016/5/6.
 */
public class DeviceUtil {

    private static String deviceId;
    private static String deviceType;
    private static String osType;
    private static String reportType;
    private static String osVersion;
    private static String versionName;
    private static String packageName;
    private static String className;

    public static String getDeviceId() {
        if (TextUtils.isEmpty(deviceId)
                && CacheData.getApplication()!=null
                && CacheData.getApplication().getBaseContext()!=null){
            deviceId = DeviceUuid.getInstance(CacheData.getApplication().getBaseContext())
                    .getDeviceUuid().toString();
            deviceId = deviceId.replace("-","");
        }
        return deviceId;
    }

    public static String getDeviceType() {
        if (TextUtils.isEmpty(deviceType)){
            deviceType =android.os.Build.MODEL+"("+android.os.Build.MANUFACTURER+")";
        }
        return deviceType;
    }

    public static String getOsType() {
        if (TextUtils.isEmpty(osType)){
            osType = Constant.OS_TYPE;
        }
        return osType;
    }

    public static String getReportType() {
        if (TextUtils.isEmpty(reportType)){
            reportType = Constant.REPORT_TYPE;
        }
        return reportType;
    }

    public static String getOsVersion() {
        if (TextUtils.isEmpty(osVersion)){
            osVersion = android.os.Build.VERSION.RELEASE
                    + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
        }
        return osVersion;
    }

    public static String getVersionName() {
        if (TextUtils.isEmpty(versionName)){
            try {
                versionName = CacheData.getApplication().
                        getPackageManager().getPackageInfo(CacheData.getApplication().
                        getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return versionName;
    }

    public static String getPackageName(Context context) {
        if (TextUtils.isEmpty(packageName)){
            packageName = context.getPackageName();
        }
        return packageName;
    }

    public static String getClassName(Context context) {
        try {
            if (context==null){
                return "";
            }
            ActivityInfo info = context.getPackageManager().getActivityInfo(
                    ((Activity)context).getComponentName(), 0);
            className = info.name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return className;
    }
}
