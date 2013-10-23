package com.bargainburg.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bargainburg.android.API.Model.Search;
import com.bargainburg.android.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/23/13
 * Time: 4:46 PM
 */
public class ListAdapterSearch extends BaseAdapter {
    ArrayList<Search> results = new ArrayList<Search>();
    Context context;

    public ListAdapterSearch(Context context, ArrayList<Search> list) {
        this.context = context;
        results = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.coupon_list_item, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.text = (TextView) view.findViewById(R.id.coupon_text);
            holder.image = (ImageView) view.findViewById(R.id.coupon_image);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Search item = getItem(position);
        holder.text.setText(item.name);
        if (item.type.equals("coupon")) {
            holder.image.setImageResource(R.drawable.ic_coupon);
        } else {
            holder.image.setImageResource(R.drawable.ic_building);
        }
        view.setSelected(true);
        return view;
    }

    public class ViewHolder{
        TextView text;
        ImageView image;
    }

    @Override
    public int getCount(){
        return results.size();
    }

    @Override
    public Search getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
