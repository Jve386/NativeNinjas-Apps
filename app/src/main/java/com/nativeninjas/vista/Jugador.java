package com.nativeninjas.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.prod1.R;

import java.util.Locale;

public class Jugador extends AppCompatActivity {

    private EditText editTextNombre;
    private Button btnConfirmar, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        Configuration config = getResources().getConfiguration();
        Locale locale = config.locale;
        String codigo = locale.getLanguage();

        if (codigo.equals("es")) getSupportActionBar().setTitle("Piedra, papel y tijera");
        else if (codigo.equals("en")) getSupportActionBar().setTitle("Rock, paper and scissors");
        else if (codigo.equals("ca")) getSupportActionBar().setTitle("Pedra, paper i tisora");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.info) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Jugador.this);
            builder.setMessage("Esta es la app del clásico juego Piedra, Papel, Tijera, desarrollada por el equipo Native Ninjas");
            builder.show();
            return true;
        } else if (id == R.id.exit) {
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}