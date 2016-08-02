package com.poshtech.android.waller;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.poshtech.android.waller.sync.WallerSyncAdapter;

/**
 * Created by Punit Chhajer on 05-06-2016.
 */
public class SearchResultActivity extends AppCompatActivity implements CallBack{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle args = new Bundle();
        args.putString(TabFragment.SELECTED_METHORD_KEY,TabFragment.SEARCH);
        TabFragment search = new TabFragment();
        search.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.search_container, search)
                .commit();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            WallerSyncAdapter.syncSelectiveImmediately(getApplicationContext(),"search",query);
        }
    }

    @Override
    public void onItemSelected(long _Id) {
        Intent intent = new Intent(this,DetailActivity.class)
                .putExtra(DetailActivity.KEY,_Id);
        startActivity(intent);
    }
}
