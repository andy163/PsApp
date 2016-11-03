package me.pangshen.psapp.util;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.MessageFormat;

import me.pangshen.psapp.model.WeixinHot;

/**
 * Created by ps_an on 2016/11/3.
 */

public class Util {
    private static  String TAG = "Util";
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            // logger.debug("fromJson:{0} of clazz {1}", clazz, json);
//            return JSONObject.parseObject(json, clazz);
			return new Gson().fromJson(json, clazz);
        } catch (Exception ex) {
            Log.e(TAG,"Error:" + json, ex);
            return null;
        }
    }

    public static <T> T fromJson(String json, Type type) {
        try {

            return new Gson().fromJson(json,type);
        } catch (Exception ex) {
            Log.e(TAG,"Error:" + json, ex);
            return null;
        }
    }

    public static String toJson(Object o) {
        try {
            return new Gson().toJson(o);
        } catch (Exception ex) {
            Log.e(TAG,"Error:" + o, ex);
            return null;
        }
    }

    public static String messageFormat(String var1, Object... var2) {
        if (var2 == null || var2.length == 0) {
            return var1;
        }
        Object[] var3 = new Object[var2.length];
        for (int i = 0; i < var2.length; i++) {
            if (var2[i] == null) {
                var3[i] = "";
                continue;
            }
            var3[i] = var2[i] instanceof String ? var2[i] : String.valueOf(var2[i]);
        }
        return MessageFormat.format(var1, var3);
    }

}
