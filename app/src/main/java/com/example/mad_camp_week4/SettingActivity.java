package com.example.mad_camp_week4;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    LinearLayout favorite_cafe_title;
    LinearLayout favorite_menu_title;
    LinearLayout coffee_time_title;
    TextView back;

    private TextView favoriteCaffe;
    private Calendar calendar;
    private TextView favoriteTime;
    private TextView favoriteMenu;

    private ArrayList<GoodsItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        favorite_cafe_title = findViewById(R.id.favorite_cafe_title);
        favorite_menu_title = findViewById(R.id.favorite_menu_title);
        coffee_time_title = findViewById(R.id.coffee_time_title);
        back = findViewById(R.id.setting_back);
        favoriteCaffe = findViewById(R.id.favorite_cafe);
        favoriteCaffe.setText(getSharedPreferences("caffe", MODE_PRIVATE).getString("caffe", ""));
        favoriteMenu = findViewById(R.id.favorite_menu);
        favoriteMenu.setText(getSharedPreferences("caffe", MODE_PRIVATE).getString("menu", ""));
        favoriteTime = findViewById(R.id.coffee_time);
        favoriteTime.setText(getSharedPreferences("caffe", MODE_PRIVATE).getString("menu", ""));

        calendar = Calendar.getInstance();

        back.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favorite_cafe_title.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = getLayoutInflater().inflate(R.layout.favorite_cafe_dialog, null);
                final LinearLayout twosome_logo = dialogView.findViewById(R.id.twosome_logo);
                final LinearLayout starbucks_logo = dialogView.findViewById(R.id.starbucks_logo);
                final LinearLayout gongcha_logo = dialogView.findViewById(R.id.gongcha_logo);

                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setView(dialogView);

                final AlertDialog dialog = builder.create();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                twosome_logo.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        favoriteCaffe.setText("투썸 플레이스");
                        SharedPreferences sharedPreferences = getSharedPreferences("caffe", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("caffe", "투썸");
                        editor.commit();
                        dialog.dismiss();
                    }
                });
                starbucks_logo.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        favoriteCaffe.setText("스타벅스");
                        SharedPreferences sharedPreferences = getSharedPreferences("caffe", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("caffe", "스타벅스");
                        editor.commit();
                        dialog.dismiss();
                    }
                });
                gongcha_logo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        favoriteCaffe.setText("공차");
                        SharedPreferences sharedPreferences = getSharedPreferences("caffe", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("caffe", "공차");
                        editor.commit();
                        dialog.dismiss();
                    }
                });
            }
        });
        favorite_menu_title.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                final FavoriteCoffeeDialogFragment favoriteCoffeeDialogFragment = new FavoriteCoffeeDialogFragment(getSharedPreferences("caffe", MODE_PRIVATE).getString("caffe", null));
                favoriteCoffeeDialogFragment.show(getSupportFragmentManager(), "dialog");
                getSupportFragmentManager().executePendingTransactions();
                favoriteCoffeeDialogFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        arrayList = favoriteCoffeeDialogFragment.getGoodsItemArrayList();
                        String favor = "";
                        for(int i = 0; i < arrayList.size(); i++){
                            favor += arrayList.get(i).getGoodName() +  ", ";
                        }
                        SharedPreferences.Editor editor = getSharedPreferences("caffe", MODE_PRIVATE).edit();
                        editor.putString("menu", favor);
                        favoriteMenu.setText(favor);
                    }
                });
            }
        });
        coffee_time_title.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: multiple options? how to CRUD!
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        String time = String.format("%d 시 %d 분", hour, min);
                        SharedPreferences sharedPreferences = getSharedPreferences("caffe", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("time", time);
                        editor.commit();
                        favoriteTime.setText(time);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
    }


}
