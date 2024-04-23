package com.nativeninjas;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.telephony.TelephonyManager;

import com.nativeninjas.prod1.R;
import com.nativeninjas.services.call.*;

public class MyApplication extends Application {

    private static MyApplication instance;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
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
        isPlaying = true;


        // necesito que se pare la música cuando hay notificaciones como llamadas entrantes, alarmas, etc
        // Registrar el BroadcastReceiver para detectar notificaciones
        IntentFilter filter = new IntentFilter();
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        //filter.addAction(AlarmManager.ACTION_ALARM_CHANGED); // alarma por configurar
        registerReceiver(notificationReceiver, filter);
    }
    private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
            @Override
        public void onReceive(Context context, Intent intent) {
            // Pausar la música cuando llegue una notificación
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
            }
        }
    };
    public static MyApplication getInstance() {
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
