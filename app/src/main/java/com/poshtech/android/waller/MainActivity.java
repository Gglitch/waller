package com.poshtech.android.waller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] dataArrray = {"apple","banana", "cream","dream","Ear", "Front","Lol","haha","hmm"};

        ArrayList<String> dataList = new ArrayList<String>(Arrays.asList(dataArrray));

        data = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.list_layout_view,
                R.id.list_item_wallpaper_title,
                dataList
        );

        ListView listView = (ListView) findViewById(R.id.list_view_wallpaper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fieldData = data.getItem(position);
                Intent intent = new Intent(getApplication(),DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT,fieldData);
                startActivity(intent);
            }
        });
        listView.setAdapter(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.action_refresh){
            FetchRandomData wallpaperDetail = new FetchRandomData(data);
            wallpaperDetail.execute();
            return true;
        }else if (id==R.id.open_settings){
            Intent setting = new Intent(this, SettingActivity.class);
            startActivity(setting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
