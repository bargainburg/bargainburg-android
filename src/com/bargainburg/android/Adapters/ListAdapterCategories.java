package com.bargainburg.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bargainburg.android.API.Model.Category;
import com.bargainburg.android.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 8/12/13
 * Time: 3:36 PM
 */
public class ListAdapterCategories extends BaseAdapter {
    ArrayList<Category> items = new ArrayList<Category>();
    Context context;

    public ListAdapterCategories(Context context, ArrayList<Category> list) {
        this.context = context;
        items = list;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.text = (TextView) view.findViewById(R.id.list_item_text);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.text.setText(getItem(position).name);
        view.setSelected(true);
        return view;
    }

    public class ViewHolder {
        TextView text;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Category getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}