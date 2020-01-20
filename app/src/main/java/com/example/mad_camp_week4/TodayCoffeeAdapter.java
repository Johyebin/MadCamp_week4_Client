package com.example.mad_camp_week4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodayCoffeeAdapter extends RecyclerView.Adapter<TodayCoffeeAdapter.ViewHolder> {
    private ArrayList<GoodsItem> mData = null;

    TodayCoffeeAdapter(ArrayList<GoodsItem> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.today_coffee_item, parent, false) ;
        TodayCoffeeAdapter.ViewHolder vh = new TodayCoffeeAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsItem item = mData.get(position);

        holder.img.setImageResource(item.getImgSrc());
        holder.menu.setText(item.getGoodName());
        holder.cafe.setText(item.getCafeName());
        holder.price.setText(item.getPrice());
        holder.caffeine_content.setText(item.getCaffeineContent());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView menu;
        TextView cafe;
        TextView price;
        TextView caffeine_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.today_coffee_image);
            menu = itemView.findViewById(R.id.today_coffee_menu);
            cafe = itemView.findViewById(R.id.today_coffee_cafe);
            price = itemView.findViewById(R.id.today_coffee_price);
            caffeine_content = itemView.findViewById(R.id.today_coffee_caffeine_content);
        }
    }
}
