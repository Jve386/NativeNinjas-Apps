package com.nativeninjas.modelo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.nativeninjas.modelo.Partida;
import com.nativeninjas.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
@RequiresApi(api = Build.VERSION_CODES.O)
public class PartidaDAO extends SQLiteOpenHelper implements DAO<Partida, String> {

    private static final String TABLE_PARTIDA = "partida";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USUARIO = "usuario_id";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_MONEDAS = "monedas";
    private static final String CREATE_TABLE_PARTIDAS = "CREATE TABLE " + TABLE_PARTIDA + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USUARIO + " TEXT," +
            COLUMN_FECHA + " TEXT," +
            COLUMN_MONEDAS + " INTEGER," +
            "FOREIGN KEY (" + COLUMN_USUARIO + ") REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE" +
            ")";


    public PartidaDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PARTIDAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIDA);
        onCreate(db);
    }

    @Override
    public void insertar(Partida partida) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO, partida.getUsuarioId());
        values.put(COLUMN_MONEDAS, partida.getMonedas());
        values.put(COLUMN_FECHA, partida.getFecha());
        db.insert(TABLE_PARTIDA, null, values);
        db.close();
    }

    @Override
    public void modificar(Partida partida) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEDAS, partida.getMonedas());
        db.update(TABLE_PARTIDA, values, COLUMN_ID + "=?", new String[]{Integer.toString(partida.getId())});
        db.close();


    }

    @Override
    public void eliminar(String id) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PARTIDA, COLUMN_ID + "=?", new String[]{id});
        db.close();


    }

    @Override
    public List<Partida> listarTodos() throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Partida> array_list = new ArrayList<Partida>();
        Cursor res = db.rawQuery("select * from " + TABLE_PARTIDA, null);
        res.moveToFirst();
        int id;
        String idUsuario;
        int monedas;
        String fecha;
        Partida partida;
        while (!res.isAfterLast()) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            idUsuario = res.getString(res.getColumnIndex(COLUMN_USUARIO));
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
            fecha = res.getString(res.getColumnIndex(COLUMN_FECHA));
            partida = new Partida(monedas, idUsuario);
            partida.setId(id);
            partida.setFecha(fecha);
            array_list.add(partida);
            res.moveToNext();
        }
        return array_list;
    }



    public Partida listarUno(int id) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_PARTIDA + " WHERE " + COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        res.moveToFirst();
        String idUsuario;
        int monedas;
        String fecha;
        Partida partida = null;
        while (!res.isAfterLast()) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            idUsuario = res.getString(res.getColumnIndex(COLUMN_USUARIO));
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
            fecha = res.getString(res.getColumnIndex(COLUMN_FECHA));
            partida = new Partida(monedas, idUsuario);
            partida.setId(id);
            partida.setFecha(fecha);
            res.moveToNext();
        }
        return partida;
    }

    public List<Partida> obtenerRanking() {
        List<Partida> ranking = new ArrayList<>();
        String selectQuery = "SELECT " + TABLE_PARTIDA + "." + COLUMN_ID + ", " +
                TABLE_PARTIDA + "." + COLUMN_USUARIO + " AS partida_usuario_id, " +
                TABLE_PARTIDA + "." + COLUMN_MONEDAS + ", " +
                TABLE_PARTIDA + "." + COLUMN_FECHA +
                " FROM " + TABLE_PARTIDA +
                " JOIN ( SELECT " + COLUMN_USUARIO + " AS usuario_id, MAX(" + COLUMN_MONEDAS + ") AS max_monedas " +
                "       FROM " + TABLE_PARTIDA + " GROUP BY " + COLUMN_USUARIO +
                "       ) AS aux " +
                "ON " + TABLE_PARTIDA + "." + COLUMN_USUARIO + " = aux.usuario_id AND " +
                TABLE_PARTIDA + "." + COLUMN_MONEDAS +
                " = aux.max_monedas " +
                "ORDER BY max_monedas DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();
        int id;
        String idUsuario;
        int monedas;
        String fecha;
        Partida partida;
        while (!res.isAfterLast()) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            idUsuario = res.getString(res.getColumnIndex("partida_usuario_id")); // Usar el alias de la columna
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
            fecha = res.getString(res.getColumnIndex(COLUMN_FECHA));
            partida = new Partida(monedas, idUsuario);
            partida.setId(id);
            partida.setFecha(fecha);
            ranking.add(partida);
            res.moveToNext();
        }
        res.close();
        db.close();
        return ranking;
    }
    public int obtenerMaximaPuntuacion(String idUsuario) {
        String selectQuery = "SELECT MAX("+COLUMN_MONEDAS+") AS "+COLUMN_MONEDAS+
                " FROM "+TABLE_PARTIDA +
                " WHERE "+ COLUMN_USUARIO +" = "+idUsuario+
                " GROUP BY "+ COLUMN_MONEDAS +" ORDER BY " + COLUMN_MONEDAS +" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();
        int monedas = -1;
        if(!res.isAfterLast()) {
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
        }
        res.close();
        db.close();
        return monedas;
    }

}