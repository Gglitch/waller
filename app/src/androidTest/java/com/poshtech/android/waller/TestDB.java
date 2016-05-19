package com.poshtech.android.waller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.poshtech.android.waller.data.DatabaseContract;
import com.poshtech.android.waller.data.WallpaperDBHelper;

/**
 * Created by Punit Chhajer on 19-05-2016.
 */
public class TestDB extends AndroidTestCase {
    public static final String LOG_TAG = TestDB.class.getSimpleName();
    public void testCreateDb() throws Throwable{
        mContext.deleteDatabase(WallpaperDBHelper.NAME);
        SQLiteDatabase db = new WallpaperDBHelper(this.mContext).getWritableDatabase();
        assertEquals(true,db.isOpen());
        db.close();
    }

    public void testInsertReadDb(){
        long testId = 595064;
        String testName = "";
        String testCatagory = "Photography";
        String teetSubCatagory = "Park";
        String testCollection = null;
        String testGroup = null;
        long testIsDownloaded = 0;
        long testWidth = 1920;
        long testHeight = 1080;
        String testFileType = "jpg";
        long testSize = 658088;
        String testUrlImage = "https:\\/\\/images3.alphacoders.com\\/595\\/595064.jpg";
        String testUrlThumb = "https:\\/\\/images3.alphacoders.com\\/595\\/thumb-595064.jpg";
        String testMethord = "wallpaper_info";

        WallpaperDBHelper dbHelper = new WallpaperDBHelper(mContext);
        SQLiteDatabase db  = dbHelper.getWritableDatabase();

        ContentValues wall = new ContentValues();
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_ID,testId);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_NAME,testName);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_CATAGORY,testCatagory);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_SUB_CATAGORY,teetSubCatagory);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_COLLECTION,testCollection);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_GROUP,testGroup);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_IS_DOWNLOADED,testIsDownloaded);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_WIDTH,testWidth);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_HEIGHT,testHeight);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_FILE_TYPE,testFileType);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_SIZE,testSize);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_URL_IMAGE,testUrlImage);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_URL_THUMB,testUrlThumb);
        wall.put(DatabaseContract.WallpaperEntries.COLUMN_METHORD,testMethord);

        long detailRowId =-1;
        try {
            detailRowId = db.insert(DatabaseContract.WallpaperEntries.TABLE_NAME, null, wall);
        }catch (Exception e){
            e.printStackTrace();
        }

        assertTrue(detailRowId!=-1);
        Log.d(LOG_TAG,"new row id : "+detailRowId);

        String[] columns = {
                DatabaseContract.WallpaperEntries.COLUMN_ID,
                DatabaseContract.WallpaperEntries.COLUMN_NAME,
                DatabaseContract.WallpaperEntries.COLUMN_CATAGORY,
                DatabaseContract.WallpaperEntries.COLUMN_SUB_CATAGORY,
                DatabaseContract.WallpaperEntries.COLUMN_COLLECTION,
                DatabaseContract.WallpaperEntries.COLUMN_GROUP,
                DatabaseContract.WallpaperEntries.COLUMN_IS_DOWNLOADED,
                DatabaseContract.WallpaperEntries.COLUMN_WIDTH,
                DatabaseContract.WallpaperEntries.COLUMN_HEIGHT,
                DatabaseContract.WallpaperEntries.COLUMN_FILE_TYPE,
                DatabaseContract.WallpaperEntries.COLUMN_SIZE,
                DatabaseContract.WallpaperEntries.COLUMN_URL_IMAGE,
                DatabaseContract.WallpaperEntries.COLUMN_URL_THUMB,
                DatabaseContract.WallpaperEntries.COLUMN_METHORD
        };

        Cursor cursor = db.query(
                DatabaseContract.WallpaperEntries.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()){
            int wallpaperIdIndex = cursor.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_ID);
            long wallpaperId = cursor.getLong(wallpaperIdIndex);

            int wallpaperNameIndex = cursor.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_NAME);
            String wallpaperName = cursor.getString(wallpaperNameIndex);

            int wallpaperWidthIndex = cursor.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_WIDTH);
            long wallpaperWidth = cursor.getLong(wallpaperWidthIndex);

            int wallpaperHeightIndex = cursor.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_HEIGHT);
            long wallpaperHeight = cursor.getLong(wallpaperHeightIndex);

            assertEquals(testId,wallpaperId);
            assertEquals(testName,wallpaperName);
            assertEquals(testWidth,wallpaperWidth);
            assertEquals(testHeight,wallpaperHeight);
        } else {
            fail("No values returned");
        }
    }
}
