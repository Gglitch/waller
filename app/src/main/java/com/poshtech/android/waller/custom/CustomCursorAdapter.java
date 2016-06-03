package com.poshtech.android.waller.custom;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poshtech.android.waller.R;
import com.poshtech.android.waller.TabFragment;

/**
 * Created by Punit Chhajer on 03-06-2016.
 */
public class CustomCursorAdapter extends CursorAdapter {

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_layout_view,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView wallThumb = (ImageView) view.findViewById(R.id.imageThumb);
        ((AnimationDrawable) wallThumb.getDrawable()).start();

        String sname = cursor.getString(TabFragment.COL_NAME);
        if (!sname.equals("null")){
            TextView tvname = (TextView) view.findViewById(R.id.list_item_name_textview);
            tvname.setText(sname);
        }
        String scategory = cursor.getString(TabFragment.COL_CATEGORY);
        if (!scategory.equals("null")){
            TextView tvcategory = (TextView) view.findViewById(R.id.list_item_category_textview);
            tvcategory.setText(scategory);
            tvcategory.setVisibility(View.VISIBLE);
        }
        String sscategory = cursor.getString(TabFragment.COL_SUB_CATEGORY);
        if (!sscategory.equals("null")){
            TextView tvscategory = (TextView) view.findViewById(R.id.list_item_sub_category_textview);
            tvscategory.setText(sscategory);
            tvscategory.setVisibility(View.VISIBLE);
            view.findViewById(R.id.list_item_category_dash).setVisibility(View.VISIBLE);
        }
        String scollection = cursor.getString(TabFragment.COL_COLLECTION);
        if (!scollection.equals("null")){
            TextView tvcollection = (TextView) view.findViewById(R.id.list_item_collection_textview);
            tvcollection.setText(scollection);
            tvcollection.setVisibility(View.VISIBLE);
        }

        String swidth = cursor.getString(TabFragment.COL_WIDTH);
        if (!swidth.equals("null")){
            TextView tvwidth = (TextView) view.findViewById(R.id.list_item_width_textview);
            tvwidth.setText(swidth);
        }

        String sheight = cursor.getString(TabFragment.COL_HEIGHT);
        if (!sheight.equals("null")){
            TextView tvheight = (TextView) view.findViewById(R.id.list_item_height_textview);
            tvheight.setText(sheight);
        }
    }
}
