package com.nativeninjas.vista;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.MyApplication;
import com.nativeninjas.modelo.Ajustes;
import com.nativeninjas.prod1.R;

import java.io.IOException;
import java.util.Locale;

public class AjustesActivity extends AppCompatActivity {
    private ImageButton btnCambiarMusica;
    private ToggleButton toggleMusicaFondo;
    private Ajustes ajustes;
    private static final int PICK_AUDIO_REQUEST = 1;

    private Button btnAtras, btnAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ajustes);

        Configuration config = getResources().getConfiguration();
        Locale locale = config.locale;
        String codigo = locale.getLanguage();

        if (codigo.equals("es")) getSupportActionBar().setTitle("Piedra, papel y tijera");
        else if (codigo.equals("en")) getSupportActionBar().setTitle("Rock, paper and scissors");
        else if (codigo.equals("ca")) getSupportActionBar().setTitle("Pedra, paper i tisora");

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

        btnAtras = findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjustesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAyuda = findViewById(R.id.btnAyuda);

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjustesActivity.this, paginaWebView.class);
                startActivity(intent);
            }
        });

        Spinner spinnerOptions = findViewById(R.id.spinner_options);

        // Definir los elementos del menú
        String[] options = {"Español", "Inglés", "Catalán"};

        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        spinnerOptions.setAdapter(adapter);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Acciones a realizar cuando se selecciona una opción
                String selectedOption = options[position];
                // Aquí puedes realizar las acciones específicas para cada opción seleccionada
                switch (selectedOption) {
                    case "Español":
                        // Acciones para la opción 1
                        cambiarIdioma(AjustesActivity.this, "es");
                        break;
                    case "Inglés":
                        // Acciones para la opción 2
                        cambiarIdioma(AjustesActivity.this, "en");
                        break;
                    case "Catalán":
                        // Acciones para la opción 3
                        cambiarIdioma(AjustesActivity.this, "ca");
                        break;
                    default:
                        // Acciones predeterminadas
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se selecciona ninguna opción (si es necesario)
            }

        });
    }

    public void cambiarIdioma(Context context, String codigoIdioma) {
        Locale nuevaloc = new Locale(codigoIdioma);
        Locale.setDefault(nuevaloc);

        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
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
