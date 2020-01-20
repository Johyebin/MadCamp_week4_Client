package com.example.mad_camp_week4;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class FavoriteCoffeeDialogFragment extends DialogFragment {

    private ArrayList<GoodsItem> goodsItemArrayList;
    private GoodsDatabase goodsDB;
    private GridView gridView;
    private String favoriteCaffe;
    private ArrayList<GoodsItem> arrayList;

    public FavoriteCoffeeDialogFragment(String favoriteCaffe) {
        this.favoriteCaffe = favoriteCaffe;
        goodsDB = new GoodsDatabase();

        switch (favoriteCaffe){
            case "투썸":
                goodsItemArrayList = goodsDB.getCafeItemArrayList("투썸");
                break;
            case "스타벅스":
                goodsItemArrayList = goodsDB.getCafeItemArrayList("스타벅스");
                break;
            case "공차":
                goodsItemArrayList = goodsDB.getCafeItemArrayList("공차");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        arrayList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_favorite_coffee_dialog, container, false);
        TextView menuText = view.findViewById(R.id.menuText);
        menuText.setText(favoriteCaffe);
        gridView = view.findViewById(R.id.menu_view);
        MenuAdapter menuAdapter = new MenuAdapter(requireContext(), goodsItemArrayList);
        gridView.setAdapter(menuAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.check_image);
                if(imageView.getVisibility() == View.GONE){
                    imageView.setVisibility(View.VISIBLE);
                    arrayList.add(goodsItemArrayList.get(position));
                }else{
                    imageView.setVisibility(View.GONE);
                    arrayList.remove(goodsItemArrayList.get(position));
                }
            }
        });

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    public ArrayList<GoodsItem> getGoodsItemArrayList(){
        return arrayList;
    }
}
