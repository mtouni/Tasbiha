package com.mal.amr.tasbiha.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amr on 4/8/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasbihah.db";
    public static final int VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql = "create table " + Contract.TABLE_NAME +
                " (" + Contract._ID + " integer primary key, " +
                Contract.FREE + " integer not null, " +
                Contract.SOBHAN_ALLAH + " integer not null, " +
                Contract.ALHAMDULELLAH + " integer not null, " +
                Contract.ALLAH_AKBAR + " integer not null, " +
                Contract.DATE + " text unique not null);";

        final String demo_sql = "create table " + Contract.DEMO_TABLE_NAME +
                " (" + Contract._ID + " integer primary key, " +
                Contract.FREE + " integer not null, " +
                Contract.SOBHAN_ALLAH + " integer not null, " +
                Contract.ALHAMDULELLAH + " integer not null, " +
                Contract.ALLAH_AKBAR + " integer not null, " +
                Contract.DATE + " text unique not null);";

        db.execSQL(sql);
        db.execSQL(demo_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Contract.TABLE_NAME);

        onCreate(db);
    }
}
