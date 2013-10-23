package com.bargainburg.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bargainburg.android.API.Model.Coupon;
import com.bargainburg.android.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: christhoma
 * Date: 10/22/13
 * Time: 8:39 PM
 */
public class ListAdapterCoupons extends BaseAdapter {

    ArrayList<Coupon> coupons = new ArrayList<Coupon>();
    Context context;

    public ListAdapterCoupons(Context context, ArrayList<Coupon> list) {
        this.context = context;
        coupons = list;
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
        holder.text.setText(getItem(position).name);
        view.setSelected(true);
        return view;
    }

    public class ViewHolder{
        TextView text;
        ImageView image;
    }

    @Override
    public int getCount(){
        return coupons.size();
    }

    @Override
    public Coupon getItem(int position) {
        return coupons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
