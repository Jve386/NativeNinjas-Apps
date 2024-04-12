package com.nativeninjas.vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.controlador.Controlador;
import com.nativeninjas.prod1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Final extends AppCompatActivity {

    private TextView txtPuntuacionFinal, txtRecord, txtPuntuacionMasAlta;
    private Button btnMain, btnSalir, btnReintentar, btnScreenShoot;
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
        btnScreenShoot= findViewById(R.id.btnScreenShoot);


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
        } else {
            // Si la puntuación final no es mayor, mostrar "Record no superado"
            txtRecord.setText("Record no superado");
        }
        controlador.anadirDatosCalendario();



        // Botón "Volver a Jugar"
        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final.this, Jugador.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });

        btnScreenShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {
                    // image naming and path  to include sd card  appending name you choose for file

                    // create bitmap screen capture
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);

                    File imageFile =  File.createTempFile(now.toString(),
                            ".jpg",
                            getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                    imageFile.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                } catch (Throwable e) {
                    // Several error may come out with file handling or DOM
                    e.printStackTrace();
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