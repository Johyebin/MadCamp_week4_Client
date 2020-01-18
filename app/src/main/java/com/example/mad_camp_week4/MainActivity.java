package com.example.mad_camp_week4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView cup_of_coffee;
    ImageButton setting_btn;
    ImageButton refresh_btn;
    ImageButton add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cup_of_coffee = findViewById(R.id.cup_of_coffee);
        setting_btn = findViewById(R.id.setting_btn);
        refresh_btn = findViewById(R.id.refresh_btn);
        add_btn = findViewById(R.id.add_btn);

        cup_of_coffee.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                View dialogView = getLayoutInflater().inflate(R.layout.today_coffee_dialog, null);
                //final EditText nameEditText = (EditText)dialogView.findViewById(R.id.name);
                //final EditText NicknameEditText = (EditText)dialogView.findViewById(R.id.nickname);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        setting_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        add_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.coffee_add_dialog, null);
                //TODO: how to implement viewpager inside Dialog!

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

    }
}
