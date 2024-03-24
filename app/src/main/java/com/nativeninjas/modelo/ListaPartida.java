
package com.nativeninjas.modelo;


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

    public void addPartida(String idPartida, int monedas, date fecha){
        Partida partida = new Partida(idPartida, monedas, fecha);

        this.partidas.add(partida);
    }
    @Override
    public String toString() {
        return "ListaPartida{" +
                "partidas=" + partidas +
                '}';
    }

}
