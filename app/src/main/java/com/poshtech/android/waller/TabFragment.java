package com.poshtech.android.waller;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.poshtech.android.waller.custom.CustomCursorAdapter;
import com.poshtech.android.waller.data.DatabaseContract;

import java.util.ArrayList;

public class TabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String POPULAR = "by_favorites";
    public static final String RANDOM = "random";
    public static final String LOCAL = "local";
    public static final String SEARCH = "search";
    public static final String SELECTED_METHORD_KEY = "methord";
    private static final String SELECTED_KEY = "position";

    private String mMethord;
    private static final int WALLPAPER_LOADER =0;
    private int mPosition;
    private ListView mListView;



    private static final String[] WALLPAPER_COLUMNS={
            DatabaseContract.WallpaperEntries.COLUMN_ID,
            DatabaseContract.WallpaperEntries.COLUMN_NAME,
            DatabaseContract.WallpaperEntries.COLUMN_CATEGORY,
            DatabaseContract.WallpaperEntries.COLUMN_SUB_CATAGORY,
            DatabaseContract.WallpaperEntries.COLUMN_COLLECTION,
            DatabaseContract.WallpaperEntries.COLUMN_WIDTH,
            DatabaseContract.WallpaperEntries.COLUMN_HEIGHT
    };

    public static final int COL_ID = 0;
    public static final int COL_NAME =1;
    public static final int COL_CATEGORY = 2;
    public static final int COL_SUB_CATEGORY = 3;
    public static final int COL_COLLECTION = 4;
    public static final int COL_WIDTH = 5;
    public static final int COL_HEIGHT = 6;

    private CustomCursorAdapter itemAdapter;

    public TabFragment(){
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition!= ListView.INVALID_POSITION){
            outState.putInt(SELECTED_KEY,mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(WALLPAPER_LOADER,null,this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        ArrayList<String> dataList = new ArrayList<String>();
        if (getArguments().containsKey(SELECTED_METHORD_KEY)){
            mMethord = getArguments().getString(SELECTED_METHORD_KEY);
        }

        itemAdapter = new CustomCursorAdapter(
                getActivity(),
                null,
                0
        );

        mListView = (ListView) rootView.findViewById(R.id.list_view_wallpaper);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomCursorAdapter adapter = (CustomCursorAdapter) parent.getAdapter();
                Cursor cursor = adapter.getCursor();
                if (cursor!=null &&cursor.moveToPosition(position)){
                    int idIndex = cursor.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_ID);
                    long Id = cursor.getInt(idIndex);
                    ((CallBack) getActivity()).onItemSelected(Id);
                }
                mPosition = position;
            }
        });
        mListView.setAdapter(itemAdapter);

        if (savedInstanceState!=null &&savedInstanceState.containsKey(SELECTED_KEY)){
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        //updateData();
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri wallpaperUri;
        if (mMethord.equals(LOCAL)){
            wallpaperUri = DatabaseContract.WallpaperEntries.buildWallpaperDownloaded();
        }else {
            wallpaperUri = DatabaseContract.WallpaperEntries.buildWallpaperMethord(mMethord);
        }
        return new CursorLoader(
                getActivity(),
                wallpaperUri,
                WALLPAPER_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        itemAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            mListView.setSelection(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemAdapter.swapCursor(null);
    }
}
