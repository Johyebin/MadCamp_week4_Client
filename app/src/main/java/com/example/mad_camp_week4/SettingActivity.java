package com.example.mad_camp_week4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {
    LinearLayout favorite_cafe_title;
    LinearLayout favorite_menu_title;
    LinearLayout coffee_time_title;
    TextView back;

    private TextView favoriteCaffe;
    private Calendar calendar;
    private TextView favoriteTime;
    private TextView favoriteMenu;
    private CheckBox checkBox;

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
        favoriteTime.setText(getSharedPreferences("caffe", MODE_PRIVATE).getString("time", ""));

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
                final String prev = getSharedPreferences("caffe", MODE_PRIVATE).getString("caffe", "");
                //변경 전 최애 카페

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                twosome_logo.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        favoriteCaffe.setText("투썸 플레이스");
                        SharedPreferences sharedPreferences = getSharedPreferences("caffe", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if(!prev.equals("투썸")){ // 최애 카페 변경됐을 경우
                            editor.putString("menu", "");
                            Log.wtf("FAVCAFE", "changed@");
                            favoriteMenu.setText("");
                        }
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
                        if(!prev.equals("스타벅스")){ // 최애 카페 변경됐을 경우
                            editor.putString("menu", "");
                            Log.wtf("FAVCAFE", "changed@");
                            favoriteMenu.setText("");
                        }
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
                        if(!prev.equals("공차")){ // 최애 카페 변경됐을 경우
                            editor.putString("menu", "");
                            Log.wtf("FAVCAFE", "changed@");
                            favoriteMenu.setText("");
                        }
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
                final SharedPreferences sharedPreferencesIn = getSharedPreferences("caffe", MODE_PRIVATE);
                final FavoriteCoffeeDialogFragment favoriteCoffeeDialogFragment = new FavoriteCoffeeDialogFragment(sharedPreferencesIn.getString("caffe", null));
                favoriteCoffeeDialogFragment.show(getSupportFragmentManager(), "dialog");
                getSupportFragmentManager().executePendingTransactions();
                favoriteCoffeeDialogFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        arrayList = favoriteCoffeeDialogFragment.getGoodsItemArrayList();
                        Log.wtf("DISMISSED", "reach here");
                        String favor = "";
                        if(arrayList.size() != 0){
                            for(int i = 0; i < arrayList.size(); i++){
                                favor += arrayList.get(i).getGoodName() +  ", ";
                            }
                            favor = favor.substring(0, favor.length()-2);
                            SharedPreferences.Editor editor = sharedPreferencesIn.edit();
                            editor.putString("menu", favor);
                            editor.commit();
                            favoriteMenu.setText(favor);
                        }
                    }
                });
                favoriteCoffeeDialogFragment.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
        coffee_time_title.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        String time = String.format("%d 시 %d 분", hour, min);
                        SharedPreferences sharedPreferences = getSharedPreferences("caffe", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("time", time);
                        editor.commit();
                        favoriteTime.setText(time);

                        Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                        intent.putExtra("alarm", getSharedPreferences("caffe", MODE_PRIVATE).getBoolean("alarm", false));
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                        String temp;
                        if(calendar.get(Calendar.MONTH) >= 9){
                            temp = calendar.get(Calendar.YEAR) + "" + (calendar.get(Calendar.MONTH)+1) + "" + calendar.get(Calendar.DAY_OF_MONTH) + "" + hour + "" + min + "00";
                        }else{
                            temp = calendar.get(Calendar.YEAR) + "0" + (calendar.get(Calendar.MONTH)+1) + "" + calendar.get(Calendar.DAY_OF_MONTH) + "" + hour + "" + min + "00";
                        }
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = null;
                        try {
                            date = dateFormat.parse(temp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(date);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });

        checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("caffe", MODE_PRIVATE).edit();
                    editor.putBoolean("alarm", true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("caffe", MODE_PRIVATE).edit();
                    editor.putBoolean("alarm", false);
                    editor.commit();
                }
            }
        });
    }


}

