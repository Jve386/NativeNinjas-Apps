package com.nativeninjas.vista;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.nativeninjas.controlador.Controlador;
import com.nativeninjas.prod1.R;
import com.nativeninjas.services.Notificaciones;

public class Final extends AppCompatActivity {

    private TextView txtPuntuacionFinal, txtRecord, txtPuntuacionMasAlta;
    private Button btnMain, btnSalir, btnReintentar;
    private Controlador controlador;
    private String idUsuario;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        txtPuntuacionMasAlta = findViewById(R.id.txtPuntuacionMasAlta);
        txtPuntuacionFinal = findViewById(R.id.txtPuntuacionFinal);
        txtRecord = findViewById(R.id.txtRecord);
        btnMain = findViewById(R.id.btnMain);
        btnSalir = findViewById(R.id.btnSalir);
        btnReintentar = findViewById(R.id.btnReintentar);
       // Notificaciones notificaciones = new Notificaciones(this); // Reemplaza "context" con el contexto adecuado, como por ejemplo "this" si estás dentro de una actividad

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        String channelId = "record_channel";
        CharSequence channelName = "Record Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(channel);

        // Recuperar la puntuación final de la actividad anterior
        int puntuacionFinal = getIntent().getIntExtra("puntuacionFinal", 0);
        txtPuntuacionFinal.setText("Puntuación Final: " + puntuacionFinal);


        // Obtener la puntuación más alta de la base de datos
        controlador = new Controlador();
        controlador.addDatos(this);
        int puntuacionMasAltaEnBBDD = controlador.obtenerRecord();
        txtPuntuacionMasAlta.setText("Puntuación más alta: " + puntuacionMasAltaEnBBDD);

        // Comparar la puntuación final con la puntuación más alta en la base de datos
        if (puntuacionFinal > puntuacionMasAltaEnBBDD) {
            // Si la puntuación final es mayor que la puntuación más alta en la base de datos, mostrar "Record superado"
            txtRecord.setText("Record superado");
            // Prueba 1
            Toast.makeText(getApplicationContext(), "Record Superado", Toast.LENGTH_SHORT).show();
            //Prueba 2

            // Paso 3: Crear la notificación
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.logonativeninjas)
                    .setContentTitle("¡Nuevo récord!")
                    .setContentText("Has superado tu récord anterior en el juego.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Paso 4: Mostrar la notificación
            notificationManager.notify(1, builder.build());

            Notificaciones notificaciones = new Notificaciones(this);
            notificaciones.showNotification("Record superado");


        } else {
            // Si la puntuación final no es mayor, mostrar "Record no superado"
            txtRecord.setText("Record no superado");
        }



        // Botón "Volver a Jugar"
        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final.this, Jugador.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });


        // Configurar el onClickListener para el botón "Volver al Menú"
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });

        // Configurar el onClickListener para el botón "Salir"
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Cerrar todas las actividades de la aplicación
            }
        });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Final.this);
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