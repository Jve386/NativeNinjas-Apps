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
    private static final String COLUMN_LATITUD = "latitud";
    private static final String COLUMN_LONGITUD = "longitud";
    private static final String CREATE_TABLE_PARTIDAS = "CREATE TABLE " + TABLE_PARTIDA + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USUARIO + " TEXT," +
            COLUMN_FECHA + " TEXT," +
            COLUMN_MONEDAS + " INTEGER," +
            COLUMN_LATITUD + " REAL," +
            COLUMN_LONGITUD + " REAL," +
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
        values.put(COLUMN_LATITUD, partida.getLatitud());
        values.put(COLUMN_LONGITUD, partida.getLongitud());
        db.insert(TABLE_PARTIDA, null, values);
        db.close();
    }

    @Override
    public void modificar(Partida partida) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEDAS, partida.getMonedas());
        values.put(COLUMN_LATITUD, partida.getLatitud());
        values.put(COLUMN_LONGITUD, partida.getLongitud());
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
        double latitud;
        double longitud;
        Partida partida;
        while (!res.isAfterLast()) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            idUsuario = res.getString(res.getColumnIndex(COLUMN_USUARIO));
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
            fecha = res.getString(res.getColumnIndex(COLUMN_FECHA));
            latitud = res.getDouble(res.getColumnIndex(COLUMN_LATITUD));
            longitud = res.getDouble(res.getColumnIndex(COLUMN_LONGITUD));
            partida = new Partida(monedas, idUsuario, latitud, longitud);
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
        double latitud;
        double longitud;
        Partida partida = null;
        while (!res.isAfterLast()) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            idUsuario = res.getString(res.getColumnIndex(COLUMN_USUARIO));
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
            fecha = res.getString(res.getColumnIndex(COLUMN_FECHA));
            latitud = res.getDouble(res.getColumnIndex(COLUMN_LATITUD));
            longitud = res.getDouble(res.getColumnIndex(COLUMN_LONGITUD));
            partida = new Partida(monedas, idUsuario, latitud, longitud);
            partida.setId(id);
            partida.setFecha(fecha);
            res.moveToNext();
        }
        return partida;
    }

    public List<Partida> obtenerRanking() {
        List<Partida> ranking = new ArrayList<>();
        String selectQuery = "SELECT "+COLUMN_ID+", "+COLUMN_USUARIO+", "+COLUMN_MONEDAS+", "+COLUMN_FECHA+
                ", "+COLUMN_LATITUD+", "+COLUMN_LONGITUD+" FROM "+TABLE_PARTIDA +
                " JOIN ( SELECT "+COLUMN_USUARIO+" AS aux_usuario, MAX("+COLUMN_MONEDAS+") AS max_monedas " +
                "       FROM "+TABLE_PARTIDA+" GROUP BY "+COLUMN_USUARIO +
                "       ) AS aux " +
                " ON "+COLUMN_USUARIO+" = aux.aux_usuario AND "+COLUMN_MONEDAS+
                " = aux.max_monedas " +
                " ORDER BY "+COLUMN_MONEDAS+" DESC, "+ COLUMN_FECHA + " ASC";
        /**
         String selectQuery = "SELECT * FROM ( SELECT " + COLUMN_ID + ", " +
         COLUMN_USUARIO + ", "+  COLUMN_MONEDAS + ", " + COLUMN_FECHA +
         " , ROW_NUMBER () OVER (PARTITION BY "+ COLUMN_USUARIO +" ORDER BY "+ COLUMN_MONEDAS + " DESC) RowNum" +
         " FROM " + TABLE_PARTIDA +
         " ) t"+
         " WHERE RowNum=1 ORDER BY "+COLUMN_MONEDAS+" DESC";
         **/
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();
        int id;
        String idUsuario;
        int monedas;
        String fecha;
        Partida partida;
        double latitud;
        double longitud;
        if(res.moveToFirst()) {
            do{
                id = res.getInt(res.getColumnIndex(COLUMN_ID));
                idUsuario = res.getString(res.getColumnIndex(COLUMN_USUARIO));
                monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
                fecha = res.getString(res.getColumnIndex(COLUMN_FECHA));
                latitud = res.getDouble(res.getColumnIndex(COLUMN_LATITUD));
                longitud = res.getDouble(res.getColumnIndex(COLUMN_LONGITUD));
                partida = new Partida(monedas, idUsuario, latitud, longitud);
                partida.setId(id);
                partida.setFecha(fecha);
                ranking.add(partida);
                res.moveToNext();
            }while (!res.isAfterLast());
        }
        res.close();
        db.close();
        return ranking;
    }
    public int obtenerMaximaPuntuacion() {
        String selectQuery = "SELECT "+COLUMN_MONEDAS+
                " FROM "+TABLE_PARTIDA +
                " ORDER BY " + COLUMN_MONEDAS +" DESC"+
                " LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        int monedas = 0;
        if(res.moveToFirst()) {
            monedas = res.getInt(res.getColumnIndex(COLUMN_MONEDAS));
        }
        res.close();
        db.close();
        return monedas;
    }

}