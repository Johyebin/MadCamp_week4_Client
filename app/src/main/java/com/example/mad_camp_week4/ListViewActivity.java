package com.example.mad_camp_week4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity implements Serializable {
    private ListView listview;
    private ArrayAdapter adapter;
    private ArrayList<CafeResult> items = new ArrayList<>();
    private ArrayList<String> lstRowId = new ArrayList<>() ; // 객체배열의 rowid를 저장
    private ArrayList<String> lstGoodName = new ArrayList<>() ; // 객체배열의 name을 저장 user에게 보여지는 정보, 연관된 id로 디비에서 삭제해야 함
    private ArrayList<String> lstResultRowId = new ArrayList<>() ; // 선택되지 않은 rowId들을 가진 배열 메인에 전달해야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

            items.clear();

        // Main에서 넘겨받은 배열을 저장
        Intent intent = getIntent();
        items = (ArrayList<CafeResult>)intent.getSerializableExtra("initialArray");

            lstRowId.clear();
            lstGoodName.clear();

        for(int i=0;i<items.size();i++){
            lstRowId.add(items.get(i).getRowId());
            lstGoodName.add(items.get(i).getGoodName());
        }

        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록 만듦.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, lstGoodName) ;

        // listview 생성 및 adapter 지정.
        listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        // 사용자가 선택하지 않은 것을 디비에서 삭제하기 위해 판별하고 배열을 메인으로 넘겨주어야 함
        Button doneButton = (Button)findViewById(R.id.done_btn) ;
        doneButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = adapter.getCount() ;
                lstResultRowId.clear();

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        lstResultRowId.add(lstRowId.get(i));
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("resultArray",lstResultRowId);
                setResult(RESULT_OK,intent);
//                // 모든 선택 상태 초기화.
//                listview.clearChoices() ;
//                adapter.notifyDataSetChanged();
                finish();
            }
        }) ;
    }
}
