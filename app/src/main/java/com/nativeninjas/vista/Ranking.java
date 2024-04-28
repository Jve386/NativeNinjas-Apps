package com.nativeninjas.vista;

import com.nativeninjas.prod1.R;
import com.nativeninjas.modelo.Partida;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.controlador.Controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;




public class Ranking extends AppCompatActivity {

    private ListView listViewRanking;
    private ArrayAdapter<String> adapter;
    private Button btnVolver;
    private Disposable disposable; // Variable para almacenar la suscripción

    private Controlador controlador;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Configuration config = getResources().getConfiguration();
        Locale locale = config.locale;
        String codigo = locale.getLanguage();

        if (codigo.equals("es")) getSupportActionBar().setTitle("Piedra, papel y tijera");
        else if (codigo.equals("en")) getSupportActionBar().setTitle("Rock, paper and scissors");
        else if (codigo.equals("ca")) getSupportActionBar().setTitle("Pedra, paper i tisora");

        btnVolver = findViewById(R.id.btnVolver);
        listViewRanking = findViewById(R.id.listViewRanking);

        // Obtener el ranking de la base de datos usando RxJava

        //**
        controlador = new Controlador();
        controlador.addDatos(this);
        disposable = Single.fromCallable(() -> controlador.obtenerRanking())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::mostrarRanking, Throwable::printStackTrace);
        //**

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


    //**
    private void mostrarRanking(Single<List<Partida>> rankingSingle) {
        disposable = rankingSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::mostrarListaRanking, Throwable::printStackTrace);
    }
    //**

    //**
    private void mostrarListaRanking(List<Partida> ranking) {
        // Ordenar la lista de partidas por puntuación de forma descendente
        //Collections.sort(ranking, (p1, p2) -> Integer.compare(p2.getMonedas(), p1.getMonedas()));

        // Limitar la lista a las 10 mejores partidas si es necesario
        List<Partida> sublist = ranking.subList(0, Math.min(ranking.size(), 10));

        // Crear un adaptador personalizado para la lista
        ArrayAdapter<Partida> adapter = new ArrayAdapter<Partida>(this, android.R.layout.simple_list_item_2, sublist) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                }

                // Obtener la partida actual
                Partida partida = getItem(position);

                // Configurar el texto para el nombre y la puntuación
                TextView textViewNombre = view.findViewById(android.R.id.text1);
                textViewNombre.setText((position + 1) + ". " + partida.getUsuarioId() + " - Puntuación: " + partida.getMonedas());

                // Configurar la fecha debajo del texto principal con un tamaño de letra más pequeño
                TextView textViewFecha = view.findViewById(android.R.id.text2);
                textViewFecha.setText("Fecha: " + partida.getFecha() + " Latitud: " + String.valueOf(partida.getLatitud()) + " Longitud: " + String.valueOf(partida.getLongitud()));
                textViewFecha.setTextSize(12);

                return view;
            }
        };

        // Establecer el adaptador en el ListView
        listViewRanking.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar los recursos cuando la actividad se destruya para evitar memory leaks
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Ranking.this);
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