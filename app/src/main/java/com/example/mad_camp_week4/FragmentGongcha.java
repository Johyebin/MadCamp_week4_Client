package com.example.mad_camp_week4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FragmentGongcha extends Fragment {

    private GridView gridView;
    private GoodsDatabase goodsDB;
    private ArrayList<GoodsItem> gongchaMenu;
    private View dialogView;
    private ImageView menuImage;
    private TextView menuName;
    private TextView menuCaffeineContent;
    private TextView menuPrice;
    private Button addButton;

    public FragmentGongcha(){
        goodsDB = new GoodsDatabase();
        gongchaMenu = goodsDB.getCafeItemArrayList("공차");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.coffee_add_gongcha, container, false);

        gridView = view.findViewById(R.id.gongcha_menu_view);
        MenuAdapter gongchaAdapter = new MenuAdapter(requireContext(), gongchaMenu);
        gridView.setAdapter(gongchaAdapter);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("caffe", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("caffe", "").equals("공차")){
            String str = sharedPreferences.getString("menu", "");
            String[] array = str.split(", "); // fav메뉴 이름 배열
            ArrayList<String> goodIDArray = new ArrayList<>(); //fav메뉴에 해당하는 goodID 배열
            for(String s: array){
                //Log.wtf("PARSED", s);
                for(int i=0;i<gongchaMenu.size();i++){
                    if(s.equals(gongchaMenu.get(i).getGoodName())){
                        goodIDArray.add(gongchaMenu.get(i).getGoodId());
                        gongchaMenu.remove(i);
                    }
                }
            }
            //TODO: 커피 쏟기 컨테스트
            //TODO: coffee_info_dialog에도 별 띄우기
           for(String id: goodIDArray){
                //Log.wtf("GOODID", goodIDArray.toString());
               goodsDB.findGoods(id).setIsFavorite(true);
               gongchaMenu.add(0, goodsDB.findGoods(id));
            }
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final GoodsItem goodsItem = (GoodsItem) parent.getAdapter().getItem(position);
                dialogView = getLayoutInflater().inflate(R.layout.coffee_info_dialog, null);
                menuImage = dialogView.findViewById(R.id.menu_image);
                menuName = dialogView.findViewById(R.id.menu_name);
                menuCaffeineContent = dialogView.findViewById(R.id.menu_caffeine_content);
                menuPrice = dialogView.findViewById(R.id.menu_price);
                addButton = dialogView.findViewById(R.id.coffee_add);

                menuImage.setImageResource(goodsItem.getImgSrc());
                menuName.setText(goodsItem.getGoodName());
                menuCaffeineContent.setText(goodsItem.getCaffeineContent().concat("mg"));
                menuPrice.setText(goodsItem.getPrice().concat("원"));

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);

                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                addButton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        Intent intent = new Intent();
                        intent.putExtra("goodId", goodsItem.getGoodId());
                        getActivity().setResult(RESULT_OK, intent);

                        getActivity().finish();
                    }
                });
            }
        });

        return view;
    }
}
