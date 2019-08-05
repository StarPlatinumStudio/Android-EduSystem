package service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import com.example.pockettutorms.MainActivity;
import com.example.pockettutorms.R;

import java.io.IOException;

public class MyService extends Service {
    private MediaPlayer mediaPlayer = null;
    private boolean isReady = false;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("服务启动");

        //初始化媒体播放器
         mediaPlayer = MediaPlayer.create(this,R.raw.wave);
        if(mediaPlayer == null){
            return;
        }

        mediaPlayer.stop();
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.release();
                stopSelf();
                return false;
            }
        });


        try{
            mediaPlayer.prepare();
            isReady = true;
        } catch (IOException e) {
            e.printStackTrace();
            isReady = false;
        }

        if(isReady){
            //将背景音乐设置为循环播放
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        Toast.makeText(this,"音乐播放：\nギャラクシー・騎士 - 嘉 禾 天 橙 国 际 大 影 院",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("服务销毁");
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                //停止播放音乐
                mediaPlayer.stop();
            }
            //释放媒体播放器资源
            mediaPlayer.release();
            Toast.makeText(this, "停止播放背景音乐", Toast.LENGTH_LONG).show();}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
