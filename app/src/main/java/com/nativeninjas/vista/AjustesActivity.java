package com.nativeninjas.vista;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.MyApplication;
import com.nativeninjas.modelo.Ajustes;
import com.nativeninjas.prod1.R;

public class AjustesActivity extends AppCompatActivity {
    private ImageButton btnCambiarMusica;
    private ToggleButton toggleMusicaFondo;
    private Ajustes ajustes;

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

        // Configura los listeners de los botones
        btnCambiarMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustes.cambiarCancionFondo();
            }
        });

        toggleMusicaFondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustes.activarDesactivarMusica();
            }
        });
    }
}
