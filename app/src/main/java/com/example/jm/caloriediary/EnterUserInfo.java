package com.example.jm.caloriediary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by JM on 2018-06-02.
 */

//개인정보 입력 화면 구현
public class EnterUserInfo extends AppCompatActivity {

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Spinner spinnerGoal, spinnerGender, spinnerActivity;
    String goal, gender, activity;
    int height, goalWeight, reKcal;
    EditText editHeight, editGoalWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enteruserinfo);
        getSupportActionBar().setTitle("개인정보입력");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myDBHelper = new MyDBHelper(this);

        final String[] goals = {"체중감소", "체중유지", "체중증가"};
        final String[] genders = {"남자", "여자"};
        final String[] activitys = {"비활동적", "보통", "활동적", "매우활동적"};

        spinnerGoal = findViewById(R.id.spinnerGoal);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerActivity = findViewById(R.id.spinnerActivity);

        editHeight = findViewById(R.id.editHeight);
        editGoalWeight = findViewById(R.id.editGoalWeight);

        //spinner를 생성하고 spinner의 값을 가져오는 코드
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, goals);
        spinnerGoal.setAdapter(adapter1);

        spinnerGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                goal = goals[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genders);
        spinnerGender.setAdapter(adapter2);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = genders[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activitys);
        spinnerActivity.setAdapter(adapter3);

        spinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activity = activitys[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //옵션 메뉴를 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.enteruserinfo_menu, menu);
        return true;
    }

    //옵션 메뉴 아이템을 클릭시 실행
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent intent1 = new Intent(EnterUserInfo.this, Start.class);
                startActivity(intent1);
                break;
            case R.id.btnOk: // 개인정보입력 화면에서 받은 정보를 userInfo에 저장
                sqlDB = myDBHelper.getWritableDatabase();
                try {
                    height = Integer.parseInt(editHeight.getText().toString());
                    goalWeight = Integer.parseInt(editGoalWeight.getText().toString());

                    reKcal = reKcalCal(height, activity, goal);

                    sqlDB.execSQL("INSERT INTO userInfo VALUES ( '1','" + goal + "' , '" + gender + "', '" + activity + "','" + height + "','" + goalWeight + "','" + reKcal + "');");
                    sqlDB.close();

                    Intent intent2 = new Intent(EnterUserInfo.this, Main.class);
                    startActivity(intent2);
                }catch (Exception e){
                    Toast.makeText(EnterUserInfo.this, "정보를 모두 입력하세요",Toast.LENGTH_SHORT).show(); //정보가 입력되지 않으면 화면이 넘어가지 않고 해당 토스트를 띄움
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //권장 섭취 칼로리를 계산하는 함수 {(키 - 100) * 0.9) * 활동지수} + 목표
    public int reKcalCal(int height, String activity, String goal) {
        int result, x, y;

        if (activity == "비활동적") {
            x = 25;
        } else if (activity == "보통") {
            x = 30;
        } else if (activity == "활동적") {
            x = 35;
        } else {
            x = 40;
        }

        if (goal == "체중감소") {
            y = -400;
        } else if (goal == "체중유지") {
            y = 0;
        } else {
            y = 400;
        }

        Log.d("x", x + "");
        Log.d("y", y + "");
        Log.d("height", height + "");
        result = (int) ((((height - 100) * 0.9) * x) + y);

        Log.d("result", result + "");
        return result;
    }
}