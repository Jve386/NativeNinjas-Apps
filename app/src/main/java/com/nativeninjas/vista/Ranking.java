package com.nativeninjas.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nativeninjas.modelo.DatabaseHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Ranking extends AppCompatActivity {

    private ListView listViewRanking;
    private ArrayAdapter<String> adapter;
    private Button btnVolver;
    private Disposable disposable; // Variable para almacenar la suscripción

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        btnVolver = findViewById(R.id.btnVolver);
        listViewRanking = findViewById(R.id.listViewRanking);

        // Obtener el ranking de la base de datos usando RxJava

        //**
        disposable = Single.fromCallable(() -> new DatabaseHelper(this).obtenerRanking())
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
    private void mostrarRanking(Single<List<Usuario>> rankingSingle) {
        disposable = rankingSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::mostrarListaRanking, Throwable::printStackTrace);
    }
    //**

    //**
    private void mostrarListaRanking(List<Usuario> ranking) {
        // Limitar la lista a los 10 primeros jugadores
        ranking = ranking.subList(0, Math.min(ranking.size(), 10));

        // Crear un adaptador personalizado para la lista
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_2, ranking) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                }

                // Obtener el jugador actual
                Usuario jugador = getItem(position);

                // Configurar el texto para el nombre y la puntuación
                TextView textViewNombre = view.findViewById(android.R.id.text1);
                textViewNombre.setText((position + 1) + ". " + jugador.getNombre() + " - Puntuación: " + jugador.getPuntuacion());

                // Configurar la fecha debajo del texto principal con un tamaño de letra más pequeño
                TextView textViewFecha = view.findViewById(android.R.id.text2);
                textViewFecha.setText("Fecha: " + jugador.getFecha());
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
}