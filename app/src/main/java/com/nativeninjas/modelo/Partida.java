<<<<<<< HEAD
package com.nativeninjas.modelo;

=======
package com.example.piedrapapeltijera.modelo;
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
import java.date;
public class Partida {

    private LocalDate fecha;
    private String id;
    //private DateTime fecha;// (BUSCAR FECHAS EN ANDROID)
    private int monedas;

    //BUILDER
    public Partida(String id, int monedas, LocalDate fecha) {
        this.id = id;
        this.monedas = monedas;
        //this.fecha = new LocalDateTime();// (BUSCAR LOCALDATETIME() ANDRROID)
    }

    //GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id='" + id + '\'' +
                ", monedas=" + monedas +
                '}';
    }
}
