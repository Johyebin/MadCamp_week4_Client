package com.example.mad_camp_week4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentGongcha extends Fragment {

    GridView gridView;
    ArrayList<Menu> gongchaMenu = new ArrayList<>();

    public FragmentGongcha(){
        gongchaMenu.add(new Menu("아메리카노", R.drawable.starbucks_americano));
        gongchaMenu.add(new Menu("자바칩\n프라푸치노", R.drawable.starbucks_javachip));
        gongchaMenu.add(new Menu("바닐라크림\n콜드브루", R.drawable.starbucks_vanilla));
        gongchaMenu.add(new Menu("카페모카", R.drawable.starbucks_mocha));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.coffee_add_gongcha, container, false);

        gridView = view.findViewById(R.id.gongcha_menu_view);
        MenuAdapter gongchaAdapter = new MenuAdapter(requireContext(), gongchaMenu);
        gridView.setAdapter(gongchaAdapter);

        return view;
    }
}
