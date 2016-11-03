package me.pangshen.psapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.Log;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/1/14.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        ApiStoreSDK.init(this, "cb206355b02faf46437e95686de39902");
        super.onCreate();
        Configuration config = getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        //这个获取出来的值，我们需要建立对应的values-sw{smallestWidth}dp文件夹进行适配
        Log.d(TAG,"smallest width : " + smallestScreenWidth);
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        int FADE_DURATION = 800;
        String storagePath = context.getPackageName() + "/cache/image";
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, storagePath);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.threadPoolSize(3);
        config.memoryCacheExtraOptions(480, FADE_DURATION);
        config.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024));
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        config.diskCache(new UnlimitedDiskCache(cacheDir));// 自定义缓存路径
        // Initialize ImageLoader with configuration.
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000));
        ImageLoader.getInstance().init(config.build());
    }

    public static DisplayImageOptions getDisplayImageOptions(boolean isCircle, int round) {
        if (isCircle) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.icon_default) // 设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.icon_default)// 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.icon_default) // 设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(false)// 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
                    .displayer(new CircleBitmapDisplayer())// 是否设置为圆角，弧度为多少
                    .bitmapConfig(Bitmap.Config.RGB_565).build();// 构建完成
            return options;
        } else {
            DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.icon_default) // 设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.icon_default)// 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.icon_default) // 设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(false)// 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
                    .displayer(new RoundedBitmapDisplayer(round))// 是否设置为圆角，弧度为多少
                    .bitmapConfig(Bitmap.Config.RGB_565).build();// 构建完成
            return options;
        }
    }
}
