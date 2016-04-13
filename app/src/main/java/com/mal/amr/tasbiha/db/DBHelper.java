package com.mal.amr.tasbiha.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amr on 4/8/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasbihah.db";
    public static final int VERSION = 4;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql = "CREATE TABLE " + Contract.Tasbiha.TABLE_NAME +
                " (" + Contract.Tasbiha._ID + " INTEGER PRIMARY KEY, " +
                Contract.FREE_TASBIH + " INTEGER, " +
                Contract.SOBHAN_ALLAH + " INTEGER, " +
                Contract.ALHAMDULELLAH + " INTEGER, " +
                Contract.ALLAH_AKBAR + " INTEGER, " +
                Contract.LA_ELAH_ELLA_ALLAH + " INTEGER, " +
                Contract.DATE_TASBIH + " TEXT not null unique );";

        final String demo_sql = "CREATE TABLE " + Contract.TempTasbiha.TABLE_NAME +
                " (" + Contract.TempTasbiha._ID + " INTEGER PRIMARY KEY, " +
                Contract.FREE_TASBIH + " INTEGER, " +
                Contract.SOBHAN_ALLAH + " INTEGER, " +
                Contract.ALHAMDULELLAH + " INTEGER, " +
                Contract.ALLAH_AKBAR + " INTEGER, " +
                Contract.LA_ELAH_ELLA_ALLAH + " INTEGER, " +
                Contract.DATE_TASBIH + " TEXT not null unique );";

        db.execSQL(sql);
        db.execSQL(demo_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Contract.Tasbiha.TABLE_NAME);
        db.execSQL("drop table if exists " + Contract.TempTasbiha.TABLE_NAME);

        onCreate(db);
    }
}
