
package com.nativeninjas.modelo;
import android.os.Build;

import androidx.annotation.RequiresApi;
import android.content.Context;

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Datos(Context context) {
        this.usuarioDAO = this.daoFactory.getUsarioDAO(context);
        this.partidaDAO = this.daoFactory.getPartidaDAO(context);

    }


    //METHODS

    public void registrarUsuario(String id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        this.usuarioDAO.insertar(usuario);
    }

    public String mostrarUsuario(String id) {
        Usuario usuario;
        usuario = this.usuarioDAO.listarUno(id);
        return usuario.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addPartida(String usuarioId, int monedas){
        Partida partida = new Partida(monedas, usuarioId);
         this.partidaDAO.insertar(partida);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Single<ArrayList<List<String>>> mostrarRanking(){
        return Single.fromCallable(() -> {
            ArrayList<List<String>> ranking = new ArrayList<>();
            List<Partida> listPartida = this.partidaDAO.obtenerRanking();
            String idUsuario;
            String monedas;
            String fecha;
            for(Partida p: listPartida){
                idUsuario = p.getUsuarioId();
                monedas = Integer.toString(p.getMonedas());
                fecha = p.getFecha();

                ranking.add(new ArrayList<>(Arrays.asList(idUsuario,monedas, fecha)));

            }
            return ranking;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int obtenerRecord(String idUsuario) {
        return this.partidaDAO.obtenerMaximaPuntuacion(idUsuario);
    }

}
