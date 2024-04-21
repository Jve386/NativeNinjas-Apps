package com.nativeninjas.vista;


import java.util.Date;
import java.util.Random;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.controls.Control;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.media.MediaPlayer;

import com.nativeninjas.controlador.Controlador;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import com.nativeninjas.prod1.R;


@RequiresApi(api = Build.VERSION_CODES.O)

public class Partida extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
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
    private MediaPlayer mpGanar, mpPerder, mpEmpate; // Agregar reproductores de audio
    private boolean juegoEnProgreso = false; // handler
    private ImageView imgPiedra;
    private ImageView imgTijera;
    private ImageView imgPapel;
    private ImageView imgAnonimo;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                        result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {

            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                // Logic to handle location object
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }


        });

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
        imgPiedra = findViewById(R.id.imgPiedra);
        imgPapel = findViewById(R.id.imgPapel);
        imgTijera = findViewById(R.id.imgTijera);
        imgAnonimo = findViewById(R.id.imgAnonimo);


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
                // Cambiar la imagen del ImageView a ic_piedra_red
                imgPiedra.setImageResource(R.drawable.ic_piedra_red);

                // Programar un retraso de 1 segundo antes de cambiar la imagen de nuevo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Cambiar la imagen de nuevo a ic_piedra después de 1 segundo
                        imgPiedra.setImageResource(R.drawable.ic_piedra);
                    }
                }, 1000); // 1000 milisegundos = 1 segundo
            }
        });

        btnPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar("Papel");
                // Cambiar la imagen del ImageView a ic_piedra_red
                imgPapel.setImageResource(R.drawable.ic_papel_red);

                // Programar un retraso de 1 segundo antes de cambiar la imagen de nuevo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Cambiar la imagen de nuevo a ic_piedra después de 1 segundo
                        imgPapel.setImageResource(R.drawable.ic_papel);
                    }
                }, 1000); // 1000 milisegundos = 1 segundo
            }
        });

        btnTijera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar("Tijera");
                // Cambiar la imagen del ImageView a ic_piedra_red
                imgTijera.setImageResource(R.drawable.ic_tijera_red);

                // Programar un retraso de 1 segundo antes de cambiar la imagen de nuevo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Cambiar la imagen de nuevo a ic_piedra después de 1 segundo
                        imgTijera.setImageResource(R.drawable.ic_tijera);
                    }
                }, 1000); // 1000 milisegundos = 1 segundo
            }
        });

        // Inicializar el DatabaseHelper
        controlador = new Controlador();
        controlador.addDatos(this);

        // Obtener la puntuación más alta de la base de datos
        String idUsuario = getIntent().getStringExtra("nombreJugador");
        puntuacionMasAltaEnBBDD = controlador.obtenerRecord();

        // Inicializar reproductores de audio
        mpGanar = MediaPlayer.create(this, R.raw.ganar);
        mpPerder = MediaPlayer.create(this, R.raw.perder);
        mpEmpate = MediaPlayer.create(this, R.raw.empate);

        // Verificar si el juego está en progreso
        if (juegoEnProgreso) {
            return; // Salir si el juego está en progreso
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void jugar(String movimientoJugador) {
        // Obtener el movimiento de la computadora
        String movimientoComputadoraTexto = "La computadora eligió: " + movimientoComputadora;

        if (movimientoJugador.equals(movimientoComputadora)) {
            txtResultado.setText("¡Empate!\n" + movimientoComputadoraTexto);
            mpEmpate.start(); // Reproducir el sonido de empate
        } else if ((movimientoJugador.equals("Piedra") && movimientoComputadora.equals("Tijera")) ||
                (movimientoJugador.equals("Papel") && movimientoComputadora.equals("Piedra")) ||
                (movimientoJugador.equals("Tijera") && movimientoComputadora.equals("Papel"))) {
            txtResultado.setText("¡Ganaste!\n" + movimientoComputadoraTexto);
            contadorMonedas++;
            mpGanar.start(); // Reproducir el sonido de ganar
            aplicarAnimacionGanar(); // Aplicar la animación de ganar
        } else {
            txtResultado.setText("¡Perdiste!\n" + movimientoComputadoraTexto);
            intentos--;
            if (intentos == 0) {
                // Si el jugador pierde todos los Intentos, guardar la puntuación final y abrir la actividad Final
                guardarPuntuacionFinal(contadorMonedas);
                return;
            }
            actualizarIntentos();
            mpPerder.start(); // Reproducir el sonido de perder
        }

        // Mostrar el contador de ganadas
        txtContador.setText("Partidas ganadas: " + contadorMonedas);

        // Postergar el siguiente movimiento durante 2 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                movimientoComputadora = opciones[random.nextInt(opciones.length)];
            }
        }, 2000); // 2000 milisegundos = 2 segundos
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void guardarPuntuacionFinal(int puntuacionFinal) {
        // Obtener el nombre del jugador
        String nombreJugador = getIntent().getStringExtra("nombreJugador");
        Log.d("Fecha", "Fecha actual: " + new Date().toString());
        // Insertar la puntuación final y el nombre del jugador en la base de datos
        controlador.guardarPartida(nombreJugador, puntuacionFinal, longitude, latitude);
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

    // Funcion para reproducir los sonidos.
    private void reproducirSonido(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Método para aplicar la animación de ganar
    private void aplicarAnimacionGanar() {
        // Cargar las animaciones desde los archivos XML
        Animation animacionExpand = AnimationUtils.loadAnimation(this, R.anim.animacion_expand);
        Animation animacionReduce = AnimationUtils.loadAnimation(this, R.anim.animacion_reduce);

        // Establecer un oyente de animación para la animación de expansión
        animacionExpand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // No es necesario hacer nada en el inicio de la animación
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Restaurar la imagen original después de que termine la animación
                imgAnonimo.startAnimation(animacionReduce);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // No es necesario hacer nada en la repetición de la animación
            }
        });

        // Aplicar la animación de expansión al ImageView
        imgAnonimo.startAnimation(animacionExpand);
    }
}