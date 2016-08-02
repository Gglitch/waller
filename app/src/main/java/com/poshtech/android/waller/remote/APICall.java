package com.poshtech.android.waller.remote;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.poshtech.android.waller.data.DatabaseContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by Punit Chhajer on 05-06-2016.
 */
public class APICall {
    private final String LOG_CAT = getClass().getSimpleName();
    private String mMethord;
    private Context mContext;
    private String term;
    private int page;
    private Vector<ContentValues> cVVector;

    final String API_BASE_URL = "https://wall.alphacoders.com/api2.0/get.php";
    final String AUTH_CODE = "auth";
    final String METHORD = "method";
    final String TERM = "term";
    final String PAGE = "page";
    final String ID = "id";
    final String TYPE = "type";


    public APICall(Context context, String methord) {
        this.mContext = context;
        this.mMethord = methord;
        page = 1;
    }


    public void setTerm(String term){
        this.term = term;
    }
    public void getData(){
        String jsonStr = getResponce(mMethord);
        if (jsonStr!=null){
            String[] ids = getWallpaperIdFromJson(jsonStr);
            cVVector = new Vector<ContentValues>(ids.length);;
            for (int i=0;i< ids.length;i++) {
                String datail = getResponce("wallpaper_info",ids[i]);
                if (datail!=null){
                    String[] d =getWallpaperDetailFromJson(datail);
                    if (d.length>0){
                        ContentValues wall = new ContentValues();
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_ID,d[0]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_NAME,d[1]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_CATEGORY,d[2]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_SUB_CATAGORY,d[3]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_COLLECTION,d[4]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_GROUP,d[5]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_IS_DOWNLOADED,d[6]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_WIDTH,d[7]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_HEIGHT,d[8]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_FILE_TYPE,d[9]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_SIZE,d[10]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_URL_IMAGE,d[11]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_URL_THUMB,d[12]);
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_METHORD,mMethord);
                        cVVector.add(wall);
                    }
                }
            }
        }
        try {
            if (cVVector.size() > 0) {
                if (page++==1){
                    mContext.getContentResolver().delete(DatabaseContract.WallpaperEntries.CONTENT_URI,
                            DatabaseContract.WallpaperEntries.COLUMN_METHORD +" = ?",
                            new String[]{mMethord});
                }
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(DatabaseContract.WallpaperEntries.CONTENT_URI, cvArray);

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
    private String[] getWallpaperIdFromJson(String json){
        String[] detail = new String[0];
        JSONObject rawData = null;
        try {
            rawData = new JSONObject(json);
            Object success  = rawData.get("success");
            if ((boolean)success){
                JSONArray dataArray = rawData.getJSONArray("wallpapers");
                detail = new String[dataArray.length()];
                for (int i=0; i< dataArray.length();i++) {
                    JSONObject data = dataArray.getJSONObject(i);
                    detail[i] = data.get("id").toString();
                }
            }else  {
                String error  = rawData.get("error").toString();
                Log.e(LOG_CAT,error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return detail;
    }
    private String[] getWallpaperDetailFromJson(String json){
        String[] detail = new String[0];
        JSONObject rawData = null;
        try {
            rawData = new JSONObject(json);
            Object success  = rawData.get("success");
            if ((boolean)success){
                JSONObject data = rawData.getJSONObject("wallpaper");
                detail = new String[14];
                detail[0] = data.getString("id");
                detail[1] = data.getString("name");
                detail[2] = data.getString("category");
                detail[3] = data.getString("sub_category");
                detail[4] = data.getString("collection");
                detail[5] = data.getString("group");
                detail[6] = "0";
                detail[7] = data.getString("width");
                detail[8] = data.getString("height");
                detail[9] = data.getString("file_type");
                detail[10] = data.getString("file_size");
                detail[11] = data.getString("url_image");
                detail[12] = data.getString("url_thumb");
            }else  {
                String error  = rawData.get("error").toString();
                Log.e(LOG_CAT,error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return detail;
    }

    private String getResponce(String... m){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String auth_key = "a9cfebcdb4e20ed975e82b7fd877693f";
        String methord = m[0];
        String JsonStr = null;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            Uri builder;
            if (m[0].equals("wallpaper_info")){
                builder = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(AUTH_CODE,auth_key)
                        .appendQueryParameter(METHORD,methord)
                        .appendQueryParameter(ID,m[1])
                        .build();
            } else if (m[0].equals("search")){
                builder = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(AUTH_CODE,auth_key)
                        .appendQueryParameter(METHORD,methord)
                        .appendQueryParameter(TERM,term.replaceAll("\\s+","\\+"))
                        .appendQueryParameter(PAGE, String.valueOf(page))
                        .appendQueryParameter(TYPE,"1")
                        .build();
            } else{
                builder = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(AUTH_CODE,auth_key)
                        .appendQueryParameter(METHORD,methord)
                        .appendQueryParameter(PAGE, String.valueOf(page))
                        .appendQueryParameter(TYPE,"1")
                        .build();
            }


            URL url = new URL(builder.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_CAT,e.getMessage());
            JsonStr = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_CAT,e.getMessage());
                }
            }
        }
        return JsonStr;
    }
}
