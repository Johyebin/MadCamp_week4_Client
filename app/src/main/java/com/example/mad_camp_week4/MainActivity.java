package com.example.mad_camp_week4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.ParseException;
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

public class MainActivity extends AppCompatActivity {
    private final String userId = "01"; // 해당 유저의 아이디
    private List<CafeResult> lstCafeResult = new ArrayList<>(); // 오늘 날짜에 해당하는 row들을 다받아올것임
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL="http://192.249.19.251:0180";
    private Gson gson = new Gson();
    private ImageButton refreshBtn;
    private String currentDate; // 현재 시간을 저장하기 위한 전역변수
    private ArrayList<CafeResult> lstCafeSelect = new ArrayList<>(); // 일괄업로드된 항목을 식별하기 위해 뷰를 뿌려줄 배열
    private ArrayList<CafeResult> lstCafeUpload = new ArrayList<>();
    private final int RESULT_CODE = 1;
    private ArrayList<String> lstResultRowId = new ArrayList<>(); // 선택되지 않은 아이템을 삭제하기 위한 rowId를 저장하는 리스트
    private ArrayList<String> lstDeleteRowId = new ArrayList<>(); // 일괄 선택된 애들을 모두 지움

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
        // resultTextView = findViewById(R.id.textView);
        refreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setInitial();
            }
        });

    }
    public void setInitial(){
        getCurrentDate(); // 현재 시간 세팅
        getDataWithCheck(); // 디비에서 사용자의 데이터를 검사하고 가져오기
    }

    // 오늘 날짜를 받아올 함수 // 창훈이 작품 (currentMillis는 배신하지 않아 )
    public void getCurrentDate(){
        long time = System.currentTimeMillis() + 9 * 60 * 60 * 1000;
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd");
        String str = dayTime.format(new Date(time));
        currentDate = str;
    }

    public void getDataWithCheck(){
        HashMap<String,String> map = new HashMap<>();
        map.put("tradDate",currentDate);

        Call<ArrayList<CafeResult>> call = retrofitInterface.executePull(map);
        call.enqueue(new Callback<ArrayList<CafeResult>>() {
            @Override
            public void onResponse(Call<ArrayList<CafeResult>> call, Response<ArrayList<CafeResult>> response) {
                if (response.code() == 200) {
                    lstCafeResult.clear(); // 원래 저장되어 있던 배열 비우고,오늘 치꺼 모두 받음
                    ArrayList<CafeResult> tmpList = response.body();
                    for(int i=0;i<tmpList.size();i++) {
                        lstCafeResult.add(tmpList.get(i));
                    }
                    // 데이터를 세팅하고 해당 리스트를 가지고 있는 어댑터가 그린 뷰를 update하는 코드
                    // updateView();
                    checkMultipleFlag();
                }
                else if( response.code() == 404){
                    Toast.makeText(getApplicationContext(),"Download Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CafeResult>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // 받아온 데이터에 대해 일괄건이 있는지 확인하고, 사용자에게 확인받기 위한 체크박스리스트뷰를 띄워줄 함수
    public void checkMultipleFlag(){
        lstDeleteRowId.clear();
        lstCafeSelect.clear();
        // 오늘 날짜의 데이터를 다 받아왔으면 일괄건이 있는지 확인하기
       for(int i=0;i<lstCafeResult.size();i++){
            if (Integer.parseInt(lstCafeResult.get(i).getMultipleFlag()) == 1) {
                lstCafeSelect.add(lstCafeResult.get(i)); // 사용자가 선택 할 수 있는 리스트뷰를 그릴 리스트에 해당 항목을추가하기 // 커스텀 리스트뷰를 만들고 모두 삭제함
                lstDeleteRowId.add(lstCafeResult.get(i).getRowId());
            }
        }
                // 해당 리스트에 저장된 모든 데이터를 지움
                deleteRow(lstDeleteRowId);

                Intent intent = new Intent(MainActivity.this,ListViewActivity.class);
                intent.putExtra("initialArray",lstCafeSelect);
                startActivityForResult(intent,RESULT_CODE);

    }

    // 선택된 결과리스트를 받아와서 해당 객체의 플래그를 0으로 세팅한 후 다시 업로드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lstResultRowId.clear();

        if(resultCode != RESULT_OK) {
            Toast.makeText(MainActivity.this,"List Selection Failed", Toast.LENGTH_LONG).show();
            return;
        }

        if(requestCode == RESULT_CODE && data != null){
            lstCafeUpload.clear();

            Intent intent = data;
            ArrayList<String> tmpList = intent.getStringArrayListExtra("resultArray"); // 선택된 것들은 flag를 0으로 바꾸고 올리기
            for(String s: tmpList)
                lstResultRowId.add(s);

            for(int i=0;i<lstResultRowId.size();i++) {
                String tmpId = lstResultRowId.get(i);
                for(int j=0;j<lstCafeSelect.size();j++){
                    if(lstCafeSelect.get(j).getRowId().equals(tmpId)) {
                        lstCafeSelect.get(j).setMultipleFlag("0");
                        lstCafeUpload.add(lstCafeSelect.get(j));
                    }
                }
            }
            upLoadRow(lstCafeUpload);// 변경한 row들을 다시 서버에 업로드
        }
    }

    int upCount ; // 업로드 한 갯수를 셀 변수
    public void upLoadRow(ArrayList<CafeResult> argsList){
        upCount = 0;
        ArrayList<CafeResult> tmpList = new ArrayList<>();
        for(CafeResult c: argsList)
            tmpList.add(c);

            // 선택된 것의 갯수만큼 업로드
            for(int i=0;i<tmpList.size();i++) {
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
                                     if(response.code() == 200){
                                         upCount++;
                                     }else if(response.code() == 400){
                                          Toast.makeText(MainActivity.this,"Upload Failed",Toast.LENGTH_LONG).show();
                                     }
                                 }
                                 // 예기치 못한 오류
                                 @Override
                                 public void onFailure(Call<Void> call, Throwable t) {
                                     Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                                 }
                             }
                );

            }
            if(upCount == tmpList.size())
                Toast.makeText(MainActivity.this,"Uploaded successfully",Toast.LENGTH_LONG).show();
        }


    int deleteCount ; // 갯수를 셀 변수
    public void deleteRow(ArrayList<String> argArrayList){
        ArrayList<String> tmpList = new ArrayList<>();
        for(String s: argArrayList)
            tmpList.add(s);
        deleteCount = 0;
        for(int i=0;i<tmpList.size();i++) {
            HashMap<String, String> map = new HashMap<>();
            // 요청 객체 ( 입력값을 ) 보냄
            map.put("rowId",tmpList.get(i));
            Call<Void> call = retrofitInterface.executeDelete(map);

            // 해당 요청을 서버 프로그램의 req출력 큐에 넣음
            call.enqueue(new Callback<Void>() { // 처리되어 결과가 오면 콜백함수가 실행 // 삭제는 서버로부터 돌려받을 값이 없기 때문에 Void임
                             @Override
                             // 서버로부터 처리는 잘된경우 // 클라이언트는 상태코드로 처리결과를 확인
                             public void onResponse(Call<Void> call, Response<Void> response) { // 요청과 받은 값
                                 if(response.code() == 200){
                                     deleteCount++;
                                 }else if(response.code() == 400){
                                     Toast.makeText(MainActivity.this,"Delete Failed",Toast.LENGTH_LONG).show();
                                 }
                             }
                             // 예기치 못한 오류
                             @Override
                             public void onFailure(Call<Void> call, Throwable t) {
                                 Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }
            );

        }
        if(deleteCount == tmpList.size())
            Toast.makeText(MainActivity.this,"Delete successfully",Toast.LENGTH_LONG).show();
    }

    public void updateView(){
        // 새로고침해서 얻어온 데이터로 뷰를 다시 그리는 코드
    }
}
