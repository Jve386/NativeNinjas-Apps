package com.nativeninjas.vista;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.util.TimeZone;
import android.media.MediaScannerConnection;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import androidx.core.app.ActivityCompat;




import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
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
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.nativeninjas.controlador.Controlador;
import com.nativeninjas.prod1.R;
import com.nativeninjas.services.Notificaciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Final extends AppCompatActivity {

    private TextView txtPuntuacionFinal, txtRecord, txtPuntuacionMasAlta;
    private Button btnMain, btnSalir, btnReintentar, btnScreenShoot, btnPremio;
    private Controlador controlador;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;
    // The indices for the projection array above.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Configuration config = getResources().getConfiguration();
        Locale locale = config.locale;
        String codigo = locale.getLanguage();

        if (codigo.equals("es")) getSupportActionBar().setTitle("Piedra, papel y tijera");
        else if (codigo.equals("en")) getSupportActionBar().setTitle("Rock, paper and scissors");
        else if (codigo.equals("ca")) getSupportActionBar().setTitle("Pedra, paper i tisora");

        txtPuntuacionMasAlta = findViewById(R.id.txtPuntuacionMasAlta);
        txtPuntuacionFinal = findViewById(R.id.txtPuntuacionFinal);
        txtRecord = findViewById(R.id.txtRecord);
        btnMain = findViewById(R.id.btnMain);
        btnSalir = findViewById(R.id.btnSalir);
        btnReintentar = findViewById(R.id.btnReintentar);
        btnScreenShoot= findViewById(R.id.btnScreenShoot);
        btnPremio = findViewById(R.id.btnPremio);
        Notificaciones notificaciones = new Notificaciones(this); // Reemplaza "context" con el contexto adecuado, como por ejemplo "this" si estás dentro de una actividad

        // Recuperar la puntuación final de la actividad anterior
        int puntuacionFinal = getIntent().getIntExtra("puntuacionFinal", 0);
        txtPuntuacionFinal.setText("Puntuación Final: " + puntuacionFinal);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        String channelId = "record_channel";
        CharSequence channelName = "Record Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(channel);

        // Obtener la puntuación más alta de la base de datos

        controlador = new Controlador();
        controlador.addDatos(this);
        // Obtener la puntuación más alta de la base de datos desde el intent
        int puntuacionMasAltaEnBBDD = getIntent().getIntExtra("puntuacionMasAltaEnBBDD", 0);


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
                    .setContentTitle("¡Nuevo récord personal!")
                    .setContentText("Has superado tu récord anterior en el juego.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Paso 4: Mostrar la notificación
            notificationManager.notify(1, builder.build());
            notificaciones.showNotification("Record superado, Muy bien jugado!");
        } else {
            // Si la puntuación final no es mayor, mostrar "Record no superado"
            txtRecord.setText("Record no superado");

        }

        txtPuntuacionMasAlta.setText("Puntuación más alta: " + puntuacionMasAltaEnBBDD);

        anadirDatosCalendario();



        // Botón "Volver a Jugar"
        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final.this, Jugador.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });
        
        btnPremio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final.this, Premios.class);
                intent.putExtra("puntuacionFinal", puntuacionFinal);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });

        btnScreenShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si el permiso WRITE_EXTERNAL_STORAGE está concedido
                if (ContextCompat.checkSelfPermission(Final.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Si el permiso no está concedido, solicitarlo al usuario
                    ActivityCompat.requestPermissions(Final.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
                } else {
                    // Si el permiso ya está concedido, ejecutar la lógica para capturar la pantalla

                    // Crear un objeto Date para obtener una marca de tiempo única
                    Date now = new Date();
                    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                    try {
                        // Habilitar el caché de la vista raíz para permitir la captura de pantalla
                        View rootView = getWindow().getDecorView().getRootView();
                        rootView.setDrawingCacheEnabled(true);

                        // Capturar la vista raíz como un Bitmap
                        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());

                        // Deshabilitar el caché de la vista raíz después de la captura de pantalla
                        rootView.setDrawingCacheEnabled(false);

                        // Crear un archivo temporal para guardar la captura de pantalla con el nombre "NativeNinjasScreenshot.jpg"
                        File imageFile =  File.createTempFile("NativeNinjasScreenshot", ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));

                        // Crear un flujo de salida para escribir en el archivo
                        FileOutputStream outputStream = new FileOutputStream(imageFile);

                        // Comprimir el bitmap en formato JPEG y escribirlo en el archivo
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                        // Vaciar y cerrar el flujo de salida
                        outputStream.flush();
                        outputStream.close();

                        // Escanear el archivo para que aparezca en la galería de medios
                        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{imageFile.getAbsolutePath()}, null, null);

                        // Mostrar un mensaje de éxito
                        Toast.makeText(getApplicationContext(), "Captura de pantalla guardada en la galería", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // Manejar cualquier excepción de E/S
                        e.printStackTrace();
                    }
                }
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

    private void anadirDatosCalendario() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Partida PiedraPapelTijera")
                .putExtra(CalendarContract.Events.DESCRIPTION, "partida")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);

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