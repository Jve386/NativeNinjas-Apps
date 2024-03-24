<<<<<<< HEAD
package com.nativeninjas.controlador;

import com.nativeninjas.vista.*;
import com.nativeninjas.modelo.Datos;
=======
package com.example.piedrapapeltijera.controlador;

import com.example.piedrapapeltijera.Vistas.Vista;
import com.example.piedrapapeltijera.Vistas.VistaPrincipal;
import com.example.piedrapapeltijera.modelo.Datos;
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f

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
<<<<<<< HEAD
        this.vista = new Splash();
=======
        this.vista = new VistaPrincipal();
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f

    }

}
