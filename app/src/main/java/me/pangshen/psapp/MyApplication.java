package me.pangshen.psapp;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/1/14.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        int FADE_DURATION = 800;
        String storagePath = context.getPackageName()+"/cache/image";
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, storagePath);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config .threadPoolSize(3);
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
}
