package com.poshtech.android.waller.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.poshtech.android.waller.data.DatabaseContract.WallpaperEntries;

/**
 * Created by Punit Chhajer on 18-05-2016.
 */
public class WallpaperDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VESION = 1;
    public static final String NAME = "wallpaper.db";

    public WallpaperDBHelper(Context context) {
        super(context, NAME, null, DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_BASIC_TABLE =
                "CREATE TABLE " + WallpaperEntries.TABLE_NAME + " ("+
                        WallpaperEntries.COLUMN_ID + " INTEGER PRIMARY KEY,"+
                        WallpaperEntries.COLUMN_NAME + " TEXT ,"+
                        WallpaperEntries.COLUMN_CATAGORY + " TEXT ," +
                        WallpaperEntries.COLUMN_SUB_CATAGORY + " TEXT ," +
                        WallpaperEntries.COLUMN_COLLECTION + " TEXT ,"+
                        WallpaperEntries.COLUMN_GROUP + " TEXT,"+
                        WallpaperEntries.COLUMN_IS_DOWNLOADED+ " INTEGER DEFAULT 0,"+
                        WallpaperEntries.COLUMN_WIDTH + " INIEGER ,"+
                        WallpaperEntries.COLUMN_HEIGHT + " INTEGER ," +
                        WallpaperEntries.COLUMN_FILE_TYPE + " TEXT NOT NULL,"+
                        WallpaperEntries.COLUMN_SIZE + " INTEGER NOT NULL," +
                        WallpaperEntries.COLUMN_URL_IMAGE + " TEXT NOT NULL," +
                        WallpaperEntries.COLUMN_URL_THUMB + " TEXT NOT NULL,"+
                        WallpaperEntries.COLUMN_METHORD + " TEXT NOT NULL, "+

                        "UNIQUE ("+WallpaperEntries.COLUMN_ID+") ON CONFLICT IGNORE);";

        db.execSQL(SQL_CREATE_BASIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+WallpaperEntries.TABLE_NAME);
        onCreate(db);
    }
}
