# Aplayerx
Aplayer播放器,视频播放器,几乎可以播放所有格式类型的视频

导入之后要记得在build.gradle(:app)里面加载so文件

    defaultConfig {
            ....
        ndk{ //加载so文件
            abiFilters "armeabi-v7a"
        }
    }

//------------------------------------------------------------

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


混淆问题: 注意不要混淆了
#Aplayer
-keep class com.tools.aplayer* { *; }

![This is an image](https://raw.githubusercontent.com/super963883929/Aplayerx/master/img_test.jpg)
