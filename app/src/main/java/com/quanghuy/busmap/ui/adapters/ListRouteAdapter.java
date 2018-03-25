package com.quanghuy.busmap.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quanghuy.busmap.R;

import java.util.Arrays;

/**
 * Created by Huy on 3/25/2018.
 */

public class ListRouteAdapter extends BaseAdapter{
    Context context;
    private final String [] values;
    private final String [] numbers;
    private final int [] images;

    public ListRouteAdapter(Context context, String [] values, String [] numbers, int [] images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values = values;
        this.numbers = numbers;
        this.images = images;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            Log.d("convertView", "getView: ");
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_row, parent, false);

            viewHolder.txtName = convertView.findViewById(R.id.aNametxt);
            Log.d("errrrrrrro", "txtName1: " + convertView.findViewById(R.id.aNametxt));
            Log.d("errrrrrrro", "txtName2: " + viewHolder.txtName);
            viewHolder.txtVersion =convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = convertView.findViewById(R.id.appIconIV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.d("test", "viewholder: " + viewHolder);
        Log.d("test", "getView: " + position);
        Log.d("test", "pos: " + values[position]);
        viewHolder.txtName.setText(values[position]);
        viewHolder.txtVersion.setText("Version: "+numbers[position]);
        viewHolder.icon.setImageResource(images[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;
        ImageView icon;

    }
}
