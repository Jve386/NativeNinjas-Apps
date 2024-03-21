package com.example.piedrapapeltijera.modelo;

public class Datos {
    private ListaUsuarios usuarios;
    private ListaPartida partidas;

    //BUILDER

    public Datos() {
        this.usuarios = new ListaUsuarios();
        this.partidas = new ListaPartida();
    }


    //METHODS

    public void registrarUsuario(String id){
        this.usuarios.addUsuario(id);
    }

    public String mostrarUsuario(String id){
        return this.usuarios.getUsuario(id).toString();

    }
    public String mostrarPartida(String id){
        return this.partidas.getPartida(id).toString();
    }

    public void addPartida(String id, int monedas){
         this.partidas.addPartida(id, monedas);
    }
    public String mostrarRanking(){
        return this.usuarios.mostrarRanking();
    }
    public void updateMonedas(String id, int monedas){
        this.usuarios.updateMonedas(id, monedas);
    }
    public String mostrarHistorico(){
        return this.partidas.toString();
    }
    public String mostrarUsuarios(){
        return this.usuarios.toString();
    }
    public String getRecordActual(String id){
        return this.usuarios.getRecordActual(id);
    }
}
