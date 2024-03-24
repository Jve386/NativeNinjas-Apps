package com.nativeninjas.controlador;

import com.nativeninjas.vista.*;
import com.nativeninjas.modelo.Datos;

public class Controlador {
    private Datos datos;
    private Vista vista;
    private String idJugadorActual;

    //METHODS
    public void registrarUsuario(String id){
        this.datos.registrarUsuario(id);
    }
    public String getRecordActual(){
       return this.datos.getRecordActual(idJugadorActual);

    }
    public String mostrarRanking(){
        return this.datos.mostrarRanking();
    }

    public void addDatos(){
        this.datos = new Datos();

    }
    public void addVista(){
        this.vista = new Splash();

    }

}
