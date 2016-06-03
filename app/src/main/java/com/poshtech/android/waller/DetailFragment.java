package com.poshtech.android.waller;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.StandaloneActionMode;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poshtech.android.waller.data.DatabaseContract;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_TEXT;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;

/**
 * Created by Punit Chhajer on 19-05-2016.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int DETAIL_LOADER =0;
    private static final String ID_KEY = "_id";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args!=null  && args.containsKey(DetailActivity.KEY) && -1 != mId){
            getLoaderManager().restartLoader(DETAIL_LOADER,null,this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args!=null  && args.containsKey(DetailActivity.KEY)){
            getLoaderManager().initLoader(DETAIL_LOADER,null,this);
        }
    }

    private static final String HASH_TAG  = " #WallerApp";
    private String mDataStr;

    private long mId;
    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mId = getActivity().getIntent().getLongExtra(Intent.EXTRA_TEXT,-1);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details,menu);

        MenuItem shareActionMenu = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareActionMenu);
        if (shareActionProvider!=null){
            shareActionProvider.setShareIntent(createShareIntent());
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.open_settings){
            Intent setting = new Intent(getActivity(), SettingActivity.class);
            startActivity(setting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private Intent createShareIntent(){
        Intent shareIntent = new Intent(ACTION_SEND);
        shareIntent.addFlags(FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(EXTRA_TEXT,mDataStr+HASH_TAG);
        return shareIntent;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mId = getArguments().getLong(DetailActivity.KEY,-1);

        Uri uri = DatabaseContract.WallpaperEntries.buildWallpaperUri(mId);
        return new CursorLoader(
                getActivity(),
                uri,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()){
            String name = data.getString(data.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_NAME));
            String category = data.getString(data.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_CATEGORY));
            String subCategory = data.getString(data.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_SUB_CATAGORY));
            String collection = data.getString(data.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_COLLECTION));
            String group = data.getString(data.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_GROUP));
            long width = data.getLong(data.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_WIDTH));
            long height = data.getLong(data.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_HEIGHT));

            TextView nameView = (TextView)getView().findViewById(R.id.detail_name_textview);
            TextView categoryView = (TextView)getView().findViewById(R.id.detail_category_textview);
            TextView subCategoryView = (TextView)getView().findViewById(R.id.detail_sub_category_textview);
            TextView collectionView = (TextView)getView().findViewById(R.id.detail_collection_textview);
            TextView groupView = (TextView)getView().findViewById(R.id.detail_group_textview);
            TextView widthView = (TextView) getView().findViewById(R.id.detail_width_textview);
            TextView heightView = (TextView) getView().findViewById(R.id.detail_height_textview);

            nameView.setText(name);
            widthView.setText(String.valueOf(width));
            heightView.setText(String.valueOf(height));
            if (category.equals("null")){
                getView().findViewById(R.id.detail_category_block).setVisibility(View.GONE);
            }else{
                categoryView.setText(category);
            }
            if (subCategory.equals("null")){
                getView().findViewById(R.id.detail_sub_category_textview).setVisibility(View.GONE);
            }else{
                subCategoryView.setText(subCategory);
            }
            if (collection.equals("null")){
                getView().findViewById(R.id.detail_collection_block).setVisibility(View.GONE);
            }else{
                collectionView.setText(collection);
            }
            if (group.equals("null")){
                getView().findViewById(R.id.detail_group_block).setVisibility(View.GONE);
            }else{
                groupView.setText(group);
            }
            mDataStr = name ;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ID_KEY, String.valueOf(mId));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
