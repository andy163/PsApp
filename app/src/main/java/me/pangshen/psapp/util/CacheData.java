package me.pangshen.psapp.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ps_an on 2016/5/5.
 */
public class CacheData {
    private final String TAG = getClass().getSimpleName();

    private static final String PREFS_NAME = "OO_FILE";
    private static final String REGION_ID = "region2";
    private static final String USER_NAME = "USER_NAME";// user_name就是电话号码
    private static final String USER_TOKEN = "USER_TOKEN";

    private String regionId = "-1";
    private String userId;

    private static CacheData cacheData;
    private static Application application;

    private volatile Map<String, String> stscMap = new ConcurrentHashMap();

    //当前页面的key
    private String currentKey;

    //当前页面对应内容类型
    private String content_type;

    //当前页面的内容ID
    private String content_id;

    //记录购物车操作的入口标识
    private String currentEntryCartType;

    public Map<String, String> getStscMap() {
        return stscMap;
    }

    public void setStscMap(Map<String, String> stscMap) {
        this.stscMap = stscMap;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
    }

    public static CacheData getInstance() {
        if (cacheData == null) {
            synchronized (CacheData.class) {
                if (cacheData == null) {
                    cacheData = new CacheData();
                }
            }
        }
        return cacheData;
    }

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        CacheData.application = application;
    }

    public String getRegionId() {
        if (application!=null){
            SharedPreferences settings = application.getSharedPreferences(PREFS_NAME, 0);
            String regionId = settings.getString(REGION_ID, "1");
            return regionId;
        }
        return "";
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getUserId() {
        if (application!=null){
            return application.getSharedPreferences(PREFS_NAME, 0).getString(USER_NAME, "");
        }
        return "";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getCurrentEntryCartType() {
        return currentEntryCartType;
    }

    public void setCurrentEntryCartType(String currentEntryCartType) {
        this.currentEntryCartType = currentEntryCartType;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getUserToken());
    }

    public String getUserToken() {
        if (application != null) {
            SharedPreferences settings = application.getSharedPreferences(PREFS_NAME, 0);
            return settings.getString(USER_TOKEN, "");
        }
        return "";
    }
}
