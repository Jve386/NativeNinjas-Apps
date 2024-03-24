package com.nativeninjas.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Jugador extends AppCompatActivity {

    private EditText editTextNombre;
    private Button btnConfirmar, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        editTextNombre = findViewById(R.id.editTextNombre);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnSalir = findViewById(R.id.btnSalir);

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

        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Mostrar un diálogo de confirmación antes de salir
                mostrarDialogoConfirmacion();
            }
        });
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Jugador.this);
        builder.setMessage("¿Vas a salir sin echar una partidita?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada, simplemente cerrar el diálogo
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Salir de la aplicación
                finishAffinity(); // Cerrar todas las actividades de la aplicación
            }
        });
        builder.show();
    }
}