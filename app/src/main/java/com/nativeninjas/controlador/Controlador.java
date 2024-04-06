
package com.nativeninjas.controlador;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.nativeninjas.vista.*;
import com.nativeninjas.modelo.Datos;
import com.nativeninjas.modelo.Partida;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;


public class Controlador {
    private Datos datos;
    private  CoordenatesTracker coordenatesTracker;

    //CONSTRUCTOR
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Controlador(Context context) {
        this.datos = new Datos(context);
        this.coordenatesTracker = new CoordenatesTracker();
        this.coordenatesTracker.InicialicerTracker(context);
    }


    //METHODS

    public void registrarUsuario(String id){
        this.datos.registrarUsuario(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarPartida(String nombreJugador, int puntuacionFinal) {
        this.coordenatesTracker.getLocation();
        String latitud = String.valueOf(this.coordenatesTracker.getLatitude());
        String longitud = String.valueOf(this.coordenatesTracker.getLongitude());
        this.datos.addPartida(nombreJugador,puntuacionFinal, longitud,latitud);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int obtenerRecord(String idUsuario) {
        return  this.datos.obtenerRecord(idUsuario);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Single<List<Partida>> obtenerRanking() {
        return datos.mostrarRanking();
    }
}
