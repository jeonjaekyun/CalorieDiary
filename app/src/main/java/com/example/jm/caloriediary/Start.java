package com.example.jm.caloriediary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.database.Cursor;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JM on 2018-06-02.
 */

public class Start extends AppCompatActivity {

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    String nowDay;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.M.d");
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        getSupportActionBar().hide();
        myDBHelper = new MyDBHelper(this);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myDBHelper.getWritableDatabase();
                Cursor cursor;
                nowDay = getDay();
                try {
                    //새로운 날짜가 들어온다면 dateInfo 클래스에 넣어주고 끼니별 테이블에 정보를 삭제
                    sqlDB.execSQL("INSERT INTO dateInfo (date) VALUES ('" + nowDay + "');");
                    sqlDB.execSQL("DELETE FROM breakfast");
                    sqlDB.execSQL("DELETE FROM lunch");
                    sqlDB.execSQL("DELETE FROM dinner");
                    sqlDB.execSQL("DELETE FROM snack");
                }catch (Exception e){

                }

                cursor = sqlDB.rawQuery("SELECT * FROM userInfo",null);

                //userInfo에 정보 유무에 따라 시작하기 버튼 클릭시 나오는 화면이 달라짐
                while (cursor.moveToNext()){
                    key = cursor.getString(0);
                }

                if(key!=null){
                    Intent intent = new Intent(Start.this, Main.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Start.this, EnterUserInfo.class);
                    startActivity(intent);
                }
                cursor.close();
                sqlDB.close();
            }
        });
    }

    //오늘의 날짜를 가져오는 함수
    public String getDay() {
        mNow=System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}
