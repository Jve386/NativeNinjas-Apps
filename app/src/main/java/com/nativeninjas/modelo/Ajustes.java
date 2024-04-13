package com.nativeninjas.modelo;

import android.media.MediaPlayer;
import android.net.Uri;

import com.nativeninjas.MyApplication;

public class Ajustes {
    private MyApplication myApplication;

    public Ajustes(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    public void cambiarCancionFondo(Uri audioUri) {
        MediaPlayer mediaPlayer = myApplication.getMediaPlayer();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(myApplication.getApplicationContext(), audioUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activarDesactivarMusica() {
        MediaPlayer mediaPlayer = myApplication.getMediaPlayer();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }
}
