package com.nativeninjas.prod1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Ranking extends AppCompatActivity {

    private ListView listViewRanking;
    private ArrayAdapter<String> adapter;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        btnVolver = findViewById(R.id.btnVolver);

        listViewRanking = findViewById(R.id.listViewRanking);

        // Instanciar el helper de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Obtener el ranking de la base de datos
        List<dbJugador> ranking = dbHelper.obtenerRanking();

        // Limitar la lista a los 10 primeros jugadores
        ranking = ranking.subList(0, Math.min(ranking.size(), 10));

        // Crear una lista de cadenas para mostrar en el ListView
        String[] rankingStrings = new String[ranking.size()];
        for (int i = 0; i < ranking.size(); i++) {
            dbJugador jugador = ranking.get(i);
            rankingStrings[i] = (i + 1) + ". " + jugador.getNombre() + " - Puntuación: " + jugador.getPuntuacion();
        }

        // Crear el adaptador para el ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rankingStrings);

        // Establecer el adaptador en el ListView
        listViewRanking.setAdapter(adapter);

        // Configurar el botón para volver al menú anterior
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ranking.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            }
        });
    }
}
