package com.nativeninjas.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rankingJugadores.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_JUGADORES = "jugadores";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONEDAS = "monedas";

    private static final String CREATE_TABLE_JUGADORES = "CREATE TABLE " + TABLE_JUGADORES + "(" +
            COLUMN_ID + " TEXT PRIMARY KEY," +
            COLUMN_MONEDAS + " INTEGER" +
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

    public void insertarJugador(String nombreJugador, int puntuacionFinal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, nombreJugador);
        values.put(COLUMN_MONEDAS, puntuacionFinal);
        db.insert(TABLE_JUGADORES, null, values);
        db.close();
    }

    public Single<List<Usuario>> obtenerRanking() {
        return Single.fromCallable(() -> {
            List<Usuario> ranking = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_JUGADORES + " ORDER BY " + COLUMN_MONEDAS + " DESC";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Usuario jugador = new Usuario();
                    jugador.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                    jugador.setMoney(cursor.getInt(cursor.getColumnIndex(COLUMN_MONEDAS)));
                    ranking.add(jugador);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return ranking;
        });
    }

    // PDTE REVISAR LOGICA PARA QUE MUESTRE
    // LA PUNTUACION MAS ALTA EXCLUYENDO LA PUNTUACION ACTUAL
    public int obtenerPuntuacionMasAlta() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_JUGADORES + " ORDER BY " + COLUMN_MONEDAS + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int puntuacionMasAlta = 0;
        if (cursor.moveToFirst()) {
            puntuacionMasAlta = cursor.getInt(cursor.getColumnIndex(COLUMN_MONEDAS));
        }
        cursor.close();
        return puntuacionMasAlta;
    }


    public int obtenerPuntuacionMasAltaExcluyendo(int puntuacionFinal) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT MAX(" + COLUMN_MONEDAS + ") FROM " + TABLE_JUGADORES +
                " WHERE " + COLUMN_MONEDAS + " < " + puntuacionFinal;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int puntuacionMasAlta = 0;
        if (cursor.moveToFirst()) {
            puntuacionMasAlta = cursor.getInt(0);
        }
        cursor.close();
        return puntuacionMasAlta;
    }
}