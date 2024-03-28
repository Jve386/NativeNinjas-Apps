package com.nativeninjas.vista;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.controlador.Controlador;
import com.nativeninjas.prod1.R;

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


        // Recuperar la puntuación final de la actividad anterior
        int puntuacionFinal = getIntent().getIntExtra("puntuacionFinal", 0);
        txtPuntuacionFinal.setText("Puntuación Final: " + puntuacionFinal);

        // Obtener la puntuación más alta de la base de datos
        controlador = new Controlador();
        controlador.addDatos(this);
        idUsuario = getIntent().getStringExtra("nombreJugador");
        System.out.println(idUsuario);
        int puntuacionMasAlta = controlador.obtenerRecord(idUsuario);
        txtPuntuacionMasAlta.setText("Puntuación más alta: " + puntuacionMasAlta);

        // Comparar la puntuación final con la puntuación más alta
        if (puntuacionFinal > puntuacionMasAlta) {
            // Si la puntuación final es mayor, mostrar "Record superado"
            txtRecord.setText("Record superado");
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
}