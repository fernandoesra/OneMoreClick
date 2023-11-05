package com.example.onemoreclick;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "dataOneMoreClick.db";
    private static final int DATABASE_VERSION = 1;

    public static final String tableMaxPoints = "maxPoints";
    public static final String columnTotalPoints = "totalPoints";

    private static final String TABLE_CREATE = "CREATE TABLE " + tableMaxPoints + " (" + columnTotalPoints + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableMaxPoints);
        onCreate(db);
    }
}
