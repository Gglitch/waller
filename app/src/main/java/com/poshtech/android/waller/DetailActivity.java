package com.poshtech.android.waller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.detail_text_view);
        textView.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
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
}
