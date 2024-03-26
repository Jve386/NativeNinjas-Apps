package com.nativeninjas.modelo.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.nativeninjas.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;


public class UsuarioDAO extends SQLiteOpenHelper implements DAO <Usuario, String >  {
    private static final String TABLE_JUGADORES = "usuario";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MONEDAS = "monedas";
    private static final String CREATE_TABLE_JUGADORES = "CREATE TABLE " + TABLE_JUGADORES + "(" +
            COLUMN_ID + " TEXT PRIMARY KEY," +
            COLUMN_MONEDAS + " INTEGER" +
            ")";
    public UsuarioDAO() {
        super(null, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_JUGADORES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUGADORES);
        onCreate(db);
    }

    @Override
    public void insertar(Usuario usuario) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, usuario.getId());
        values.put(COLUMN_MONEDAS, usuario.getMoney());
        db.insert(TABLE_JUGADORES, null, values);
        db.close();
    }

    @Override
    public void modificar(Usuario usuario) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEDAS, usuario.getMoney());
        db.update(TABLE_JUGADORES, values, COLUMN_ID + "=?", new String[]{usuario.getId()});
        db.close();


    }

    @Override
    public void eliminar(String id) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JUGADORES,COLUMN_ID + "=?", new String[]{id});
        db.close();


    }

    @Override
    public List<Usuario> listarTodos() throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Usuario> array_list = new ArrayList<Usuario>();
        Cursor res = db.rawQuery( "select * from "+ TABLE_JUGADORES, null );
        res.moveToFirst();
        String id;
        int monedas;
        Usuario usuario;
        while(!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(COLUMN_ID));
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
            usuario = new Usuario();
            usuario.setId(id);
            usuario.setMoney(monedas);
            array_list.add(usuario);
            res.moveToNext();
        }
        return array_list;
    }


    @Override
    public Usuario listarUno(String id) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from "+ TABLE_JUGADORES + " WHERE " + COLUMN_ID + "=?", new String[]{id} );
        res.moveToFirst();
        int monedas;
        Usuario usuario = null;
        while(!res.isAfterLast()) {
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
            usuario = new Usuario();
            usuario.setId(id);
            usuario.setMoney(monedas);
            res.moveToNext();
        }
        return usuario;
    }
    public List<Usuario> obtenerRanking() {
            List<Usuario> ranking = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_JUGADORES + " ORDER BY " + COLUMN_MONEDAS + " DESC";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                Usuario jugador;
                do {
                    jugador = new Usuario();
                    jugador.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                    jugador.setMoney(cursor.getInt(cursor.getColumnIndex(COLUMN_MONEDAS)));
                    ranking.add(jugador);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return ranking;
    }

}
