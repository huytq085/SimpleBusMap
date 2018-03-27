package com.quanghuy.busmap.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quanghuy.busmap.R;
import com.quanghuy.busmap.entity.Route;

import java.util.List;

/**
 * Created by Huy on 3/25/2018.
 */

public class ListRouteAdapter extends BaseAdapter{
    Context context;
    private List<Route> routes;

    public ListRouteAdapter(Context context, List<Route> routes){
        this.context = context;
        this.routes = routes;
    }

    @Override
    public int getCount() {
        return routes.size();
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
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_row, parent, false);

            viewHolder.txtName = convertView.findViewById(R.id.aNametxt);
            viewHolder.txtVersion =convertView.findViewById(R.id.aVersiontxt);
            viewHolder.txtRouteCode = convertView.findViewById(R.id.txtRouteCode);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtName.setText(routes.get(position).getName());
        viewHolder.txtVersion.setText("Version: ");
        viewHolder.txtRouteCode.setText(String.valueOf(routes.get(position).getCode()));

        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;
        TextView txtRouteCode;

    }
}
