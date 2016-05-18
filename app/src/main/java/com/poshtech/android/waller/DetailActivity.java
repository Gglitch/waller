package com.poshtech.android.waller;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final String HASH_TAG  = " #WallerApp";
    private String mDataStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        mDataStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView textView = (TextView) findViewById(R.id.detail_text_view);
        textView.setText(mDataStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details,menu);

        MenuItem shareActionMenu = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareActionMenu);
        if (shareActionProvider!=null){
            shareActionProvider.setShareIntent(createShareIntent());
        }
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



    private Intent createShareIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,mDataStr+HASH_TAG);
        return shareIntent;
    }
}
