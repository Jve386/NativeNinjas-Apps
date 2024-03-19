package com.nativeninjas.prod1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Final extends AppCompatActivity {

    private TextView txtPuntuacionFinal;
    private Button btnMain, btnSalir, btnReintentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        txtPuntuacionFinal = findViewById(R.id.txtPuntuacionFinal);

        btnMain = findViewById(R.id.btnMain);
        btnSalir = findViewById(R.id.btnSalir);
        btnReintentar = findViewById(R.id.btnReintentar);



        // Recuperar la puntuación final de la actividad anterior
        int puntuacionFinal = getIntent().getIntExtra("puntuacionFinal", 0);
        txtPuntuacionFinal.setText("Puntuación Final: " + puntuacionFinal);
        // Configurar el onClickListener para el botón "Volver a Jugar"
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