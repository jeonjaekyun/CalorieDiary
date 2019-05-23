package com.example.jm.caloriediary;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JM on 2018-06-02.
 */

//식단기록, 마이페이지 화면을 Tabhost로 구현
public class Main extends TabActivity {

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    String rekcal, inkcal, carbohydrate, protein, fat;
    TextView textReKcal, textInKcal, textCarbohydrate, textProtein, textFat;
    TextView nowDay;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.M.d");

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("메인");
        myDBHelper = new MyDBHelper(this);

        textReKcal = findViewById(R.id.textReKcal);
        textInKcal = findViewById(R.id.textInKcal);
        textCarbohydrate = findViewById(R.id.textCarbohydrate);
        textProtein = findViewById(R.id.textProtein);
        textFat = findViewById(R.id.textFat);

        nowDay = findViewById(R.id.nowDay);
        nowDay.setText(getDay());

        sqlDB = myDBHelper.getReadableDatabase();
        //main이 oncreate될때마다 dateInfo의 데이터를 업데이트해줌
        sqlDB.execSQL("UPDATE dateInfo SET rekcal=(SELECT rekcal FROM userInfo WHERE id = 1), inkcal=0, carbohydrate=0, protein=0, fat=0  WHERE date = '" + getDay() + "'");
        //ifnull(x,0) x의 값이 null일 경우 0을 반환해줌
        sqlDB.execSQL("update dateInfo set inkcal = (ifnull((select sum(kcal) from breakfast),0)+ifnull((select sum(kcal) from lunch),0)+ifnull((select sum(kcal) from dinner),0)+" +
                "ifnull((select sum(kcal) from snack),0)) WHERE date = '" + getDay() + "'");
        sqlDB.execSQL("update dateInfo set carbohydrate = (ifnull((select sum(carbohydrate) from breakfast),0)+ifnull((select sum(carbohydrate) from lunch),0)+ifnull((select sum(carbohydrate) from dinner),0)+" +
                "ifnull((select sum(carbohydrate) from snack),0))WHERE date = '" + getDay() + "'");
        sqlDB.execSQL("update dateInfo set protein =  (ifnull((select sum(protein) from breakfast),0)+ifnull((select sum(protein) from lunch),0)+ifnull((select sum(protein) from dinner),0)+" +
                "ifnull((select sum(protein) from snack),0)) WHERE date = '" + getDay() + "'");
        sqlDB.execSQL("update dateInfo set fat = (ifnull((select sum(fat) from breakfast),0)+ifnull((select sum(fat) from lunch),0)+ifnull((select sum(fat) from dinner),0)+" +
                "ifnull((select sum(fat) from snack),0)) WHERE date = '" + getDay() + "'");
        Cursor cursor;
        //식단 기록 화면에서 보여줄 권장칼로리, 섭취칼로리, 탄수화물, 단백질, 지방을 가져옴
        cursor = sqlDB.rawQuery("SELECT * FROM dateInfo WHERE date = '" + getDay() + "';", null);

        while (cursor.moveToNext()) {
            rekcal = cursor.getString(1);
            inkcal = cursor.getString(2);
            carbohydrate = cursor.getString(3);
            protein = cursor.getString(4);
            fat = cursor.getString(5);
        }

        textReKcal.setText(rekcal);
        textInKcal.setText(inkcal);
        textCarbohydrate.setText(carbohydrate);
        textProtein.setText(protein);
        textFat.setText(fat);
        cursor.close();
        sqlDB.close();

        TabHost tabHost = getTabHost();

        TabSpec tabSpec1 = tabHost.newTabSpec("record").setIndicator("식단기록");
        tabSpec1.setContent(R.id.mainRecord);
        tabHost.addTab(tabSpec1);

        TabSpec tabSpec2 = tabHost.newTabSpec("personal").setIndicator("마이페이지");
        tabSpec2.setContent(R.id.mainPersonal);
        tabHost.addTab(tabSpec2);

        tabHost.setCurrentTab(0);

        Intent inIntent = getIntent();
        int mainNo = inIntent.getIntExtra("mainNo", 0);

        if (mainNo == 0) {
            tabHost.setCurrentTab(0);
        } else if (mainNo == 1) {
            tabHost.setCurrentTab(1);
        }

        String[] meal = {"아침", "점심", "저녁", "간식"};
        String[] personal = {"개인 정보", "일 별 식단 기록"};

        ListView list1 = findViewById(R.id.listView1);
        ListView list2 = findViewById(R.id.listView2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meal);
        list1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, personal);
        list2.setAdapter(adapter2);

        //선택되는 아이템에 따라 mealNo을 Meal에 넘겨줌
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Main.this, Meal.class);
                intent.putExtra("mealNo", i);
                startActivity(intent);
            }
        });


        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent1 = new Intent(Main.this, UserInfo.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(Main.this, DailyDietRecord.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }

    //오늘 날짜를 받아오는 함수
    public String getDay() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}
