package com.quanghuy.busmap.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.quanghuy.busmap.R;
import com.quanghuy.busmap.entity.Route;
import com.quanghuy.busmap.utils.JsonUtils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Huy on 3/25/2018.
 */

public class ListRouteAdapter extends BaseAdapter implements Filterable {
    Context context;
    private ValueFilter valueFilter;
    private List<Route> routes;
    private List<Route> mStringFilterList;


    public ListRouteAdapter(Context context, List<Route> routes) {
        this.context = context;
        this.routes = routes;
        this.mStringFilterList = routes;

    }

    @Override
    public int getCount() {
        return routes.size();
    }

    @Override
    public Object getItem(int i) {
        return routes.get(i);
    }

    public void removeItem(int code){
        Log.d("LIST", "code: " + code);
        Iterator<Route> it = routes.iterator();
        while (it.hasNext()) {
            Route route = it.next();
            Log.d("TEST", "removeItem: " + route.getCode());
            if (route.getCode() == code) {
                Log.d("TEST", "removeItem: Ok to remove");
//              FIXME: java.lang.UnsupportedOperationException
                it.remove();
            }
        }
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
            viewHolder.txtVersion = convertView.findViewById(R.id.aVersiontxt);
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

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;
        TextView txtRouteCode;

    }

    public static void main(String[] args) {
        List<Route> lists = new ArrayList<>();
        Route a = new Route();
        a.setCode(1);
        Route b = new Route();
        b.setCode(2);
        lists.add(a);
        lists.add(b);
        Iterator<Route> it = lists.iterator();
        System.out.println(JsonUtils.encode(lists));
        while (it.hasNext()) {
            Route route = it.next();
            if (route.getCode() == 1) {
                it.remove();
            }
        }
        System.out.println(JsonUtils.encode(lists));

    }

    private class ValueFilter extends Filter {
        FilterResults results = new FilterResults();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Route> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toUpperCase()
                            .contains(constraint.toString().toUpperCase()))
                            ||
                            (NumberUtils.isCreatable(constraint.toString())
                                    && mStringFilterList.get(i).getCode() == Integer.valueOf(constraint.toString()))) {

                        Route route = new Route();
                        route.setName(mStringFilterList.get(i).getName());
                        route.setCode(mStringFilterList.get(i).getCode());

                        filterList.add(route);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            routes = (List<Route>) results.values;
            notifyDataSetChanged();
        }
    }
}

