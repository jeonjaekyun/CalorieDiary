package com.example.jm.caloriediary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

/**
 * Created by JM on 2018-06-03.
 */

public class UserInfo extends AppCompatActivity {
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    TextView textGoal, textGender, textActivity, textHeghit, textGoalWeight;
    String goal, gender, activity,height, goalWeight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        getSupportActionBar().setTitle("개인정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDBHelper = new MyDBHelper(this);
        textGoal = findViewById(R.id.textGoal);
        textGender = findViewById(R.id.textGender);
        textActivity = findViewById(R.id.textActivity);
        textHeghit = findViewById(R.id.textHeight);
        textGoalWeight = findViewById(R.id.textGoalWeight);

        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM userInfo", null);

        while (cursor.moveToNext()) {
            goal = cursor.getString(1);
            gender = cursor.getString(2);
            activity = cursor.getString(3);
            height = cursor.getString(4);
            goalWeight = cursor.getString(5);
        }

        textGoal.setText(goal);
        textGender.setText(gender);
        textActivity.setText(activity);
        textHeghit.setText(height);
        textGoalWeight.setText(goalWeight);

        cursor.close();
        sqlDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.userinfo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnInit:
                sqlDB = myDBHelper.getWritableDatabase();
                myDBHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();
                Toast.makeText(UserInfo.this,"개인 정보 초기화",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(UserInfo.this, Start.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
