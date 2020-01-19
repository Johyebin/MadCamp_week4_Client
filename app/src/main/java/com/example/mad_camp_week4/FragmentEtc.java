package com.example.mad_camp_week4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentEtc extends Fragment {

    GridView gridView;
    ArrayList<Menu> etcMenu = new ArrayList<>();

    public FragmentEtc(){
        etcMenu.add(new Menu("데자와", R.drawable.etc_tejava));
        etcMenu.add(new Menu("다방커피", R.drawable.etc_dabang));
        etcMenu.add(new Menu("프렌치카페", R.drawable.etc_french));
        etcMenu.add(new Menu("핫식스", R.drawable.etc_hotsix));
        etcMenu.add(new Menu("우유속에커피", R.drawable.etc_milkcoffee));
        etcMenu.add(new Menu("몬스터", R.drawable.etc_monster));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.coffee_add_etc, container, false);
        gridView = view.findViewById(R.id.etc_menu_view);
        MenuAdapter etcAdapter = new MenuAdapter(requireContext(), etcMenu);
        gridView.setAdapter(etcAdapter);

        return view;
    }
}
