package com.yfoo.aplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tools.aplayer.APlayerActivity;
import com.tools.aplayer.playCallback.OnPlayerCallBack;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        String url = "http://vodkgeyttp8.vod.126.net/cloudmusic/IWYjYDQ0ZDExIDMxJDAgIg==/mv/303142/911ffb38fa29e85ce81441f921124e5c.mp4?wsSecret=fb9f1996c735059d75940b6c3c76039d&wsTime=1673410670";
        editText.setText("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4"); //"http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4"
       // aplayer = new Aplayer("",this,"加载中...");
         APlayerActivity.setOnPlayerCallBack(new OnPlayerCallBack() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPause() {
                Toast.makeText(MainActivity.this, "播放暂停", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Toast.makeText(MainActivity.this, "播放开始", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void playVideo(View view){
       // aplayer.start("测试标题",editText.getText().toString().trim());

        APlayerActivity.playVideo(this,"测试标题",editText.getText().toString().trim(),false,0);
    }
}
