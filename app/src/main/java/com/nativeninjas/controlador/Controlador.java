
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
    //CONSTRUCTOR

    public Controlador() {

    }


    //METHODS

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addDatos(Context context){
        this.datos = new Datos(context);

    }


    public void registrarUsuario(String id){
        this.datos.registrarUsuario(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarPartida(String nombreJugador, int puntuacionFinal, double latitude, double longitude) {
        this.datos.addPartida(nombreJugador,puntuacionFinal, longitude,latitude);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int obtenerRecord() {
        return this.datos.obtenerRecord(); // Llama al método en la capa de datos correspondiente sin pasar ningún argumento
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Single<List<Partida>> obtenerRanking() {
        return datos.mostrarRanking();
    }
}
