package com.example.mad_camp_week4;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class AddActivity extends AppCompatActivity {
    ViewPager viewPager;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_add_activity);

        viewPager = findViewById(R.id.coffeeAddPager);
        viewPager.setOffscreenPageLimit(4);
        AddPagerAdapter addPagerAdapter = new AddPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(addPagerAdapter);
        back = findViewById(R.id.add_back);

        back.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
