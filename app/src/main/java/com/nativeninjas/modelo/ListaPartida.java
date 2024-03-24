<<<<<<< HEAD
package com.nativeninjas.modelo;
=======
package com.example.piedrapapeltijera.modelo;
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f

import java.util.ArrayList;

public class ListaPartida {
    private ArrayList<Partida> partidas;

    //BUILDER

    public ListaPartida() {
        this.partidas = new ArrayList<>();
    }
    public Partida getPartida(String idPartida){
        for(Partida partida: partidas) {
            if (partida.getId().equals(idPartida)) {
                return partida;
            }

        }
        return null;
    }

<<<<<<< HEAD
    public void addPartida(String idPartida, int monedas, date fecha){
        Partida partida = new Partida(idPartida, monedas, fecha);
=======
    public void addPartida(String idPartida, int monedas){
        Partida partida = new Partida(idPartida, monedas);
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
        this.partidas.add(partida);
    }
    @Override
    public String toString() {
        return "ListaPartida{" +
                "partidas=" + partidas +
                '}';
    }

}
