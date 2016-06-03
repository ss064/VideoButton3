package com.example.suzukisusumu_sist.videobutton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends AppCompatActivity {
    //onCreate内で定義すると、onClickListenerから参照できないため、ここで定義
    public VideoView video;
    public EditText counter;
    public int[] videoFileName={R.raw.sample,R.raw.cm01,R.raw.cm02};
    public String videoPath;
    public int videoPoint=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video = (VideoView) findViewById(R.id.videoView);
        //動画メディアの指定
        videoPath="android.resource://" + this.getPackageName() + "/" + videoFileName[videoPoint];
        video.setVideoPath(videoPath);

        counter = (EditText) findViewById(R.id.Counter);

        //再生時間表示に関する処理
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter.post(new Runnable() {
                    @Override
                    public void run() {
                        counter.setText(String.valueOf( video.getCurrentPosition() / 1000) + "s");
                    }
                });
            }
        }, 0, 50);

        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoPath="android.resource://" + getPackageName() + "/" + videoFileName[++videoPoint%videoFileName.length];
                video.setVideoPath(videoPath);
                video.seekTo(0);
                video.start();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
            if (video.isPlaying()) video.pause();
            else video.start();
        }
        if(keyCode==KeyEvent.KEYCODE_MEDIA_PLAY) video.start();
        if(keyCode==KeyEvent.KEYCODE_MEDIA_PAUSE) video.pause();
        return super.onKeyDown(keyCode, event);
    }

}
