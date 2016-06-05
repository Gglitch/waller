package com.poshtech.android.waller.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
public class WallerService extends IntentService {
    private final String LOG_CAT = getClass().getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public WallerService() {
        super("WallerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String methord = intent.getStringExtra(Intent.EXTRA_TEXT);
        Vector<ContentValues> cVVector = null;
        String jsonStr = getResponce(methord);//"{\"success\":true,\"wallpapers\":[{\"id\":\"151835\",\"width\":\"1440\",\"height\":\"900\",\"file_type\":\"jpg\",\"file_size\":\"243881\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/151\\/151835.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/151\\/thumb-151835.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=151835\"},{\"id\":\"117378\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"1016568\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/117\\/117378.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/117\\/thumb-117378.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=117378\"},{\"id\":\"151833\",\"width\":\"1920\",\"height\":\"1080\",\"file_type\":\"jpg\",\"file_size\":\"203863\",\"url_image\":\"https:\\/\\/images.alphacoders.com\\/151\\/151833.jpg\",\"url_thumb\":\"https:\\/\\/images.alphacoders.com\\/151\\/thumb-151833.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=151833\"},{\"id\":\"112131\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"1867374\",\"url_image\":\"https:\\/\\/images.alphacoders.com\\/112\\/112131.jpg\",\"url_thumb\":\"https:\\/\\/images.alphacoders.com\\/112\\/thumb-112131.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=112131\"},{\"id\":\"125088\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"3754254\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/125\\/125088.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/125\\/thumb-125088.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=125088\"},{\"id\":\"123862\",\"width\":\"1600\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"670328\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/123\\/123862.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/123\\/thumb-123862.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=123862\"},{\"id\":\"125091\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"2426559\",\"url_image\":\"https:\\/\\/images.alphacoders.com\\/125\\/125091.jpg\",\"url_thumb\":\"https:\\/\\/images.alphacoders.com\\/125\\/thumb-125091.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=125091\"},{\"id\":\"110016\",\"width\":\"1920\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"1140711\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/110\\/110016.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/110\\/thumb-110016.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=110016\"},{\"id\":\"112080\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"1710135\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/112\\/112080.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/112\\/thumb-112080.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=112080\"},{\"id\":\"521718\",\"width\":\"1920\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"1093152\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/521\\/521718.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/521\\/thumb-521718.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=521718\"},{\"id\":\"118371\",\"width\":\"1440\",\"height\":\"900\",\"file_type\":\"jpg\",\"file_size\":\"981827\",\"url_image\":\"https:\\/\\/images.alphacoders.com\\/118\\/118371.jpg\",\"url_thumb\":\"https:\\/\\/images.alphacoders.com\\/118\\/thumb-118371.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=118371\"},{\"id\":\"115508\",\"width\":\"1600\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"527560\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/115\\/115508.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/115\\/thumb-115508.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=115508\"},{\"id\":\"151667\",\"width\":\"1600\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"324267\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/151\\/151667.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/151\\/thumb-151667.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=151667\"},{\"id\":\"124505\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"2126136\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/124\\/124505.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/124\\/thumb-124505.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=124505\"},{\"id\":\"74381\",\"width\":\"6000\",\"height\":\"2903\",\"file_type\":\"JPG\",\"file_size\":\"12571790\",\"url_image\":\"https:\\/\\/images.alphacoders.com\\/743\\/74381.JPG\",\"url_thumb\":\"https:\\/\\/images.alphacoders.com\\/743\\/thumb-74381.JPG\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=74381\"},{\"id\":\"132608\",\"width\":\"1680\",\"height\":\"1050\",\"file_type\":\"jpg\",\"file_size\":\"284054\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/132\\/132608.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/132\\/thumb-132608.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=132608\"},{\"id\":\"124228\",\"width\":\"1600\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"218320\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/124\\/124228.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/124\\/thumb-124228.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=124228\"},{\"id\":\"114000\",\"width\":\"1600\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"218936\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/114\\/114000.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/114\\/thumb-114000.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=114000\"},{\"id\":\"109174\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"1554011\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/109\\/109174.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/109\\/thumb-109174.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=109174\"},{\"id\":\"123838\",\"width\":\"1600\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"177306\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/123\\/123838.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/123\\/thumb-123838.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=123838\"},{\"id\":\"86839\",\"width\":\"1920\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"1778527\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/868\\/86839.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/868\\/thumb-86839.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=86839\"},{\"id\":\"123859\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"2186978\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/123\\/123859.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/123\\/thumb-123859.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=123859\"},{\"id\":\"120849\",\"width\":\"1280\",\"height\":\"1024\",\"file_type\":\"jpg\",\"file_size\":\"623903\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/120\\/120849.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/120\\/thumb-120849.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=120849\"},{\"id\":\"111719\",\"width\":\"1920\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"1313099\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/111\\/111719.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/111\\/thumb-111719.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=111719\"},{\"id\":\"120558\",\"width\":\"2560\",\"height\":\"1920\",\"file_type\":\"jpg\",\"file_size\":\"1873917\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/120\\/120558.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/120\\/thumb-120558.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=120558\"},{\"id\":\"121152\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"1049366\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/121\\/121152.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/121\\/thumb-121152.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=121152\"},{\"id\":\"296286\",\"width\":\"1440\",\"height\":\"900\",\"file_type\":\"jpg\",\"file_size\":\"1119600\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/296\\/296286.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/296\\/thumb-296286.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=296286\"},{\"id\":\"106860\",\"width\":\"1920\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"540142\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/106\\/106860.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/106\\/thumb-106860.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=106860\"},{\"id\":\"97962\",\"width\":\"1600\",\"height\":\"1200\",\"file_type\":\"jpg\",\"file_size\":\"554631\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/979\\/97962.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/979\\/thumb-97962.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=97962\"},{\"id\":\"155542\",\"width\":\"1920\",\"height\":\"1440\",\"file_type\":\"jpg\",\"file_size\":\"446315\",\"url_image\":\"https:\\/\\/images2.alphacoders.com\\/155\\/155542.jpg\",\"url_thumb\":\"https:\\/\\/images2.alphacoders.com\\/155\\/thumb-155542.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=155542\"}]}";
        if (jsonStr!=null){
            String[] ids = getWallpaperIdFromJson(jsonStr);
            cVVector = new Vector<ContentValues>(ids.length);
            /*
            String[] dt = new String[]{
                    "{\"success\":true,\"wallpaper\":{\"id\":\"151835\",\"name\":\"Silence and Sorrow\",\"featured\":\"1\",\"width\":\"1440\",\"height\":\"900\",\"file_type\":\"jpg\",\"file_size\":\"243881\",\"url_image\":\"https:\\/\\/images3.alphacoders.com\\/151\\/151835.jpg\",\"url_thumb\":\"https:\\/\\/images3.alphacoders.com\\/151\\/thumb-151835.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=151835\",\"category\":\"CGI\",\"category_id\":\"5\",\"sub_category\":\"Fantasy\",\"sub_category_id\":\"32475\",\"user_name\":\"Andeavenor\",\"user_id\":\"12095\",\"collection\":null,\"collection_id\":0,\"group\":null,\"group_id\":0},\"tags\":[{\"id\":\"50\",\"name\":\"dark\"},{\"id\":\"350\",\"name\":\"forest\"},{\"id\":\"48\",\"name\":\"angel\"}]}",
                    "{\"success\":true,\"wallpaper\":{\"id\":\"117378\",\"name\":\"Berlinda\",\"featured\":\"1\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"1016568\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/117\\/117378.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/117\\/thumb-117378.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=117378\",\"category\":\"Fantasy\",\"category_id\":\"11\",\"sub_category\":\"dragon\",\"sub_category_id\":\"167147\",\"user_name\":\"SweetWitchy\",\"user_id\":\"19418\",\"collection\":null,\"collection_id\":0,\"group\":null,\"group_id\":0},\"tags\":[{\"id\":\"90\",\"name\":\"dragon\"}]}",
                    "{\"success\":true,\"wallpaper\":{\"id\":\"151833\",\"name\":\"Behold the Nightmare\",\"featured\":\"1\",\"width\":\"1920\",\"height\":\"1080\",\"file_type\":\"jpg\",\"file_size\":\"203863\",\"url_image\":\"https:\\/\\/images.alphacoders.com\\/151\\/151833.jpg\",\"url_thumb\":\"https:\\/\\/images.alphacoders.com\\/151\\/thumb-151833.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=151833\",\"category\":\"Dark\",\"category_id\":\"9\",\"sub_category\":\"Artistic\",\"sub_category_id\":\"81340\",\"user_name\":\"Andeavenor\",\"user_id\":\"12095\",\"collection\":null,\"collection_id\":0,\"group\":null,\"group_id\":0},\"tags\":[{\"id\":\"50\",\"name\":\"dark\"},{\"id\":\"1086\",\"name\":\"unicorn\"},{\"id\":\"2344\",\"name\":\"sadness\"},{\"id\":\"430\",\"name\":\"woman\"},{\"id\":\"201\",\"name\":\"gothic\"}]}",
                    "{\"success\":true,\"wallpaper\":{\"id\":\"112131\",\"name\":\"Karma Journey\",\"featured\":\"0\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"1867374\",\"url_image\":\"https:\\/\\/images.alphacoders.com\\/112\\/112131.jpg\",\"url_thumb\":\"https:\\/\\/images.alphacoders.com\\/112\\/thumb-112131.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=112131\",\"category\":\"Anime\",\"category_id\":\"3\",\"sub_category\":\"Original\",\"sub_category_id\":\"173195\",\"user_name\":\"Andeavenor\",\"user_id\":\"12095\",\"collection\":null,\"collection_id\":0,\"group\":null,\"group_id\":0},\"tags\":[{\"id\":\"418\",\"name\":\"girl\"},{\"id\":\"499\",\"name\":\"tiger\"},{\"id\":\"44\",\"name\":\"anime\"}]}",
                    "{\"success\":true,\"wallpaper\":{\"id\":\"125088\",\"name\":\"Peacocks\",\"featured\":\"1\",\"width\":\"2560\",\"height\":\"1600\",\"file_type\":\"jpg\",\"file_size\":\"3754254\",\"url_image\":\"https:\\/\\/images4.alphacoders.com\\/125\\/125088.jpg\",\"url_thumb\":\"https:\\/\\/images4.alphacoders.com\\/125\\/thumb-125088.jpg\",\"url_page\":\"https:\\/\\/wall.alphacoders.com\\/big.php?id=125088\",\"category\":\"Animal\",\"category_id\":\"2\",\"sub_category\":\"Peafowl\",\"sub_category_id\":\"229403\",\"user_name\":\"Andeavenor\",\"user_id\":\"12095\",\"collection\":\"Birds\",\"collection_id\":365,\"group\":null,\"group_id\":0},\"tags\":[]}"
            };*/

            for (int i=0;i< 5;i++) {
                String datail = getResponce("wallpaper_info",ids[i]);//dt[i];
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
                        wall.put(DatabaseContract.WallpaperEntries.COLUMN_METHORD,methord);
                        cVVector.add(wall);
                    }
                }
            }
        }
        try {
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                getContentResolver().bulkInsert(DatabaseContract.WallpaperEntries.CONTENT_URI, cvArray);

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return;//getWallpaperDataFromJson(forecastJsonStr);
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
    /*
    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        if (strings!=null){
            data.clear();
            for (String field : strings){
                data.add(field);
            }
        }
    }*/

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
            final String API_BASE_URL = "https://wall.alphacoders.com/api2.0/get.php";
            final String AUTH_CODE = "auth";
            final String METHORD = "method";
            final String TYPE = "type";
            Uri builder;
            if (m.length>1){
                builder = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(AUTH_CODE,auth_key)
                        .appendQueryParameter(METHORD,methord)
                        .appendQueryParameter("id",m[1])
                        .build();
            }else{
                builder = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(AUTH_CODE,auth_key)
                        .appendQueryParameter(METHORD,methord)
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

    static public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
}
