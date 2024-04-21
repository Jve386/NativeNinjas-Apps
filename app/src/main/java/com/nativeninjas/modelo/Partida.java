
package com.nativeninjas.modelo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Partida {

    private LocalDateTime fecha;
    private int id;
    //private DateTime fecha;// (BUSCAR FECHAS EN ANDROID)
    private int monedas;

    private String usuarioId;

    private final DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private double latitud;
    private double longitud;

    //BUILDER
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Partida(int monedas, String usuarioId, double latitud, double longitud) {
        this.monedas = monedas;
        this.fecha = LocalDateTime.now();
        this.usuarioId = usuarioId;
        this.latitud =  latitud;
        this.longitud = longitud;
    }

    //GETTERS & SETTERS
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public String getFecha() {
        return this.fecha.format(myFormatObj);
    }

    public void setFecha(String fecha) {
        this.fecha = LocalDateTime.parse(fecha,myFormatObj);
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id='" + id + '\'' +
                ", monedas=" + monedas +
                '}';
    }
}
