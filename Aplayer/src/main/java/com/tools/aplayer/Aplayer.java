package com.tools.aplayer;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * Aplayer播放器
 */
public class Aplayer {
    private String cookie = "";

    private final Context context;

    static Handler handler = null;

    private String loadingText = "正在打开视频,请稍候...";
    private AplayerConfig aplayerConfig;

    public Aplayer(String cookie,Context context, String loadingText) {
        this.cookie = cookie;
        this.loadingText = loadingText;
        this.context = context;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Aplayer.this.playFinish(msg.what);
                return false;
            }
        });

    }

    /**
     * 开始播放
     * @param title
     * @param url
     */
    public void start(String title, String url) {
        AplayerActivity.fanhui = handler;

        if (this.cookie != null && context!=null) {
            AplayerConfig aplayerConfig = new AplayerConfig(context,cookie,loadingText,title,url);
            aplayerConfig.start();
            //new AplayerConfig.Builder(title, url).init(context).setLoadingText(this.loadingText).setCookie(this.cookie).build().start();
        }
    }

    public void setCookie(String str) {
        this.cookie = str;
    }

    /**
     * 播放完成
     * @param i
     */
    public void playFinish(int i) {

    }

    /**
     * 设置加载字体
     * @param str
     */
    public void setLoading(String str) {
        this.loadingText = str;
    }
}
