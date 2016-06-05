package com.poshtech.android.waller;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static android.content.Intent.*;

public class DetailActivity extends AppCompatActivity {
    public final static String KEY = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragmenttransaction.
            long id = getIntent().getLongExtra(KEY,-1);

            Bundle args = new Bundle();
            args.putLong(KEY,id);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.wallpaper_detail_container, fragment)
                    .commit();
        }
    }
}
