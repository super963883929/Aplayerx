package com.tools.aplayer;


import android.content.Context;
import android.content.Intent;

public class AplayerConfig {
    public AplayerConfig(Context context, String cookie, String loadingText, String title, String url) {

        this.context = context;
        this.cookie = cookie;
        this.loadingText = loadingText;
        this.title = title;
        this.url = url;
    }


    private Context context;
    private  String cookie;
    private  String loadingText;
    private  String title;
    private  String url;

    public AplayerConfig() {
    }

    public static class Builder {
        private Context context;
        private String cookie;
        private String loadingText = "正在打开视频,请稍候...";
        private final String title;
        private final String url;

        public Builder(String str, String str2) {
            this.title = str;
            this.url = str2;
        }

        public Builder init(Context context) {
            this.context = context;
            return this;
        }

        public Builder setCookie(String str) {
            this.cookie = str;
            return this;
        }

        public Builder setLoadingText(String str) {
            this.loadingText = str;
            return this;
        }

        public AplayerConfig build() {
            return new AplayerConfig();
        }
    }

    private AplayerConfig(Builder builder) {
        this.title = builder.title;
        this.url = builder.url;
        this.context = builder.context;
        this.cookie = builder.cookie;
        this.loadingText = builder.loadingText;
    }

    public void start() {
        Intent intent = new Intent(this.context, AplayerActivity.class);
        intent.putExtra("title", this.title);
        intent.putExtra("url", this.url);
        intent.putExtra("loadingText", this.loadingText);
        if (!this.cookie.equals("")) {
            intent.putExtra("cookie", this.cookie);
        }
        this.context.startActivity(intent);
    }
}