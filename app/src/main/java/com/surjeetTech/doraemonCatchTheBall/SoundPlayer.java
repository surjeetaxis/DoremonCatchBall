package com.surjeetTech.doraemonCatchTheBall;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX=2;
    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;

    public SoundPlayer(Context context){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            audioAttributes=new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool=new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }else {
            soundPool=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        }

        hitSound=soundPool.load(context, R.raw.guitarup_full, 1);
        overSound=soundPool.load(context, R.raw.guitarup_raw,1);
    }
    public void playHitSound(){
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
    public void plaOverSound(){
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }


}
