package com.nativeninjas.prod1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rankingJugadores.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_JUGADORES = "jugadores";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PUNTUACION = "puntuacion";
    public static final String COLUMN_FECHA = "fecha";

    private static final String CREATE_TABLE_JUGADORES = "CREATE TABLE " + TABLE_JUGADORES + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT," +
            COLUMN_PUNTUACION + " INTEGER," +
            COLUMN_FECHA + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public long insertarJugador(String nombre, int puntuacion, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_PUNTUACION, puntuacion);
        values.put(COLUMN_FECHA, fecha);
        long id = db.insert(TABLE_JUGADORES, null, values);
        db.close();
        return id;
    }

    public List<dbJugador> obtenerRanking() {
        List<dbJugador> ranking = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_JUGADORES + " ORDER BY " + COLUMN_PUNTUACION + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                dbJugador jugador = new dbJugador();
                jugador.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                jugador.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)));
                jugador.setPuntuacion(cursor.getInt(cursor.getColumnIndex(COLUMN_PUNTUACION)));
                jugador.setFecha(cursor.getString(cursor.getColumnIndex(COLUMN_FECHA)));
                ranking.add(jugador);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ranking;
    }

    // PDTE REVISAR LOGICA PARA QUE MUESTRE
    // LA PUNTUACION MAS ALTA EXCLUYENDO LA PUNTUACION ACTUAL
    public int obtenerPuntuacionMasAlta() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_JUGADORES + " ORDER BY " + COLUMN_PUNTUACION + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int puntuacionMasAlta = 0;
        if (cursor.moveToFirst()) {
            puntuacionMasAlta = cursor.getInt(cursor.getColumnIndex(COLUMN_PUNTUACION));
        }
        cursor.close();
        return puntuacionMasAlta;
    }


    public int obtenerPuntuacionMasAltaExcluyendo(int puntuacionFinal) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT MAX(" + COLUMN_PUNTUACION + ") FROM " + TABLE_JUGADORES +
                " WHERE " + COLUMN_PUNTUACION + " < " + puntuacionFinal;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int puntuacionMasAlta = 0;
        if (cursor.moveToFirst()) {
            puntuacionMasAlta = cursor.getInt(0);
        }
        cursor.close();
        return puntuacionMasAlta;
    }
}