package com.poshtech.android.waller.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Punit Chhajer on 18-05-2016.
 */
public class WallpaperProvider extends ContentProvider {
    private static final int WALLPAPER = 100;
    private static final int WALLPAPER_WITH_METHORD = 101;
    private static final int WALLPAPER_WITH_LOCAL = 102;

    private static final UriMatcher sUriMatcher = buildUriMacher();
    private WallpaperDBHelper mOpenHelper;

    public static UriMatcher buildUriMacher(){


        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,DatabaseContract.PATH_BASIC,WALLPAPER);
        matcher.addURI(authority,DatabaseContract.PATH_BASIC+"/*",WALLPAPER_WITH_METHORD);
        matcher.addURI(authority,DatabaseContract.PATH_BASIC+"/#",WALLPAPER_WITH_LOCAL);

        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new WallpaperDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        switch (sUriMatcher.match(uri)){
            case WALLPAPER_WITH_LOCAL:
                int downloadFlag  = DatabaseContract.WallpaperEntries.getDownloadFlagFromUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.WallpaperEntries.TABLE_NAME,
                        projection,
                        DatabaseContract.WallpaperEntries.COLUMN_IS_DOWNLOADED + " = ? ",
                        new String[]{String.valueOf(downloadFlag)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case WALLPAPER_WITH_METHORD:
                String methord  = DatabaseContract.WallpaperEntries.getMethordFromUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.WallpaperEntries.TABLE_NAME,
                        projection,
                        DatabaseContract.WallpaperEntries.COLUMN_METHORD + " = ? ",
                        new String[]{methord},
                        null,
                        null,
                        sortOrder
                );
                break;
            case WALLPAPER:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.WallpaperEntries.TABLE_NAME,
                        projection,
                        DatabaseContract.WallpaperEntries.COLUMN_ID+" = '"+ ContentUris.parseId(uri)+"'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case WALLPAPER_WITH_LOCAL:
            case WALLPAPER_WITH_METHORD:
                return DatabaseContract.WallpaperEntries.CONTENT_TYPE;
            case WALLPAPER:
                return DatabaseContract.WallpaperEntries.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri = null;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case WALLPAPER:
                long _id = db.insert(DatabaseContract.WallpaperEntries.TABLE_NAME,null,values);
                if (_id > 0 ){
                    returnUri = DatabaseContract.WallpaperEntries.buildWallpaperUri(_id);
                }else {
                    throw new android.database.SQLException("Failed to insert row into uri: "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int noOfRows;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case WALLPAPER:
                noOfRows = db.delete(DatabaseContract.WallpaperEntries.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        if (noOfRows>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return noOfRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int noOfRows;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case WALLPAPER:
                noOfRows = db.update(DatabaseContract.WallpaperEntries.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        if (noOfRows>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return noOfRows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WALLPAPER:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DatabaseContract.WallpaperEntries.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
