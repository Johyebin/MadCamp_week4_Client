package com.example.mad_camp_week4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("caffe", Context.MODE_PRIVATE);
        String favorite = sharedPreferences.getString("menu", "");
        String[] array = favorite.split(", ");
        for(String s : array){
            Log.wtf("PARSED", s);
        }

        if (array.length != 0) {
            for (String s : array) {
                for (int i = 0; i < goodsItemArrayList.size(); i++) {
                    if (s.equals(goodsItemArrayList.get(i).getGoodName())) {
                        goodsItemArrayList.get(i).setIsChecked(true); // 최애메뉴라면 미리 isChecked = true
                        arrayList.add(goodsItemArrayList.get(i)); // 반환해줄 arrayList에도 추가해주기
                    }
                }
            }
        }

        final MenuAdapter menuAdapter = new MenuAdapter(requireContext(), goodsItemArrayList);
        gridView.setAdapter(menuAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(goodsItemArrayList.get(position).getIsChecked()){
                    goodsItemArrayList.get(position).setIsChecked(false); // isChecked를 false로
                    arrayList.remove(goodsItemArrayList.get(position));
                } else {
                    goodsItemArrayList.get(position).setIsChecked(true); // isChecked를 true로
                    arrayList.add(goodsItemArrayList.get(position));
                }
                menuAdapter.notifyDataSetChanged(); // isChecked 변동 사항 notify
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
