package com.example.mad_camp_week4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private List<CafeResult> lstCafeResult = new ArrayList<>(); // 오늘 날짜에 해당하는 row들을 다받아올것임
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.19.251:0180";
    private Gson gson = new Gson();
    private ImageButton refreshBtn;
    private String currentDate,currentTime; // 현재 시간을 저장하기 위한 전역변수
    private ArrayList<CafeResult> lstCafeSelect = new ArrayList<>(); // 일괄업로드된 항목을 식별하기 위해 뷰를 뿌려줄 배열
    private ArrayList<CafeResult> lstCafeUpload = new ArrayList<>();
    private final int RESULT_CODE = 1; //일괄 결제건에 대한 코드
    private final int RESULT_CODE_SETTING = 2;
    private final int RESULT_CODE_ADD = 3;
    private ArrayList<String> lstResultRowId = new ArrayList<>(); // 선택되지 않은 아이템을 삭제하기 위한 rowId를 저장하는 리스트
    private ArrayList<String> lstDeleteRowId = new ArrayList<>(); // 일괄 선택된 애들을 모두 지움
    private ImageView cup_of_coffee;

    private ImageButton setting_btn, add_btn;
    private GoodsDatabase goodsDatabase = new GoodsDatabase(); // GoodsItem을 조작하기 위한 객체 생성
    private ArrayList<GoodsItem> goodsList = new ArrayList<>(); // 뷰를 뿌리기 위한 GoodsItem을 저장할 배열
    public int caffeine, price; // 내부내부에서 접근시 access안되므로 변경하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // 해당 버튼을 누르면 서버에서 오늘 날짜의 데이터를 받아와야 함
        refreshBtn = findViewById(R.id.refresh_btn);
        cup_of_coffee = findViewById(R.id.cup_of_coffee);
        setting_btn = findViewById(R.id.setting_btn);
        add_btn = findViewById(R.id.add_btn);

        setInitial();

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInitial();
            }
        });

        cup_of_coffee.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.today_coffee_dialog, null);
                RecyclerView todayCoffeeView = dialogView.findViewById(R.id.today_coffee_view);
                TodayCoffeeAdapter todayCoffeeAdapter = new TodayCoffeeAdapter(goodsList);
                todayCoffeeView.setAdapter(todayCoffeeAdapter);
                TextView caffeineTextView = dialogView.findViewById(R.id.caffeine_content_edit);
                TextView priceTextView = dialogView.findViewById(R.id.price_edit);
                caffeineTextView.setText("" + caffeine);
                priceTextView.setText("" + price);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        setting_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        add_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddActivity.class), RESULT_CODE_ADD);
            }
        });

    }

    public void setInitial() {
        getCurrentDateTime(); // 현재 시간 세팅
        getDataWithCheck(); // 디비에서 사용자의 데이터를 검사하고 가져오기
    }

    // 오늘 날짜를 받아올 함수 // 창훈이 작품 (currentMillis는 배신하지 않아 )
    public void getCurrentDateTime() {
        long time = System.currentTimeMillis(); //한국 시간 기준
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd hhmm");
        String str = dayTime.format(new Date(time));
        String arr[] = str.split(" ");
        currentDate = arr[0];
        currentTime = arr[1];
    }

    public void getDataWithCheck() {
        HashMap<String, String> map = new HashMap<>();
        map.put("tradDate", currentDate);

        Call<ArrayList<CafeResult>> call = retrofitInterface.executePull(map);
        call.enqueue(new Callback<ArrayList<CafeResult>>() {
            @Override
            public void onResponse(Call<ArrayList<CafeResult>> call, Response<ArrayList<CafeResult>> response) {
                if (response.code() == 200) {
                    lstCafeResult.clear(); // 원래 저장되어 있던 배열 비우고,오늘 치꺼 모두 받음
                    ArrayList<CafeResult> tmpList = response.body();
                    for (int i = 0; i < tmpList.size(); i++) {
                        lstCafeResult.add(tmpList.get(i));
                    }
                    // 데이터를 세팅하고 해당 리스트를 가지고 있는 어댑터가 그린 뷰를 update하는 코드
                    checkMultipleFlag();
                    setContents();
                    // updateView();
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), "Download Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CafeResult>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // 받아온 데이터에 대해 일괄건이 있는지 확인하고, 사용자에게 확인받기 위한 체크박스리스트뷰를 띄워줄 함수
    public void checkMultipleFlag() {
        lstDeleteRowId.clear();
        lstCafeSelect.clear();
        // 오늘 날짜의 데이터를 다 받아왔으면 일괄건이 있는지 확인하기
        for (int i = 0; i < lstCafeResult.size(); i++) {
            if (Integer.parseInt(lstCafeResult.get(i).getMultipleFlag()) == 1) {
                lstCafeSelect.add(lstCafeResult.get(i)); // 사용자가 선택 할 수 있는 리스트뷰를 그릴 리스트에 해당 항목을추가하기 // 커스텀 리스트뷰를 만들고 모두 삭제함
                lstDeleteRowId.add(lstCafeResult.get(i).getRowId());
            }
        }

        // multiple flag가 없으면 리턴
        if (lstDeleteRowId.size() == 0) {
            Toast.makeText(MainActivity.this, "Nothing to Select", Toast.LENGTH_SHORT).show();
            return;
        }
        // 해당 리스트에 저장된 모든 데이터를 지움
        deleteRow(lstDeleteRowId);

        Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
        intent.putExtra("initialArray", lstCafeSelect);
        startActivityForResult(intent, RESULT_CODE);

    }

    // 일괄건이 없거나 일괄건을 처리하고 돌아온 후, 결과값으로 리스트 세팅 + 카페인 수치 계산
    // 변경해서 올린 디비로 조작
    public void setContents(){
        HashMap<String,String> map = new HashMap<>();
        map.put("tradDate",currentDate);
        Call<ArrayList<CafeResult>> call = retrofitInterface.executePull(map);
        call.enqueue(new Callback<ArrayList<CafeResult>>() {
            @Override
            public void onResponse(Call<ArrayList<CafeResult>> call, Response<ArrayList<CafeResult>> response) {
                if (response.code() == 200) {
                    lstCafeResult.clear(); // 원래 저장되어 있던 배열 비우고,오늘 치꺼 모두 받음
                    goodsList.clear();

                    ArrayList<CafeResult> tmpList = response.body();
                    for (int i = 0; i < tmpList.size(); i++) {
                        lstCafeResult.add(tmpList.get(i));
                    }
                    for (int i = 0; i < lstCafeResult.size(); i++) {
                        // 어댑터가 지녀야 할 배열 == goodsList
                        GoodsItem goodsItem = goodsDatabase.findGoods(lstCafeResult.get(i).getGoodId());
                        goodsList.add(goodsItem);
                    }
                    caffeine = goodsDatabase.getCaffeineContent(goodsList);
                    price = goodsDatabase.getPrice(goodsList);
                    if(caffeine>400){
                        cup_of_coffee.setImageResource(R.drawable.coffee_spill);
                    } else {
                        cup_of_coffee.setImageResource(R.drawable.round_cup);
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), "Download Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CafeResult>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // 선택된 결과리스트를 받아와서 해당 객체의 플래그를 0으로 세팅한 후 다시 업로드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lstResultRowId.clear();

        if (resultCode != RESULT_OK) {
            Toast.makeText(MainActivity.this, "List Selection Failed", Toast.LENGTH_LONG).show();
            return;
        }

        if (requestCode == RESULT_CODE && data != null) {
            lstCafeUpload.clear();

            Intent intent = data;
            ArrayList<String> tmpList = intent.getStringArrayListExtra("resultArray"); // 선택된 것들은 flag를 0으로 바꾸고 올리기
            for (String s : tmpList)
                lstResultRowId.add(s); // update

            for (int i = 0; i < lstResultRowId.size(); i++) {
                String tmpId = lstResultRowId.get(i);
                for (int j = 0; j < lstCafeSelect.size(); j++) {
                    if (lstCafeSelect.get(j).getRowId().equals(tmpId)) {
                        lstCafeSelect.get(j).setMultipleFlag("0");
                        lstCafeUpload.add(lstCafeSelect.get(j));
                    }
                }
            }
            upLoadRow(lstCafeUpload);// 변경한 row들을 다시 서버에 업로드
            setContents();

        }
        if (requestCode == RESULT_CODE_ADD && data != null) {
           // Toast.makeText(MainActivity.this, "Added: ".concat(data.getStringExtra("goodID")), Toast.LENGTH_LONG).show();
            ArrayList<CafeResult> tmpList = new ArrayList<>();
            String tmpString = data.getStringExtra("goodId");
            GoodsItem tmpGoods = goodsDatabase.findGoods(tmpString);
            CafeResult tmpCafeResult = new CafeResult();
            tmpCafeResult.setRowId("-1");
            getCurrentDateTime();
            tmpCafeResult.setTradDate(currentDate);
            tmpCafeResult.setTradTime("-1");
            tmpCafeResult.setDrinkTime(currentTime);
            tmpCafeResult.setGoodId(tmpGoods.getGoodId());
            tmpCafeResult.setGoodName(tmpGoods.getGoodName());
            tmpCafeResult.setCafeName(tmpGoods.getCafeName());
            tmpCafeResult.setMultipleFlag("0");
            tmpList.add(tmpCafeResult);
            upLoadRow(tmpList);
            setContents();
        }
    }

    int upCount; // 업로드 한 갯수를 셀 변수

    public void upLoadRow(ArrayList<CafeResult> argsList) {
        upCount = 0;
        ArrayList<CafeResult> tmpList = new ArrayList<>();
        for (CafeResult c : argsList)
            tmpList.add(c);

        // 선택된 것의 갯수만큼 업로드
        for (int i = 0; i < tmpList.size(); i++) {
            HashMap<String, String> map = new HashMap<>();
            // 요청 객체 ( 입력값을 ) 보냄
            map.put("rowId", tmpList.get(i).getRowId());
            map.put("tradDate", tmpList.get(i).getTradDate());
            map.put("tradTime", tmpList.get(i).getTradTime());
            map.put("drinkTime", tmpList.get(i).getDrinkTime());
            map.put("goodId", tmpList.get(i).getGoodId());
            map.put("goodName", tmpList.get(i).getGoodName());
            map.put("cafeName", tmpList.get(i).getCafeName());
            map.put("multipleFlag", tmpList.get(i).getMultipleFlag());
            Call<Void> call = retrofitInterface.executePush(map);

            // 해당 요청을 서버 프로그램의 req출력 큐에 넣음
            call.enqueue(new Callback<Void>() { // 처리되어 결과가 오면 콜백함수가 실행 // 등록은 서버로부터 돌려받을 값이 없기 때문에 Void임
                             @Override
                             // 서버로부터 처리는 잘된경우 // 클라이언트는 상태코드로 처리결과를 확인
                             public void onResponse(Call<Void> call, Response<Void> response) { // 요청과 받은 값
                                 if (response.code() == 200) {
                                     upCount++;
                                 } else if (response.code() == 400) {
                                     Toast.makeText(MainActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                                 }
                             }

                             // 예기치 못한 오류
                             @Override
                             public void onFailure(Call<Void> call, Throwable t) {
                                 Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }
            );

        }
        if (upCount == tmpList.size())
            Toast.makeText(MainActivity.this, "Uploaded successfully", Toast.LENGTH_LONG).show();
    }


    int deleteCount; // 갯수를 셀 변수

    public void deleteRow(ArrayList<String> argArrayList) {
        ArrayList<String> tmpList = new ArrayList<>();
        for (String s : argArrayList)
            tmpList.add(s);
        deleteCount = 0;
        for (int i = 0; i < tmpList.size(); i++) {
            HashMap<String, String> map = new HashMap<>();
            // 요청 객체 ( 입력값을 ) 보냄
            map.put("rowId", tmpList.get(i));
            Call<Void> call = retrofitInterface.executeDelete(map);

            // 해당 요청을 서버 프로그램의 req출력 큐에 넣음
            call.enqueue(new Callback<Void>() { // 처리되어 결과가 오면 콜백함수가 실행 // 삭제는 서버로부터 돌려받을 값이 없기 때문에 Void임
                             @Override
                             // 서버로부터 처리는 잘된경우 // 클라이언트는 상태코드로 처리결과를 확인
                             public void onResponse(Call<Void> call, Response<Void> response) { // 요청과 받은 값
                                 if (response.code() == 200) {
                                     deleteCount++;
                                 } else if (response.code() == 400) {
                                     Toast.makeText(MainActivity.this, "Delete Failed", Toast.LENGTH_LONG).show();
                                 }
                             }

                             // 예기치 못한 오류
                             @Override
                             public void onFailure(Call<Void> call, Throwable t) {
                                 Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }
            );

        }
        if (deleteCount == tmpList.size())
            Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_LONG).show();
    }
}
