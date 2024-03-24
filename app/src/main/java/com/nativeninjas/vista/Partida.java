package com.nativeninjas.vista;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import com.nativeninjas.modelo.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Partida extends AppCompatActivity {
    private Button btnPiedra, btnPapel, btnTijera;
    private String nombreJugador;
    private String fechaActual = obtenerFechaActual();
    private TextView txtResultado, txtContador, txtIntentos;
    private String[] opciones = {"Piedra", "Papel", "Tijera"};
    private Random random = new Random();
    private String movimientoComputadora = opciones[random.nextInt(opciones.length)];
    private int contadorMonedas = 0;
    private int intentos = 3;

    private DatabaseHelper databaseHelper; // Agregar una instancia de DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partida_activity);

        // Obtener el ActionBar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // Ocultar el título por defecto
            actionBar.setDisplayShowTitleEnabled(false);
        }

        btnPiedra = findViewById(R.id.btnPiedra);
        btnPapel = findViewById(R.id.btnPapel);
        btnTijera = findViewById(R.id.btnTijera);
        txtResultado = findViewById(R.id.txtResultado);
        txtContador = findViewById(R.id.txtContador);
        txtIntentos = findViewById(R.id.txtIntentos);

        actualizarIntentos();

        // Obtener el nombre del jugador del Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("nombreJugador")) {
            nombreJugador = intent.getStringExtra("nombreJugador");
        }

        // Nombre del equipo en el ActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("NativeNinjas");
        }

        // Configurar el nombre del jugador en el TextView correspondiente
        TextView txtNombreJugador = findViewById(R.id.txtNombreJugador);
        txtNombreJugador.setText(nombreJugador);

        btnPiedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar("Piedra");
            }
        });

        btnPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar("Papel");
            }
        });

        btnTijera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar("Tijera");
            }
        });

        // Inicializar el DatabaseHelper
        databaseHelper = new DatabaseHelper(this);
    }

    private void jugar(String movimientoJugador) {
        // Obtener el movimiento de la computadora
        String movimientoComputadoraTexto = "La computadora eligió: " + movimientoComputadora;

        if (movimientoJugador.equals(movimientoComputadora)) {
            txtResultado.setText("¡Empate!\n" + movimientoComputadoraTexto);
        } else if ((movimientoJugador.equals("Piedra") && movimientoComputadora.equals("Tijera")) ||
                (movimientoJugador.equals("Papel") && movimientoComputadora.equals("Piedra")) ||
                (movimientoJugador.equals("Tijera") && movimientoComputadora.equals("Papel"))) {
            txtResultado.setText("¡Ganaste!\n" + movimientoComputadoraTexto);
            contadorMonedas++;
        } else {
            txtResultado.setText("¡Perdiste!\n" + movimientoComputadoraTexto);
            intentos--;
            if (intentos == 0) {
                // Si el jugador pierde todos los Intentos, guardar la puntuación final y abrir la actividad Final
                guardarPuntuacionFinal(contadorMonedas);
                return;
            }
            actualizarIntentos();
        }

        // Mostrar el contador de ganadas
        txtContador.setText("Partidas ganadas: " + contadorMonedas);

        // Actualizar el movimiento de la computadora para la siguiente jugada
        movimientoComputadora = opciones[random.nextInt(opciones.length)];
    }

    private void guardarPuntuacionFinal(int puntuacionFinal) {
        // Obtener el nombre del jugador
        String nombreJugador = getIntent().getStringExtra("nombreJugador");
        String fechaActual = obtenerFechaActual();
        Log.d("Fecha", "Fecha actual: " + fechaActual);
        // Insertar la puntuación final y el nombre del jugador en la base de datos
        databaseHelper.insertarJugador(nombreJugador, puntuacionFinal);
        if (id != -1) {
            Toast.makeText(this, "Puntuación guardada en la base de datos.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar la puntuación en la base de datos.", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, Final.class);
        intent.putExtra("puntuacionFinal", puntuacionFinal);
        startActivity(intent);
        finish(); // Finalizar la actividad actual
    }


    private void actualizarIntentos() {
        txtIntentos.setText("Intentos restantes: " + intentos);
    }

    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String fechaActual = dateFormat.format(date);
        Log.d("Fecha", "Fecha actual: " + fechaActual);
        return fechaActual;
    }
}