package com.example.isangeet;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button button4;
    MediaPlayer player;
    AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    public void play(View view){
        player.start();
    }
    public void pause(View view){
        player.pause();
    }
    public void stop(View view){
        player.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this,R.raw.music)
        ;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar seekVol = findViewById(R.id.seekVol);
           seekVol.setMax(maxVol);
           seekVol.setProgress(curVol);

           seekVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }
           });

           final SeekBar seekProg = findViewById(R.id.seekProg);

           new Timer().scheduleAtFixedRate(new TimerTask() {
               @Override
               public void run() {
                   seekProg.setProgress(player.getCurrentPosition());
               }
           },0, 100 );
            seekProg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    player.seekTo(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
// Adding remote data source to the music player
            mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://pagalworld.ink/files/download/type/64/id/11576");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this, "Ready To play", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
            }
        });
        mediaPlayer.prepareAsync();
    }
    public void openActivity(View v){
        button4=findViewById(R.id.button4);
        Toast.makeText(this, "Hi,You entered in the Video", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}