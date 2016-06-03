package com.poshtech.android.waller.data;

import android.content.ContentUris;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Punit Chhajer on 18-05-2016.
 */
public class DatabaseContract {
    public static final String CONTENT_AUTHORITY = "com.poshtech.android.waller";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_BASIC = "basic_info";

    public static final class WallpaperEntries implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BASIC).build();
        public static final String TABLE_NAME = "basic_info";


        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/"+CONTENT_AUTHORITY + "/"+
                        PATH_BASIC;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/"+CONTENT_AUTHORITY + "/"+
                        PATH_BASIC;

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_SUB_CATAGORY = "sub_catagory";
        public static final String COLUMN_COLLECTION = "collection";
        public static final String COLUMN_GROUP = "grp";
        public static final String COLUMN_IS_DOWNLOADED = "is_downloaded";
        public static final String COLUMN_WIDTH = "width";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_FILE_TYPE = "file_type";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_URL_IMAGE = "url_image";
        public static final String COLUMN_URL_THUMB = "url_thumb";
        public static final String COLUMN_METHORD = "methord";

        public static Uri buildWallpaperUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static Uri buildWallpaperMethord(String methord){
            return CONTENT_URI.buildUpon().appendPath(COLUMN_METHORD).appendPath(methord).build();
        }

        public static  Uri buildWallpaperDownloaded(int isDownloaded){
            return CONTENT_URI.buildUpon().appendPath(COLUMN_IS_DOWNLOADED).appendPath(String.valueOf(isDownloaded)).build();
        }

        public static String getMethordFromUri(Uri uri){
            return uri.getPathSegments().get(2);
        }

        public static int getDownloadFlagFromUri(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(2));
        }

        public static int getId(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(0));
        }
    }
}
