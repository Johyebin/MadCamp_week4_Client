package com.example.mad_camp_week4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    ArrayList<GoodsItem> menus;
    Context context;
    LayoutInflater inflater;

    public MenuAdapter(Context context, ArrayList<GoodsItem> menus){
        this.context = context;
        this.menus = menus;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.coffee_select_item, null);
        }
        ImageView coffeeImg = convertView.findViewById(R.id.coffee_image);
        TextView coffeeName = convertView.findViewById(R.id.coffee_name);
        ImageView favorite = convertView.findViewById(R.id.star_image);
        ImageView checked = convertView.findViewById(R.id.check_image);

        coffeeImg.setImageResource(menus.get(position).getImgSrc());
        coffeeName.setText(menus.get(position).getGoodName());
        if(menus.get(position).getIsFavorite()){
            favorite.setVisibility(View.VISIBLE);
        }

        if(menus.get(position).getIsChecked()){
            checked.setVisibility(View.VISIBLE);
        } else {
            checked.setVisibility(View.GONE);
        }

        return convertView;
    }
}
