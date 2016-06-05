package com.poshtech.android.waller;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.poshtech.android.waller.custom.ViewPagerAdapter;
import com.poshtech.android.waller.data.DatabaseContract;

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

public class MainActivity extends AppCompatActivity implements CallBack{
    private Boolean mTwoPane;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (findViewById(R.id.wallpaper_detail_container)!=null){
            mTwoPane = true;

            if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wallpaper_detail_container,new DetailFragment())
                        .commit();
            }
        }else{
            mTwoPane = false;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //fragment of tab one
        Bundle args = new Bundle();
        args.putString(TabFragment.SELECTED_METHORD_KEY,TabFragment.POPULAR);
        TabFragment popular = new TabFragment();
        popular.setArguments(args);
        adapter.addFragment(popular,"Popular");

        //fragment of tab two
        args = new Bundle();
        args.putString(TabFragment.SELECTED_METHORD_KEY,TabFragment.RANDOM);
        TabFragment random = new TabFragment();
        random.setArguments(args);
        adapter.addFragment(random,"Random");

        //fragment of tab three
        args = new Bundle();
        args.putString(TabFragment.SELECTED_METHORD_KEY,TabFragment.LOCAL);
        TabFragment local = new TabFragment();
        local.setArguments(args);
        adapter.addFragment(local,"Local");


        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
       if (id==R.id.open_settings){
            Intent setting = new Intent(this, SettingActivity.class);
            startActivity(setting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(long _Id) {
        if (mTwoPane){
            Bundle args = new Bundle();
            args.putLong(DetailActivity.KEY,_Id);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.wallpaper_detail_container,fragment)
                    .commit();

        }else{
            Intent intent = new Intent(this,DetailActivity.class)
                    .putExtra(DetailActivity.KEY,_Id);
            startActivity(intent);
        }
    }
}
