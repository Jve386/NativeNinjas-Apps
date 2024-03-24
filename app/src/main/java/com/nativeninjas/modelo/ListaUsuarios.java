<<<<<<< HEAD
package com.nativeninjas.modelo;
=======
package com.example.piedrapapeltijera.modelo;
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f

import java.util.ArrayList;
import java.util.Comparator;

public class ListaUsuarios {
    private ArrayList<Usuario> usuarios;

    //BUILDER
    public ListaUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public void addUsuario(String idUsuario){
<<<<<<< HEAD
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setMoney(0);
=======
        Usuario usuario = new Usuario(idUsuario);
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
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

