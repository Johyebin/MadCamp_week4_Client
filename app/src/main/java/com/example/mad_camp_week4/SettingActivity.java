package com.example.mad_camp_week4;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    LinearLayout favorite_cafe_title;
    LinearLayout favorite_menu_title;
    LinearLayout coffee_time_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        favorite_cafe_title = findViewById(R.id.favorite_cafe_title);
        favorite_menu_title = findViewById(R.id.favorite_menu_title);
        coffee_time_title = findViewById(R.id.coffee_time_title);

        favorite_cafe_title.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.favorite_cafe_dialog, null);
                final LinearLayout twosome_logo = dialogView.findViewById(R.id.twosome_logo);
                final LinearLayout starbucks_logo = dialogView.findViewById(R.id.starbucks_logo);
                final LinearLayout etc_logo = dialogView.findViewById(R.id.etc_logo);

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setView(dialogView);
                twosome_logo.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //TODO: favorite cafe -> global variable? move to main activity?
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        favorite_menu_title.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: two spinner or same as add BUT, MULTIPLE Choices! how to CRUD!
            }
        });
        coffee_time_title.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: multiple options? how to CRUD!
            }
        });
    }
}
