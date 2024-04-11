package com.nativeninjas.modelo;

import android.media.MediaPlayer;

import com.nativeninjas.MyApplication;
public class Ajustes {
    private MyApplication myApplication;

    public Ajustes(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    public void cambiarCancionFondo() {
        // Implementa la lógica para cambiar la canción de fondo
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
