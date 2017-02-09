package com.yobin.stee.livingdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

/**
 * Created by yobin_he on 2017/2/9.
 */

public class VideoViewActivity extends AppCompatActivity {
    private static final String TAG = "VideoViewActivity";
    private IjkVideoView mVideoView;
    private boolean mBackPressed;
    private String mVideoPath;
    private String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        mVideoPath = getIntent().getStringExtra("stream_addr");

        Log.i(TAG, "---->>>mVideoPath" + mVideoPath);

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);

        //init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        if(mVideoPath !=null){
            mVideoView.setVideoPath(mVideoPath);
//            mVideoView.setVideoPath(url);
        }
        mVideoView.start();

    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBackPressed || !mVideoView.isBackgroundPlayEnabled()){
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        }else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }
}
