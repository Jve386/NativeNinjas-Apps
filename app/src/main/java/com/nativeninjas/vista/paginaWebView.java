package com.nativeninjas.vista;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nativeninjas.MyApplication;
import com.nativeninjas.prod1.R;

import java.util.Locale;

public class paginaWebView extends AppCompatActivity {

    private WebView webViewHelp;
    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_web_view);

        Configuration config = getResources().getConfiguration();
        Locale locale = config.locale;
        String codigo = locale.getLanguage();

        if (codigo.equals("es")) getSupportActionBar().setTitle("Piedra, papel y tijera");
        else if (codigo.equals("en")) getSupportActionBar().setTitle("Rock, paper and scissors");
        else if (codigo.equals("ca")) getSupportActionBar().setTitle("Pedra, paper i tisora");

        webViewHelp = findViewById(R.id.webview_help);

        // Configurar un WebViewClient para manejar eventos de navegación
        webViewHelp.setWebViewClient(new WebViewClient());

        // Cargar la página de ayuda desde el archivo HTML en el directorio assets
        if (codigo.equals("es")) webViewHelp.loadUrl("file:///android_asset/ayuda.html");
        else if (codigo.equals("en")) webViewHelp.loadUrl("file:///android_asset/help.html");
        else if (codigo.equals("ca")) webViewHelp.loadUrl("file:///android_asset/ajuda.html");

        btnAtras = findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(paginaWebView.this, MainActivity.class);
                startActivity(intent);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(paginaWebView.this);
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