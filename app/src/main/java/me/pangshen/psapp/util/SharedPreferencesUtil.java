package me.pangshen.psapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ps_an on 2016/11/1.
 */

public class SharedPreferencesUtil {
    public final static String SETTING = "Setting";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Context context) {
        sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context){
        return new SharedPreferencesUtil(context);
    }

    public SharedPreferences getSp() {
        return sp;
    }

    public void setSp(SharedPreferences sp) {
        this.sp = sp;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
}
