package com.nativeninjas.modelo.DAO;
import android.content.Context;

public class DAOfactory {
    public UsuarioDAO getUsarioDAO(){
        return new UsuarioDAO();

    }
    public PartidaDAO getPartidaDAO(){
        return new PartidaDAO();

    }
}
