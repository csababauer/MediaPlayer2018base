package com.example.android.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import static com.example.android.mediaplayer.R.id.play;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private SeekBar sb;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //initialize seekbar
        sb = (SeekBar) findViewById(R.id.seekBar);
        //sb.setMax(100);
        sb.setProgress(7);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //sb.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        /**onclick listener for play button, play song*/
        Button playMusic = (Button) findViewById(play);
        playMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**initialize mediaplayer*/
                mp = MediaPlayer.create(MainActivity.this, R.raw.ringtone);

                mp.start();
                Toast.makeText(MainActivity.this, "music is on", Toast.LENGTH_SHORT).show();


                /**at the end of the song, call release*/
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        Toast.makeText(MainActivity.this, "end of song", Toast.LENGTH_SHORT).show();
                        releaseMediaPlayer();
                    }
                });

            }
        });

        //onclick listener for pause button
        Button pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /**pause only works when mediaplayer is playing and initialized*/
                if (mp != null) {
                    mp.pause();
                }
            }
        });

        //onclick listener for volume seekbar
        SeekBar volume = (SeekBar) findViewById(R.id.seekBar);
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        //seekbar sets volume
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /** if app stopped music stops */
    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp = null;
        }
    }


}
