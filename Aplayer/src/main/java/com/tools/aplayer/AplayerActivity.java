package com.tools.aplayer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.aplayer.APlayerAndroid;
import com.aplayer.APlayerAndroid.OnBufferListener;
import com.aplayer.APlayerAndroid.OnOpenCompleteListener;
import com.aplayer.APlayerAndroid.OnPlayCompleteListener;
import com.aplayer.APlayerAndroid.OnPlayStateChangeListener;


import java.lang.ref.WeakReference;

public class AplayerActivity extends Activity implements OnClickListener {

    private static final String TAG = "AplayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplayer);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //纵向

        getWindow().addFlags(128);
        hideBottomUIMenu();
        initAplayer();
        this.v_title.setText(getIntent().getStringExtra("title"));
        this.url = getIntent().getStringExtra("url");
        String stringExtra = getIntent().getStringExtra("cookie");
        if (stringExtra != null) {
            this.aPlayer.setConfig(1105, stringExtra);
        }
        this.loadingText = getIntent().getStringExtra("loadingText");
        this.aPlayer.open(this.url);
        Log.e("info", "url:" + this.url);
        PlayerInfoUtil.openDataBase(this);
        //hideUi();


    }


    public static Handler fanhui = null;

    private APlayerAndroid aPlayer = null;
    private int anxiayinliang;
    private float anxiazuobiaox;
    private float anxiazuobiaoy;
    private AudioManager audioManager;
    private ProgressBar cachingProgressBar = null;
    private TextView cachingProgressHint = null;
    private LinearLayout ctrl_bar;
    private int currentVolume;
    private int dangqianliandu;
    private int dangqianliangdu;
    private int dangqianshichang;
    private int dangqianshituodong;
    private int dangqianyinliang;
    private int dongzuo;
    private LinearLayout header_bar;
    private int height;
    private SurfaceView holderView;
    private float huadongzongzuobiao;
    private String loadingText;
    private ImageView lock;
    private boolean mIsNeedUpdateUIProgress = false;
    private boolean mIsSystemCallPause = false;
    private boolean mIsTouchingSeekbar = false;
    private Thread mUpdateThread = null;
    private Handler mainUIHandler;
    private ImageView play;
    private ImageView play_large;
    private ImageView ratate;
    private ImageView img_more;
    private SeekBar seekBar;
    /**
     * 锁定
     */
    private boolean suoding = false;
    private TextView tv_duration;

    /**
     * 音量 和 亮度
     */
    private TextView tv_info;

    private TextView tv_position;
    private String url = "";
    private TextView v_title;
    private int width;
    private int zongshichang;
    private int zuidayinliang;

    static class MyHandler extends Handler {
        WeakReference<AplayerActivity> mActivity;

        MyHandler(AplayerActivity aplayerActivity) {
            this.mActivity = new WeakReference(aplayerActivity);
        }

        public void handleMessage(Message message) {
            AplayerActivity aplayerActivity = (AplayerActivity) this.mActivity.get();
            if (message.what == 1) {
                aplayerActivity.tv_duration.setText(AplayerActivity.updateTextViewWithTimeFormat(message.arg2));
                aplayerActivity.tv_position.setText(AplayerActivity.updateTextViewWithTimeFormat(message.arg1));
                if (message.arg1 <= 0 || message.arg2 < 0) {
                    aplayerActivity.seekBar.setProgress(0);
                    return;
                }
                aplayerActivity.seekBar.setMax(message.arg2);
                aplayerActivity.seekBar.setProgress(message.arg1);
            }
        }
    }

    /**
     * 更新进度
     */
    private class UpdatePlayUIProcess implements Runnable {
        private UpdatePlayUIProcess() {
        }

        public void run() {
            while (AplayerActivity.this.mIsNeedUpdateUIProgress) {
                if (!AplayerActivity.this.mIsTouchingSeekbar) {
                    int position;
                    int duration;
                    if (AplayerActivity.this.aPlayer != null) {
                        position = AplayerActivity.this.aPlayer.getPosition();
                        duration = AplayerActivity.this.aPlayer.getDuration();
                    } else {
                        duration = 0;
                        position = 0;
                    }
                    AplayerActivity.this.mainUIHandler.sendMessage(AplayerActivity.this.mainUIHandler.obtainMessage(1, position / 1000, duration / 1000));
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Log.e("info", e.toString());
                }
            }
        }
    }


    private void initAplayer() {

        if (this.aPlayer == null) {
            this.holderView = (SurfaceView) findViewById(R.id.holderView);
            this.header_bar = (LinearLayout) findViewById(R.id.header_bar);
            this.ctrl_bar = (LinearLayout) findViewById(R.id.ctrl_bar);
            this.play = (ImageView) findViewById(R.id.v_play);
            this.play_large = (ImageView) findViewById(R.id.v_play_large);
            this.play_large.setOnClickListener(this);
            this.play.setOnClickListener(this);
            findViewById(R.id.v_back).setOnClickListener(this);
            this.ratate = (ImageView) findViewById(R.id.v_rotate);
            this.ratate.setOnClickListener(this);
            this.lock = (ImageView) findViewById(R.id.v_player_lock);
            this.lock.setOnClickListener(this);
            this.cachingProgressBar = (ProgressBar) findViewById(R.id.loading);
            this.cachingProgressHint = (TextView) findViewById(R.id.loading_text);
            this.img_more = (ImageView)findViewById(R.id.img_more);
            img_more.setOnClickListener(this);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.width = displayMetrics.widthPixels;
            this.height = displayMetrics.heightPixels;
            this.audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (this.audioManager != null) {
                this.zuidayinliang = this.audioManager.getStreamMaxVolume(3);
                this.zuidayinliang *= 6;
            }
            float f = (float) (this.currentVolume * 6);
            try {
                f = (((float) System.getInt(getContentResolver(), "screen_brightness")) * 1.0f) / 255.0f;
            } catch (SettingNotFoundException e) {
                e.printStackTrace();
            }
            this.dangqianliangdu = (int) (f * 100.0f);
            this.dangqianliandu = (int) (f * 100.0f);
            this.seekBar = (SeekBar) findViewById(R.id.seekbar);
            this.tv_position = (TextView) findViewById(R.id.tv_position);
            this.tv_duration = (TextView) findViewById(R.id.tv_duration);
            this.tv_info = (TextView) findViewById(R.id.tv_info);
            this.v_title = (TextView) findViewById(R.id.v_title);
            this.aPlayer = new APlayerAndroid();
            this.aPlayer.setView(this.holderView);
            this.aPlayer.setOnOpenCompleteListener(new OnOpenCompleteListener() {
                public void onOpenComplete(boolean z) {
                    if (z) {
                        AplayerActivity.this.aPlayer.play();
                    } else {
                        AplayerActivity.this.findViewById(R.id.caching).setVisibility(View.GONE);
                    }
                }
            });
            this.aPlayer.setOnPlayCompleteListener(new OnPlayCompleteListener() {
                public void onPlayComplete(String str) {
                    Message message = new Message();
                    Log.e("info", "onPlayComplete:" + str);
                    int i = -1;
                    switch (str.hashCode()) {
                        case 49896:
                            if (str.equals("0x0")) {
                                i = 0;
                                break;
                            }
                            break;
                        case 49897:
                            if (str.equals("0x1")) {
                                i = 1;
                                break;
                            }
                            break;
                        case 1189723457:
                            if (str.equals("0x80000001")) {
                                i = 2;
                                break;
                            }
                            break;
                        case 1189723458:
                            if (str.equals("0x80000002")) {
                                i = 5;
                                break;
                            }
                            break;
                        case 1189723459:
                            if (str.equals("0x80000003")) {
                                i = 6;
                                break;
                            }
                            break;
                        case 1189723460:
                            if (str.equals("0x80000004")) {
                                i = 7;
                                break;
                            }
                            break;
                        case 1189723461:
                            if (str.equals("0x80000005")) {
                                i = 3;
                                break;
                            }
                            break;
                        case 1189723462:
                            if (str.equals("0x80000006")) {
                                i = 4;
                                break;
                            }
                            break;
                    }
                    switch (i) {
                        case 0:
                            PlayerInfoUtil.deleteInfo(AplayerActivity.this.url.hashCode() + "");
                            AplayerActivity.this.url = null;
                            message.what = 0;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        case 1:
                            message.what = 1;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        case 2:
                            message.what = 2;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        case 3:
                            message.what = 3;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        case 4:
                            message.what = 4;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        case 5:
                            message.what = 5;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        case 6:
                            message.what = 6;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        case 7:
                            message.what = 7;
                            AplayerActivity.fanhui.sendMessage(message);
                            AplayerActivity.this.finish();
                            return;
                        default:
                            return;
                    }
                }
            });
            this.aPlayer.setOnBufferListener(new OnBufferListener() {
                public void onBuffer(int i) {
                    Log.e("info", "onBuffer:" + i);
                    AplayerActivity.this.findViewById(R.id.caching).setVisibility(i == 100 ? View.INVISIBLE : View.GONE);
                    AplayerActivity.this.cachingProgressHint.setText("缓冲: " + i + "%");
                }
            });
            this.aPlayer.setOnPlayStateChangeListener(new OnPlayStateChangeListener() {
                public void onPlayStateChange(int i, int i2) {
                    switch (i) {
                        case 1:
                            AplayerActivity.this.findViewById(R.id.caching).setVisibility(View.VISIBLE);
                            AplayerActivity.this.cachingProgressHint.setText(AplayerActivity.this.loadingText);
                            break;
                        case 2:
                            AplayerActivity.this.findViewById(R.id.caching).setVisibility(View.VISIBLE);
                            AplayerActivity.this.cachingProgressHint.setText("正在暂停...");
                            break;
                        case 3:
                            AplayerActivity.this.findViewById(R.id.caching).setVisibility(View.GONE);
                            break;
                        case 4:
                            int position = PlayerInfoUtil.getPosition(AplayerActivity.this.url.hashCode() + "");
                            if (position > 0) {
                                AplayerActivity.this.aPlayer.setPosition(position);
                            }
                            AplayerActivity.this.findViewById(R.id.caching).setVisibility(View.GONE);
                            break;
                        case 6:
                            AplayerActivity.this.findViewById(R.id.caching).setVisibility(View.GONE);
                            AplayerActivity.this.cachingProgressHint.setText("正在关闭...");
                            break;
                    }
                    if (i == 4) {
                        AplayerActivity.this.play_large.setVisibility(View.GONE);
                        AplayerActivity.this.play.setImageResource(R.drawable.v_play_pause);
                        AplayerActivity.this.startUIUpdateThread();
                    } else {
                        AplayerActivity.this.play_large.setVisibility(View.VISIBLE);
                        AplayerActivity.this.play.setImageResource(R.drawable.v_play_arrow);
                        AplayerActivity.this.stopUIUpdateThread();
                    }
                    Log.e("info", "preState:" + i2 + " >> State:" + i);
                }
            });
            this.seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    if (AplayerActivity.this.aPlayer != null && z) {
                        AplayerActivity.this.mIsTouchingSeekbar = true;
                        AplayerActivity.this.userSeekPlayProgress(i, seekBar.getMax());
                    }
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                    AplayerActivity.this.tv_info.setVisibility(View.GONE);
                    AplayerActivity.this.mIsTouchingSeekbar = false;
                    AplayerActivity.this.startUIUpdateThread();
                }
            });
            this.mainUIHandler = new MyHandler(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.v_play) {
            if (this.aPlayer == null) {
                return;
            }
            if (this.aPlayer.getState() == 4) {
                this.play.setImageResource(R.drawable.v_play_arrow);
                this.aPlayer.pause();
                return;
            }
            this.play.setImageResource(R.drawable.v_play_pause);
            this.aPlayer.play();
        } else if (view.getId() == R.id.v_back) {
            finish();
        } else if (view.getId() == R.id.v_rotate) {
            int i = getResources().getConfiguration().orientation;
            if (i == 2) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //纵向
            } else if (i == 1) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); //横向
            }
        } else if (view.getId() == R.id.v_player_lock) {
            if (this.suoding) {
                this.lock.setImageResource(R.drawable.v_player_unlocked);
                this.suoding = false;
                showBars();
                return;
            }
            this.suoding = true;
            hideAfterFiveSecond();
            this.lock.setImageResource(R.drawable.v_player_locked);
        } else if (view.getId() == R.id.v_play_large) {
            this.aPlayer.play();
        } else if (view.getId() == R.id.img_more){
            showMore();
        }
    }

    private void showMore(){
        try{
//            TpDialog tpDialog = new TpDialog(this,
//                    v_title.getText().toString(),
//                    v_title.getText().toString(),
//                    this.url,
//                    "1280 x 800",
//                    tv_duration.getText().toString());
//            tpDialog.show();

            //Toast.makeText(this,"开发者偷懒,还没有写好",Toast.LENGTH_LONG).show();
        }catch (Exception ignored){

        }

    }

    /**
     * 隐藏ui
     */
    private void hideAfterFiveSecond() {
        this.header_bar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_out));
        this.header_bar.setVisibility(View.GONE);
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_out);
        this.ctrl_bar.startAnimation(loadAnimation);
        this.ctrl_bar.setVisibility(View.GONE);
        this.lock.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_out));
        this.lock.setVisibility(View.GONE);
        this.ratate.startAnimation(loadAnimation);
        this.ratate.setVisibility(View.GONE);
    }

    private void startUIUpdateThread() {
        if (this.mUpdateThread == null) {
            this.mIsNeedUpdateUIProgress = true;
            this.mUpdateThread = new Thread(new UpdatePlayUIProcess());
            this.mUpdateThread.start();
            return;
        }
        Log.e("info", "null != mUpdateThread");
    }

    private void stopUIUpdateThread() {
        this.mIsNeedUpdateUIProgress = false;
        this.mUpdateThread = null;
    }

    private static String updateTextViewWithTimeFormat(int i) {
        int i2 = (i % 3600) / 60;
        int i3 = i % 60;
        if (i / 3600 != 0) {
            return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(i / 3600), Integer.valueOf(i2), Integer.valueOf(i3)});
        }
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3)});
    }

    private boolean isOverSeekGate(int i, int i2) {
        return Math.abs(i2 - i) > 1000;
    }

    private void userSeekPlayProgress(int i, int i2) {
        if (isOverSeekGate(i * 1000, this.aPlayer.getPosition())) {
            this.mIsTouchingSeekbar = true;
            stopUIUpdateThread();
            this.seekBar.setProgress(i);
            this.tv_info.setText(updateTextViewWithTimeFormat(i) + "/" + updateTextViewWithTimeFormat(i2));
            this.tv_info.setVisibility(View.VISIBLE);
            this.aPlayer.setPosition(i * 1000);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        float x = motionEvent.getX();
        float y = motionEvent.getY();
        //Log.d(TAG, "x : " + x + "---" + "Y: " + y + "-----motionEvent.getAction() : " + motionEvent.getAction());
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:  //按下 常量值0
                this.zongshichang = this.aPlayer.getDuration() / 1000;
                this.dangqianshichang = this.aPlayer.getPosition() / 1000;
                this.anxiazuobiaox = x;
                this.anxiazuobiaoy = y;
                this.dongzuo = 1;
                this.currentVolume = this.audioManager.getStreamVolume(3);
                this.anxiayinliang = this.currentVolume * 6;
                try {
                    x = (((float) System.getInt(getContentResolver(), "screen_brightness")) * 1.0f) / 255.0f;
                } catch (SettingNotFoundException e) {
                    e.printStackTrace();
                }
                this.huadongzongzuobiao = y;
                break;
            case MotionEvent.ACTION_UP:  //弹起 常量值1
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        tv_info.setVisibility(View.GONE);
                        switch (AplayerActivity.this.dongzuo) {
                            case 2: //左右滑动松开手弹起 调节进度
                                if (!AplayerActivity.this.suoding) {
                                    if (AplayerActivity.this.aPlayer.getDuration() > 5) {
                                        AplayerActivity.this.aPlayer.setPosition(AplayerActivity.this.dangqianshituodong * 1000);
                                        return;
                                    }
                                    return;
                                }
                                break;
                            case 3:
                                break;
                            case 4:
                                return;
                            default:
                                AplayerActivity.this.onClickEmptyArea();
                                return;
                        }
                        AplayerActivity.this.dangqianliandu = AplayerActivity.this.dangqianliangdu;
                    }
                }, 100);
                break;

            case MotionEvent.ACTION_MOVE: //拖动触控的情况

                if (!suoding) {
                    float abs = Math.abs(x - this.anxiazuobiaox); //x坐标的滑动距离 取绝对值
                    float abs2 = Math.abs(y - this.anxiazuobiaoy); //y坐标的滑动距离 取绝对值
                    if (this.dongzuo == 1) {
                        if (abs > 50.0f && abs2 < 50.0f) {  //判断为左右滑动
                            this.dongzuo = 2;
                        }
                        if (abs < 50.0f && abs2 > 50.0f && ((double) this.anxiazuobiaox) < ((double) this.width) * 0.25d) {
                            this.dongzuo = 3;
                        }
                        if (abs < 50.0f && abs2 > 50.0f && ((double) this.anxiazuobiaox) > ((double) this.width) * 0.75d) {
                            this.dongzuo = 4;
                        }
                    }
                    switch (dongzuo) {
                        case 2: //左右滑动
                            this.dangqianshituodong =
                                    (int) ((float) ((((double) (((x - this.anxiazuobiaox) / ((float) this.width)) * ((float) this.zongshichang))) * 0.3d) + ((double) this.dangqianshichang)));
                            if (this.dangqianshituodong < 0) {
                                this.dangqianshituodong = 0;
                            }
                            if (this.dangqianshituodong > this.zongshichang) {
                                this.dangqianshituodong = this.zongshichang;
                            }
                            this.tv_info.setVisibility(View.VISIBLE);
                            this.tv_info.setText(updateTextViewWithTimeFormat(this.dangqianshituodong) + "/" + updateTextViewWithTimeFormat(this.zongshichang));
                            break;
                        case 3: //上下滑动
                            this.dangqianliangdu = this.dangqianliandu - ((int) (((y - this.huadongzongzuobiao) * 100.0f) / ((float) this.height)));
                            if (this.dangqianliangdu > 100) {
                                this.dangqianliangdu = 100;
                            }
                            if (this.dangqianliangdu < 7) {
                                this.dangqianliangdu = 7;
                            }
                            this.tv_info.setVisibility(View.VISIBLE);
                            this.tv_info.setText("亮度：" + (((this.dangqianliangdu - 7) * 100) / 93) + "%");
                            setBrightness(this.dangqianliangdu);
                            break;
                        case 4: //上下滑动
                            this.dangqianyinliang = this.anxiayinliang - ((int) (((y - this.huadongzongzuobiao) * 100.0f) / ((float) this.height)));
                            if (this.dangqianyinliang > this.zuidayinliang) {
                                this.dangqianyinliang = this.zuidayinliang;
                            }
                            if (this.dangqianyinliang < 0) {
                                this.dangqianyinliang = 0;
                            }
                            this.tv_info.setVisibility(View.VISIBLE);
                            this.tv_info.setText("音量：" + ((this.dangqianyinliang * 100) / this.zuidayinliang) + "%");
                            this.audioManager.setStreamVolume(3, this.dangqianyinliang / 6, 0);
                            break;
                        default:
                            break;

                    }
                }
                Log.d(TAG, "dongzuo:" + dongzuo);

                break;

        }
        return true;
    }

    private void onClickEmptyArea() {
        if (this.suoding) {
            if (this.lock.getVisibility() != View.VISIBLE) {
                this.lock.setVisibility(View.VISIBLE);
                this.lock.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_in));
                return;
            }
            this.lock.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_out));
            this.lock.setVisibility(View.GONE);
        } else if (this.header_bar.getVisibility() == View.GONE) {
            showBars();
        } else {
            hideBottomUIMenu();
            hideAfterFiveSecond();
        }
    }


    private void hideUi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    //Log.d(TAG,"隐藏ui");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            hideBottomUIMenu();
                            hideAfterFiveSecond();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void showBars() {
        this.header_bar.setVisibility(View.VISIBLE);
        this.header_bar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_in));
        this.ctrl_bar.setVisibility(View.VISIBLE);
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_in);
        this.ctrl_bar.startAnimation(loadAnimation);
        this.lock.setVisibility(View.VISIBLE);
        this.lock.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_in));
        this.ratate.setVisibility(View.VISIBLE);
        this.ratate.startAnimation(loadAnimation);
    }

    public void setBrightness(int i) {
        int i2;
        int i3 = 100;
        if (i < 0) {
            i2 = 0;
        } else {
            i2 = i;
        }
        if (i2 <= 100) {
            i3 = i2;
        }
        LayoutParams attributes = getWindow().getAttributes();
        attributes.screenBrightness = (1.0f * ((float) i3)) / 100.0f;
        getWindow().setAttributes(attributes);
        this.dangqianliangdu = i3;
    }

    protected void hideBottomUIMenu() {
        if (VERSION.SDK_INT > 11 && VERSION.SDK_INT < 19) {
            getWindow().getDecorView().setSystemUiVisibility(8);
        } else if (VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(4102);
        }
    }

    protected void onPause() {
        if (isPlay(this.aPlayer.getState())) {
            this.aPlayer.pause();
            this.mIsSystemCallPause = true;
        }
        stopUIUpdateThread();
        super.onPause();
    }

    protected void onResume() {
        if (this.mIsSystemCallPause) {
            this.aPlayer.play();
            this.mIsSystemCallPause = false;
        }
        startUIUpdateThread();
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        try {
            if (this.url != null) {
                PlayerInfoUtil.addPlayerInfo(this.url.hashCode() + "", this.aPlayer.getPosition());
            }
            this.aPlayer.close();
            this.aPlayer.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private boolean isPlay(int i) {
        if (5 == i || 4 == i) {
            return true;
        }
        return false;
    }


}