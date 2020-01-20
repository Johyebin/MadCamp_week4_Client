package com.example.mad_camp_week4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;


public class FragmentTwosome extends Fragment {

    GridView gridView;
    ArrayList<Menu> twosomeMenu = new ArrayList<>();

    public FragmentTwosome() {
        twosomeMenu.add(new Menu("아메리카노", R.drawable.twosome_americano));
        twosomeMenu.add(new Menu("에스프레소", R.drawable.twosome_espresso));
        twosomeMenu.add(new Menu("오늘의커피", R.drawable.twosome_today));
        twosomeMenu.add(new Menu("카페모카", R.drawable.twosome_mocha));
        twosomeMenu.add(new Menu("카푸치노", R.drawable.twosome_cappuccino));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coffee_add_twosome, container, false);
        gridView = view.findViewById(R.id.twosome_menu_view);
        MenuAdapter twosomeAdapter = new MenuAdapter(requireContext(), twosomeMenu);
        gridView.setAdapter(twosomeAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menu menu = (Menu) parent.getAdapter().getItem(position);

            }
        });

        return view;
    }
}
