<<<<<<< HEAD
package com.nativeninjas.modelo;
import com.nativeninjas.modelo.DAO.UsuarioDAO;
import com.nativeninjas.modelo.DAO.PartidaDAO;
import com.nativeninjas.modelo.DAO.DAOfactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class Datos {

        private UsuarioDAO usuarioDAO;
        private DAOfactory daoFactory = new DAOfactory();
        private PartidaDAO partidaDAO;

    //BUILDER


    public Datos() {
        this.usuarioDAO = this.daoFactory.getUsarioDAO();
        this.partidaDAO = this.daoFactory.getPartidaDAO();
=======
package com.example.piedrapapeltijera.modelo;

public class Datos {
    private ListaUsuarios usuarios;
    private ListaPartida partidas;

    //BUILDER

    public Datos() {
        this.usuarios = new ListaUsuarios();
        this.partidas = new ListaPartida();
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
    }


    //METHODS

    public void registrarUsuario(String id){
<<<<<<< HEAD
        Usuario usuario = new Usuario();
        usuario.setId(id);
        this.usuarioDAO.insertar(usuario);
    }

    public String mostrarUsuario(String id) {
        Usuario usuario;
        usuario = this.usuarioDAO.listarUno(id);
        return usuario.toString();
    }
    /**
=======
        this.usuarios.addUsuario(id);
    }

    public String mostrarUsuario(String id){
        return this.usuarios.getUsuario(id).toString();

    }
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
    public String mostrarPartida(String id){
        return this.partidas.getPartida(id).toString();
    }

    public void addPartida(String id, int monedas){
         this.partidas.addPartida(id, monedas);
    }
<<<<<<< HEAD
     **/
    public Single<ArrayList<List<String>>> mostrarRanking(){
        return Single.fromCallable(() -> {
            ArrayList<List<String>> ranking = new ArrayList<>();
            List<Usuario> listUsuarios = this.usuarioDAO.obtenerRanking();
            String id;
            String monedas;

            for(Usuario u: listUsuarios){
                id = u.getId();
                monedas = Integer.toString(u.getMoney());

                ranking.add(new ArrayList<>(Arrays.asList(id,monedas)));

            }
            return ranking;
        });
    }
    /**
=======
    public String mostrarRanking(){
        return this.usuarios.mostrarRanking();
    }
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
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
<<<<<<< HEAD
     **/
=======
>>>>>>> 624b1fe91fddbaf159ccc3246799c4a384cbad6f
}
