package com.nativeninjas.vista;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.MyApplication;
import com.nativeninjas.modelo.Ajustes;
import com.nativeninjas.prod1.R;

import java.io.IOException;

public class AjustesActivity extends AppCompatActivity {
    private ImageButton btnCambiarMusica;
    private ToggleButton toggleMusicaFondo;
    private Ajustes ajustes;
    private static final int PICK_AUDIO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ajustes);

        // Inicializa la clase Ajustes con la instancia de MyApplication
        MyApplication myApplication = MyApplication.getInstance();
        ajustes = new Ajustes(myApplication);

        // Configura los botones
        btnCambiarMusica = findViewById(R.id.btnCambiarMusica);
        toggleMusicaFondo = findViewById(R.id.toggleMusicaFondo);

        // Configura el listener del botón para elegir música
        btnCambiarMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCancion();
            }
        });

        // Configura el listener del botón para activar/desactivar la música
        toggleMusicaFondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustes.activarDesactivarMusica();
            }
        });
    }

    private void elegirCancion() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, "Selecciona una canción"), PICK_AUDIO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri audioUri = data.getData();
            ajustes.cambiarCancionFondo(audioUri);
        }
    }
}
