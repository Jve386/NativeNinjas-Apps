package com.nativeninjas;

import android.app.Application;
import android.media.MediaPlayer;

import com.nativeninjas.prod1.R;

public class MyApplication extends Application {

    private static MyApplication instance;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Inicializar el MediaPlayer con el archivo de música
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_music);

        // Configurar la música para que se reproduzca en bucle
        mediaPlayer.setLooping(true);

        // Iniciar la reproducción de la música
        mediaPlayer.start();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}