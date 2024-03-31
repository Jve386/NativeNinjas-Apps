package com.nativeninjas.vista;


import java.util.Date;
import java.util.Random;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.controls.Control;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import com.nativeninjas.controlador.Controlador;

import com.nativeninjas.prod1.R;


@RequiresApi(api = Build.VERSION_CODES.O)

public class Partida extends AppCompatActivity {
    private Button btnPiedra, btnPapel, btnTijera;
    private String nombreJugador;
    private TextView txtResultado, txtContador, txtIntentos;
    private String[] opciones = {"Piedra", "Papel", "Tijera"};
    private Random random = new Random();
    private String movimientoComputadora = opciones[random.nextInt(opciones.length)];
    private int contadorMonedas = 0;
    private int intentos = 3;
    private int puntuacionMasAltaEnBBDD; // Variable para almacenar la puntuación más alta de la base de datos
    private Controlador controlador; // Agregar una instancia de Controlador

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
        controlador = new Controlador();
        controlador.addDatos(this);

        // Obtener la puntuación más alta de la base de datos
        String idUsuario = getIntent().getStringExtra("nombreJugador");
        puntuacionMasAltaEnBBDD = controlador.obtenerRecord(idUsuario);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void guardarPuntuacionFinal(int puntuacionFinal) {
        // Obtener el nombre del jugador
        String nombreJugador = getIntent().getStringExtra("nombreJugador");
        Log.d("Fecha", "Fecha actual: " + new Date().toString());
        // Insertar la puntuación final y el nombre del jugador en la base de datos
        controlador.guardarPartida(nombreJugador, puntuacionFinal);
        /** if (id != -1) {
            Toast.makeText(this, "Puntuación guardada en la base de datos.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar la puntuación en la base de datos.", Toast.LENGTH_SHORT).show();
        }
         **/

        Intent intent = new Intent(this, Final.class);
        intent.putExtra("nombreJugador", nombreJugador);
        intent.putExtra("puntuacionFinal", puntuacionFinal);
        intent.putExtra("puntuacionMasAltaEnBBDD", puntuacionMasAltaEnBBDD); // Pasar la puntuación más alta de la BBDD
        startActivity(intent);
        finish(); // Finalizar la actividad actual
    }


    private void actualizarIntentos() {
        txtIntentos.setText("Intentos restantes: " + intentos);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Partida.this);
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