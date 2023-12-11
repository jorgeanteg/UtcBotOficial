package com.example.utcbot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "formulario.db";
    private static final int DATABASE_VERSION = 1;

    // Definir la estructura de la tabla para almacenar los datos del formulario
    static final String TABLE_NAME = "formulario_table";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NOMBRE = "nombre";
    static final String COLUMN_CONTENIDO = "contenido";

    // Consulta SQL para crear la tabla
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_CONTENIDO + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Puedes implementar una lógica de actualización de la base de datos si es necesario
    }
}