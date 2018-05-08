package com.example.osanseviero.proyectofinalmoviles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by osanseviero on 5/8/18.
 */

public class DBAdaptor {

    static final String KEY_ID = "id";
    static final String KEY_TOKEN = "token";

    static final String DB_NAME = "CRM";
    static final String DB_TABLE = "tokens";
    static final int DB_VERSION = 10;

    static final String CREAR_BD =
            "CREATE TABLE " + DB_TABLE +
                    "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    KEY_TOKEN + " VARCHAR (50) NOT NULL);";
    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdaptor(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
        Log.d("DBG", "CONSTRUCTOR DONE");
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("DBG", "DBHELPER onCreate");
            try {
                Log.d("SQLite", "Creating table!");
                Log.d("SQLite", CREAR_BD);
                db.execSQL(CREAR_BD);
            } catch (SQLException e) {
                Log.e("SQLite", e.toString());
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("DBG", "DBHLPER onUpgrade");
            Log.d("SQLite", "Actualizando la version de la Base de Datos de " + oldVersion + " a "
                    + newVersion + ", este proceso eliminará los regustros de la versión anterior");
            db.execSQL("DROP TABLE IF EXISTS tokens");
            onCreate(db);
        }
    }

    /* Open Database*/
    public DBAdaptor open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // Close Database
    public void close()
    {
        DBHelper.close();
    }

    // Insert token to databse
    public long insertToken(String token) {
        db.execSQL("DROP TABLE IF EXISTS tokens");
        db.execSQL(CREAR_BD);
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TOKEN, token);
        return db.insert(DB_TABLE, null, initialValues);
    }

    public Cursor getToken() {
        return db.query(DB_TABLE,
                new String[] {KEY_TOKEN},
                null, null, null, null, null);
    }

    public void dropDatabase() {
        db.execSQL("DROP TABLE IF EXISTS tokens");
        db.execSQL(CREAR_BD);
    }
}