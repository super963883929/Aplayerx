# Aplayerx
Aplayer播放器,视频播放器,几乎可以播放所有格式类型的视频

导入之后要记得在build.gradle(:app)里面加载so文件

    defaultConfig {
            ....
        ndk{ //加载so文件
            abiFilters "armeabi-v7a"
            // abiFilters 'arm64-v8a' //64位系统请用 arm64-v8a
        }
    }

//------------------------------------------------------------

    public class MainActivity extends AppCompatActivity {
     
        private EditText editText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            editText = findViewById(R.id.editText);
            editText.setText("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4");
           
            //回调
             APlayerActivity.setOnPlayerCallBack(new OnPlayerCallBack() {
                @Override
                public void onFinish() {
                   
                }

                @Override
                public void onPause() {

                }

                @Override
                public void onStart() {

                }
            });
        }
        //调用
        public void playVideo(View view){
             APlayerActivity.playVideo(this,"测试标题",editText.getText().toString().trim(),false,0)
        }
    }


混淆问题: 注意不要混淆了

    #Aplayer
    -keep class com.tools.aplayer* { *; }
    -keep class com.aplayer.* { *; }
    -keep class com.example.* { *; }

![This is an image](https://raw.githubusercontent.com/super963883929/Aplayerx/master/img_test.jpg)
