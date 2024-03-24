package com.nativeninjas.modelo;

import java.util.ArrayList;
import java.util.Comparator;

public class ListaUsuarios {
    private ArrayList<Usuario> usuarios;

    //BUILDER
    public ListaUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public void addUsuario(String idUsuario){
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setMoney(0);
        this.usuarios.add(usuario);
    }

    public String mostrarRanking(){
        usuarios.sort((Comparator.comparingInt(Usuario::getMoney)));
        return usuarios.toString();
    }
    public Usuario getUsuario(String id) {
        for(Usuario usuario: usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }

        }
        return null;
    }

    public void updateMonedas(String id, int monedas){
        for(Usuario usuario: usuarios) {
            if (usuario.getId().equals(id)) {
                usuario.setMoney(monedas);
            }

        }
    }
    public String getRecordActual(String id){
        String money = String.valueOf(getUsuario(id).getMoney());
        return money;
    }

    @Override
    public String toString() {
        return "ListaUsuarios{" +
                "usuarios=" + usuarios +
                '}';
    }


}

