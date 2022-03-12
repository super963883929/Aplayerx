package com.yfoo.aplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tools.aplayer.Aplayer;

public class MainActivity extends AppCompatActivity {

    private Aplayer aplayer;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText.setText("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4");
        aplayer = new Aplayer("",this,"加载中...");

    }

    public void playVideo(View view){
        aplayer.start("测试标题",editText.getText().toString().trim());
    }
}