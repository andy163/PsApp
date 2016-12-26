package me.pangshen.psapp.weixinredbag;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.pangshen.psapp.R;

public class RedBagActivity extends AppCompatActivity {

    private String TAG = "RedBagActivity";

    private static final Intent sSettingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    private TextView mAccessibleLabel;
    private TextView mNotificationLabel;
    private TextView mLabelText;
    private Button mAccessibilityBtn;
    private Button mNotificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_bag);

        ImageView accessibilityIv = (ImageView) findViewById(R.id.image_accessibility);
        ImageView notificationIv = (ImageView) findViewById(R.id.image_notification);

        mAccessibleLabel = (TextView) findViewById(R.id.label_accessible);
        mNotificationLabel = (TextView) findViewById(R.id.label_notification);
        mLabelText = (TextView) findViewById(R.id.label_text);
        mAccessibilityBtn = (Button) findViewById(R.id.button_accessibility);
        mNotificationBtn = (Button) findViewById(R.id.button_notification);


        if (Build.VERSION.SDK_INT >= 18) {
            notificationIv.setVisibility(View.VISIBLE);
            mNotificationLabel.setVisibility(View.VISIBLE);
            mNotificationBtn.setVisibility(View.VISIBLE);
        } else {
            notificationIv.setVisibility(View.GONE);
            mNotificationLabel.setVisibility(View.GONE);
            mNotificationBtn.setVisibility(View.GONE);
        }
    }

    /**
     * get version name
     *
     * @return
     */
    private String getVersionName() {
        String versionName = "";

        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume");
        changeLabelStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume");
        changeLabelStatus();
    }

    /**
     *
     */
    private void changeLabelStatus() {
        boolean isAccessibilityEnabled = isAccessibleEnabled();

        mAccessibilityBtn.setText(isAccessibleEnabled() ? R.string.disable_accessibility : R.string.enable_accessibility);
        mAccessibilityBtn.setTextColor(isAccessibilityEnabled ? 0xFFFFFFFF : Color.RED);


        if (Build.VERSION.SDK_INT >= 18) {
            boolean isNotificationEnabled = isNotificationEnabled();
            mNotificationBtn.setTextColor(isNotificationEnabled ? 0xFFFFFFFF : Color.RED);
            mNotificationBtn.setText(isNotificationEnabled ? R.string.disable_notification : R.string.enable_notification);

            if (isAccessibilityEnabled && isNotificationEnabled) {
                Toast.makeText(this, R.string.enable_grab_luckymoney, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.disable_grab_luckymoney, Toast.LENGTH_LONG).show();
            }
        } else {
            if (isAccessibilityEnabled) {
                Toast.makeText(this, R.string.enable_grab_luckymoney, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.disable_grab_luckymoney, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Go to notification settings
     *
     * @param view
     */
    public void onNotificationEnableButtonClicked(View view) {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    /**
     * Go to Accessbility settings
     *
     * @param view
     */
    public void onSettingsClicked(View view) {
        startActivity(sSettingsIntent);
    }

    private boolean isAccessibleEnabled() {
        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = manager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo info : runningServices) {
            Log.d(TAG,"info.getId()="+info.getId());
            if (info.getId().equals(getPackageName() + "/.weixinredbag.AutoGrabMoneyMonitorServices")) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotificationEnabled() {
        ContentResolver contentResolver = getContentResolver();
        String enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");

        if (!TextUtils.isEmpty(enabledListeners)) {
            Log.d(TAG,"enabledListeners="+enabledListeners);
            return enabledListeners.contains(getPackageName() + "/" + getPackageName() + ".weixinredbag.LuckMoneyNotificationService");
        } else {
            return false;
        }
    }

}
