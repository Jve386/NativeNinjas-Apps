package com.nativeninjas.prod1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Jugador extends AppCompatActivity {

    private EditText editTextNombre;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        editTextNombre = findViewById(R.id.editTextNombre);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el nombre ingresado por el jugador
                String nombre = editTextNombre.getText().toString();

                // Verificar si se ha ingresado un nombre
                if (!nombre.isEmpty()) {
                    // Crear un Intent para pasar el nombre a la actividad Partida
                    Intent intent = new Intent(Jugador.this, Partida.class);
                    intent.putExtra("nombreJugador", nombre);
                    startActivity(intent);
                    finish(); // Finalizar esta actividad para que no se pueda volver atrás
                } else {
                    // Mostrar un mensaje indicando que debe ingresar un nombre
                    editTextNombre.setError("Por favor, ingresa tu nombre");
                }
            }
        });
    }
}