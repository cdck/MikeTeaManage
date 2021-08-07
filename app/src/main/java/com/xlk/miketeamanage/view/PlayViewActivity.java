package com.xlk.miketeamanage.view;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.VideoView;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.R;

public class PlayViewActivity extends AppCompatActivity {

    private VideoView video_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_view);
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        video_view = findViewById(R.id.video_view);
        /* **** **  将资源文件拷贝到本机的目录下,使用文件路径播放  ** **** */
//        ResourceUtils.copyFileFromAssets("video_preview_h264.mp4", getFilesDir() + "/video_preview_h264.mp4");
//        video_view.setVideoPath(getFilesDir() + "/video_preview_h264.mp4");
        /* **** **  直接使用uri播放  ** **** */
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video_preview_h264;
        video_view.setVideoURI(Uri.parse(uri));
        video_view.start();
        //监听播放完成通知
        video_view.setOnCompletionListener(mp -> {
            //播放完成后进行重新播放，达到循环播放的效果
            video_view.start();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                finish();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        video_view.stopPlayback();
        super.onDestroy();
    }
}