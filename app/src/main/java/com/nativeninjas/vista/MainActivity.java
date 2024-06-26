package com.nativeninjas.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nativeninjas.MyApplication;
import com.nativeninjas.data.model.QuoteModel;
import com.nativeninjas.prod1.R;
import com.nativeninjas.prod1.databinding.ActivityMainBinding;
import com.nativeninjas.vista.viewmodel.QuoteViewModel;

import java.util.Locale;
import android.content.Context;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class MainActivity extends AppCompatActivity {
    private Button btnJugar, btnSalir, btnRanking, btnAjustes, btnUbicacion;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView tvQuote;
    private QuoteViewModel quoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Configuration config = getResources().getConfiguration();
        Locale locale = config.locale;
        String codigo = locale.getLanguage();

        if (codigo.equals("es")) getSupportActionBar().setTitle("Piedra, papel y tijera");
        else if (codigo.equals("en")) getSupportActionBar().setTitle("Rock, paper and scissors");
        else if (codigo.equals("ca")) getSupportActionBar().setTitle("Pedra, paper i tisora");

        btnJugar = findViewById(R.id.btnJugar);
        btnSalir = findViewById(R.id.btnSalir);
        btnRanking = findViewById(R.id.btnRanking);
        btnAjustes = findViewById(R.id.btnAjustes);
        btnUbicacion = findViewById(R.id.btnUbicacion);

        tvQuote = findViewById(R.id.tvQuote);

        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);
        quoteViewModel.onCreate();

        quoteViewModel.getQuoteModel().observe(this, new Observer<QuoteModel>() {
            @Override
            public void onChanged(QuoteModel quoteModel) {
                tvQuote.setText(quoteModel.getQuote());
                Log.d("MainActivity", "Quote updated: " + quoteModel.getQuote());
            }
        });

        tvQuote.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Log.d("MainActivity", "TextView clicked");
                quoteViewModel.randomQuote();
            }
        });


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

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de estadísticas
                Intent intent = new Intent(MainActivity.this, UbicacionActivity.class);
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
                // Salir de la aplicación y desloguear
                signOut();
            }
        });
        builder.show();
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            // Mostrar Toast después de desloguearse
            Toast.makeText(MainActivity.this, "Deslogueado de Google", Toast.LENGTH_SHORT).show();
            // Salir de la aplicación
            finishAffinity();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Esta es la app del clásico juego Piedra, Papel, Tijera, desarrollada por el equipo Native Ninjas");
            builder.show();
            return true;
        } else if (id == R.id.exit) {
            mostrarDialogoConfirmacion();
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
