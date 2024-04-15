package com.nativeninjas.vista;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.nativeninjas.MyApplication;
import com.nativeninjas.controlador.Controlador;
import com.nativeninjas.prod1.R;

public class MainActivity extends AppCompatActivity {
    private Button btnJugar, btnSalir, btnRanking, btnAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJugar = findViewById(R.id.btnJugar);
        btnSalir = findViewById(R.id.btnSalir);
        btnRanking = findViewById(R.id.btnRanking);
        btnAjustes = findViewById(R.id.btnAjustes);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        String channelId = "record_channel";
        CharSequence channelName = "Record Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(channel);
        // Paso 2: Detectar el nuevo récord y guardar la información
        int nuevoRecord = getIntent().getIntExtra("puntuacionFinal", 0); // Obtener el nuevo récord
       // int recordAnterior = controlador.obtenerRecord(); // Obtener el récord anterior
      /*  if (nuevoRecord > recordAnterior) {
            // Paso 3: Crear la notificación
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.logonativeninjas)
                    .setContentTitle("¡Nuevo récord!")
                    .setContentText("Has superado tu récord anterior en el juego.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Paso 4: Mostrar la notificación
            notificationManager.notify(1, builder.build());
        }
*/

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad del jugador para elegir el nombre
                Intent intent = new Intent(MainActivity.this, Jugador.class);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar un diálogo de confirmación antes de salir
                mostrarDialogoConfirmacion();
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad del ranking
                Intent intent = new Intent(MainActivity.this, Ranking.class);
                startActivity(intent);
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de ajustes
                Intent intent = new Intent(MainActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Esta es la app del clásico juego Piedra, Papel, Tijera, desarrollada por el equipo Native Ninjas");
            builder.show();
            return true;
        } else if (id == R.id.exit) {
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().getMediaPlayer().stop();
    }
}