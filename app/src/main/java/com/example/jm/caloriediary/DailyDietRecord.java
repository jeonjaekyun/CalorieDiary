package com.example.jm.caloriediary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by JM on 2018-06-02.
 */

//일 별 식단 기록 화면 구현 클래스
public class DailyDietRecord extends AppCompatActivity {
    CalendarView cv;
    TextView textDietRecord;
    SQLiteDatabase sqlDB;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailydietrecord);
        setTitle("일별 식단 기록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cv = findViewById(R.id.calendarView);
        textDietRecord = findViewById(R.id.textDietRecord);
        myDBHelper = new MyDBHelper(this);

        //날짜 클릭시 dateInfo 테이블에서 해당하는 날짜의 정보를 받아와 textDietRecord에 넣어주는 코드
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String selectDay = year + "." + (month + 1) + "." + day;
                Log.d("selectDay",selectDay);
                String reKal = "권장 칼로리 : ";
                String inKal = "섭취 칼로리 : ";
                String carbohydrate = "탄수화물 : ";
                String protein = "단백질 : ";
                String fat = "지방 : ";
                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM dateInfo WHERE date = '" + selectDay + "'", null);


                while (cursor.moveToNext()) {
                    reKal += cursor.getString(1);
                    inKal += cursor.getString(2);
                    carbohydrate += cursor.getString(3);
                    protein += cursor.getString(4);
                    fat += cursor.getString(5);
                }

                textDietRecord.setText(reKal + "\n\n" + inKal + "\n\n" + carbohydrate + "\n\n" + protein + "\n\n" + fat + "\n\n");

                cursor.close();
                sqlDB.close();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
