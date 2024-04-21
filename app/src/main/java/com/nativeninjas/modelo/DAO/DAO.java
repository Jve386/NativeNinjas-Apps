package com.nativeninjas.modelo.DAO;

import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la capa de persistencia.
 *
 * @param <T> Tipo de objeto
 * @param <K> Tipo de clave
 */
public interface DAO <T, K> {

    static final String DATABASE_NAME = "PiedraPapelTijeras.db";
    static final int DATABASE_VERSION = 3;

    /**
     * Inserta un objeto en la capa de persistencia.
     *
     * @param t Objeto a insertar
     * @throws SQLiteException si ocurre un error de SQL
     */
    void insertar(T t) throws  SQLiteException;

    /**
     * Modifica un objeto en la capa de persistencia.
     *
     * @param t Objeto a modificar
     * @throws SQLiteException si ocurre un error de SQL
     */
    void modificar(T t) throws  SQLiteException;

    /**
     * Elimina un objeto de la capa de persistencia basado en su clave.
     *
     * @param k key del objeto a eliminar
     * @throws SQLiteException si ocurre un error de SQL
     */
    void eliminar(K k) throws  SQLiteException;

    /**
     * Obtiene una lista de todos los objetos de la capa de persistencia.
     *
     * @return Lista de objetos
     * @throws SQLiteException si ocurre un error de SQL
     */
    List<T> listarTodos() throws SQLiteException;



}

