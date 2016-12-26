package me.pangshen.psapp.weixinredbag;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LuckMoneyNotificationService extends NotificationListenerService {
    private String TAG=LuckMoneyNotificationService.class.getSimpleName()+"_LuckyMoney";

    @SuppressWarnings("NewApi")
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG,"onNotificationPosted");

        Notification notification = sbn.getNotification();
        if (null != notification) {
            Bundle extras = notification.extras;
            if (null != extras) {
                List<String> textList = new ArrayList<String>();
                String title = extras.getString("android.title");
                Log.i(TAG,"notification title:"+title);
                if (!TextUtils.isEmpty(title)) {
                    textList.add(title);
                }

                String detailText = extras.getString("android.text");
                Log.i(TAG,"notification detailText:"+detailText);
                if (!TextUtils.isEmpty(detailText)) {
                    textList.add(detailText);
                }


                if (textList.size() > 0) {
                    for (String text : textList) {
                        Log.i(TAG,"notification text:"+text);
                        if (!TextUtils.isEmpty(text) && text.contains("[微信红包]")) {
                            final PendingIntent pendingIntent = notification.contentIntent;
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("NewApi")
    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
//        super.onNotificationPosted(sbn, rankingMap);
        Log.i(TAG,"onNotificationPosted");

        Notification notification = sbn.getNotification();
        if (null != notification) {
            Bundle extras = notification.extras;
            if (null != extras) {
                List<String> textList = new ArrayList<String>();
                String title = extras.getString("android.title");
                Log.i(TAG,"notification title:"+title);
                if (!TextUtils.isEmpty(title)) {
                    textList.add(title);
                }

                String detailText = extras.getString("android.text");
                Log.i(TAG,"notification detailText:"+detailText);
                if (!TextUtils.isEmpty(detailText)) {
                    textList.add(detailText);
                }


                if (textList.size() > 0) {
                    for (String text : textList) {
                        Log.i(TAG,"notification text:"+text);
                        if (!TextUtils.isEmpty(text) && text.contains("[微信红包]")) {
                            final PendingIntent pendingIntent = notification.contentIntent;
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
