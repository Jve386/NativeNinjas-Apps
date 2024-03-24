package com.nativeninjas.modelo.DAO;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.nativeninjas.modelo.Partida;

import java.util.List;

public class PartidaDAO implements DAO<Partida, String> {

    @Override
    public void insertar(Partida partida) throws SQLiteException {

    }

    @Override
    public void modificar(Partida partida) throws SQLiteException {

    }

    @Override
    public void eliminar(String s) throws SQLiteException {

    }

    @Override
    public List<Partida> listarTodos() throws SQLiteException {
        return null;
    }

    @Override
    public Partida listarUno(String s) throws SQLiteException {
        return null;
    }

    @Override
    public boolean existe(String s) throws SQLiteException {
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
