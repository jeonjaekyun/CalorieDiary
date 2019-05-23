package com.example.jm.caloriediary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.database.Cursor;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import java.util.ArrayList;
/**
 * Created by JM on 2018-06-02.
 */

//끼니별 섭취 음식 목록을 보여주는 화면 구현
public class Meal extends AppCompatActivity {
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myHelper = new MyDBHelper(this);

        //main에서 클릭하는 리스트에 따라 mealNo 을 받아온다
        Intent inIntent = getIntent();
        final int mealNo = inIntent.getIntExtra("mealNo",0);

        Button btnFoodAdd = findViewById(R.id.btnFoodAdd);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;

        //받아온 mealNo을 기준으로 해당하는 끼니로 타이틀 set 과 테이블을 받아옴
        switch (mealNo){
            case 0 :
                setTitle("아침");
                cursor = sqlDB.rawQuery("SELECT * FROM breakfast",null);
                selectMealInfo(cursor);  //화면에 리사이클러뷰를 보여주는 화면
                break;
            case 1 :
                setTitle("점심");
                cursor = sqlDB.rawQuery("SELECT * FROM lunch",null);
                selectMealInfo(cursor);
                break;
            case 2 :
                setTitle("저녁");
                cursor = sqlDB.rawQuery("SELECT * FROM dinner",null);
                selectMealInfo(cursor);
                break;
            case 3 :
                setTitle("간식");
                cursor = sqlDB.rawQuery("SELECT * FROM snack",null);
                selectMealInfo(cursor);
                break;
        }
        sqlDB.close();

        //추가 버튼을 누르면 음식 검색 화면으로 넘어가고 mealNo을 넘겨줌
        btnFoodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Meal.this,FoodSearch.class);
                intent.putExtra("mealNo",mealNo);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            Intent intent = new Intent(Meal.this, Main.class);
            intent.putExtra("mainNo",0);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectMealInfo (Cursor cursor){

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(Meal.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<FoodInfo> foodInfoArrayList = new ArrayList<>();

        while (cursor.moveToNext()){
           foodInfoArrayList.add(new FoodInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
        }

        MyRVAdater myRVAdater = new MyRVAdater(foodInfoArrayList);
        mRecyclerView.setAdapter(myRVAdater);
        cursor.close();
    }
}
