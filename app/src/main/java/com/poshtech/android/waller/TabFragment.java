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
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.poshtech.android.waller.data.DatabaseContract;

import java.util.ArrayList;

/**
 * Created by Punit Chhajer on 19-05-2016.
 */
public class TabFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private String mMethord = "by_favorites";
    private static final int WALLPAPER_LOADER =0;

    private static final String[] WALLPAPER_COLUMNS={
            DatabaseContract.WallpaperEntries.COLUMN_ID,
            DatabaseContract.WallpaperEntries.COLUMN_NAME,
            DatabaseContract.WallpaperEntries.COLUMN_CATAGORY,
            DatabaseContract.WallpaperEntries.COLUMN_WIDTH,
            DatabaseContract.WallpaperEntries.COLUMN_HEIGHT
    };

    public static final int COL_ID = 0;
    public static final int COL_NAME =1;
    public static final int COL_CATAGORY = 2;
    public static final int COL_WIDTH = 3;
    public static final int COL_HEIGHT = 4;

    private SimpleCursorAdapter itemAdapter;

    public TabFragment(){
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

        itemAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_layout_view,
                null,

                new String[]{
                        DatabaseContract.WallpaperEntries.COLUMN_NAME,
                        DatabaseContract.WallpaperEntries.COLUMN_WIDTH,
                        DatabaseContract.WallpaperEntries.COLUMN_HEIGHT
                },
                new int[]{
                        R.id.list_item_name_textview,
                        R.id.list_item_width_textview,
                        R.id.list_item_height_textview
                },
                0
        );

        ListView listView = (ListView) rootView.findViewById(R.id.list_view_wallpaper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleCursorAdapter adapter = (SimpleCursorAdapter) parent.getAdapter();
                Cursor cursor = adapter.getCursor();
                if (cursor!=null &&cursor.moveToPosition(position)){
                    int idIndex = cursor.getColumnIndex(DatabaseContract.WallpaperEntries.COLUMN_ID);
                    long Id = cursor.getInt(idIndex);
                    Intent intent = new Intent(getActivity(),DetailActivity.class)
                            .putExtra(Intent.EXTRA_TEXT,Id);
                    startActivity(intent);
                }
            }
        });
        listView.setAdapter(itemAdapter);
        updateData();
        return rootView;
    }

    private void updateData() {
        FetchRandomData wallpaperDetail = new FetchRandomData(getContext());
        wallpaperDetail.execute(mMethord);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri wallpaperForMethord = DatabaseContract.WallpaperEntries.buildWallpaperMethord(mMethord);
        return new CursorLoader(
                getActivity(),
                wallpaperForMethord,
                WALLPAPER_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        itemAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemAdapter.swapCursor(null);
    }
}
