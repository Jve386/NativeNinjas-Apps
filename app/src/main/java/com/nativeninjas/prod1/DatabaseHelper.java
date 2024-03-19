package com.nativeninjas.prod1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rankingJugadores.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_JUGADORES = "jugadores";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PUNTUACION = "puntuacion";

    private static final String CREATE_TABLE_JUGADORES = "CREATE TABLE " + TABLE_JUGADORES + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT," +
            COLUMN_PUNTUACION + " INTEGER" +
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

    public long insertarJugador(String nombre, int puntuacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_PUNTUACION, puntuacion);
        long id = db.insert(TABLE_JUGADORES, null, values);
        db.close();
        return id;
    }
}