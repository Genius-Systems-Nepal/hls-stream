package com.multicast.udpstream.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static int VERSION = 1;
    private static String DB_NAME = "DB_CHANNELS";
    static String CHANNELS_TABLE = "channels";

    DbHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS "+ CHANNELS_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "ip TEXT NOT NULL);";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Success DB");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela "+ e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+CHANNELS_TABLE+" ;";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Success updating DB");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar a tabela "+ e.getMessage());
        }
    }
}
