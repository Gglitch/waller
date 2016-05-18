package com.poshtech.android.waller;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Punit Chhajer on 12-05-2016.
 */
public class FetchRandomData extends AsyncTask<Void, Void, String[]> {
    private final String LOG_CAT = getClass().getSimpleName();
    private ArrayAdapter<String> data;
    public FetchRandomData(ArrayAdapter<String> data) {
        this.data = data;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String auth_key = "a9cfebcdb4e20ed975e82b7fd877693f";
        String methord = "random";
        int info_level = 1;
        String forecastJsonStr = null;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String API_BASE_URL = "https://wall.alphacoders.com/api2.0/get.php";
            final String AUTH_CODE = "auth";
            final String METHORD = "method";
            final String INFO_LEVEL = "info_level";

            Uri builder = Uri.parse(API_BASE_URL).buildUpon()
                    .appendQueryParameter(AUTH_CODE,auth_key)
                    .appendQueryParameter(METHORD,methord)
                    .appendQueryParameter(INFO_LEVEL,Integer.toString(info_level))
                    .build();

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
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_CAT,e.getMessage());
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
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
        return getWallpaperDataFromJson(forecastJsonStr);
    }

    private String[] getWallpaperDataFromJson(String json){
        if (json==null){
            return new String[0];
        }
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
                    detail[i] = data.get("id").toString() + "   "+data.get("width").toString()+" x " +data.get("height");
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

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        if (strings!=null){
            data.clear();
            for (String field : strings){
                data.add(field);
            }
        }
    }
}
