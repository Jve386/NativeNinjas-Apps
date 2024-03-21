package com.example.piedrapapeltijera.controlador;

import com.example.piedrapapeltijera.Vistas.Vista;
import com.example.piedrapapeltijera.Vistas.VistaPrincipal;
import com.example.piedrapapeltijera.modelo.Datos;

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
        this.vista = new VistaPrincipal();

    }

}
