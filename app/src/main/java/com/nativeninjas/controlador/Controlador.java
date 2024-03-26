
package com.nativeninjas.controlador;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.nativeninjas.vista.*;
import com.nativeninjas.modelo.Datos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;


public class Controlador {
    private Datos datos;

    //METHODS

    public void registrarUsuario(String id){
        this.datos.registrarUsuario(id);
    }



    public void addDatos(Context context){
        this.datos = new Datos(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarPartida(String nombreJugador, int puntuacionFinal) {
        this.datos.addPartida(nombreJugador,puntuacionFinal);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int obtenerRecord(String idUsuario) {
        return  this.datos.obtenerRecord(idUsuario);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Single<ArrayList<ArrayList<String>>> obtenerRanking() {
        return datos.mostrarRanking();
    }
}
