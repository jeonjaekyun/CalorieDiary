package com.example.jm.caloriediary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by JM on 2018-06-02.
 */

//음식 검색 화면 구현
public class FoodSearch extends AppCompatActivity {

    Button btnNewFoodAdd, btnFoodSearch;
    View newFoodAdd;
    EditText editName, editKcal, editCarbohydrate, editProtein, editFat;
    TextView textSname, textScarbohydrate, textSprotein, textSfat, textSkcal;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    EditText editFoodSearch;
    String foodsearchname;
    int mealNo;
    String Sname, Scarbohydrate, Sprotein, Sfat, Skcal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodsearch);
        setTitle("음식검색");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDBHelper = new MyDBHelper(this);

        btnNewFoodAdd = findViewById(R.id.btnNewFoodAdd);
        btnFoodSearch = findViewById(R.id.btnFoodSearch);
        editFoodSearch = findViewById(R.id.editFoodSearch);
        textSname = findViewById(R.id.textSName);
        textScarbohydrate = findViewById(R.id.textSCarbohydrate);
        textSprotein = findViewById(R.id.textSProtein);
        textSfat = findViewById(R.id.textSFat);
        textSkcal = findViewById(R.id.textSKcal);

        //meal에서 mealNo을 받음
        Intent inIntent = getIntent();
        mealNo = inIntent.getIntExtra("mealNo", 0);

        //검색 버튼을 클릭시 해당하는 음식이 화면에 나타남
        btnFoodSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cursor;
                foodsearchname = editFoodSearch.getText().toString();

                cursor = sqlDB.rawQuery("SELECT * FROM foodInfo where foodname = '" + foodsearchname + "';", null);

                while (cursor.moveToNext()) {
                    Sname = cursor.getString(0);
                    Scarbohydrate = cursor.getString(1);
                    Sprotein = cursor.getString(2);
                    Sfat = cursor.getString(3);
                    Skcal = cursor.getString(4);
                }

                textSname.setText(Sname);
                textScarbohydrate.setText(Scarbohydrate);
                textSprotein.setText(Sprotein);
                textSfat.setText(Sfat);
                textSkcal.setText(Skcal);

                cursor.close();
                sqlDB.close();
                if (Sname == null) {//만약 검색되는 음식이 없다면 토스트를 띄워줌
                    Toast.makeText(FoodSearch.this, "해당하는 음식이 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //새로운 음식 추가를 클릭시 새로운 음식 추가를 위한 대화창이 뜸
        btnNewFoodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFoodAdd = (View) View.inflate(FoodSearch.this, R.layout.newfoodadd, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(FoodSearch.this);
                dlg.setView(newFoodAdd);
                dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editName = newFoodAdd.findViewById(R.id.editName);
                        editKcal = newFoodAdd.findViewById(R.id.editKcal);
                        editCarbohydrate = newFoodAdd.findViewById(R.id.editCarbohydrate);
                        editProtein = newFoodAdd.findViewById(R.id.editProtein);
                        editFat = newFoodAdd.findViewById(R.id.editFat);
                        sqlDB = myDBHelper.getWritableDatabase();

                        try {
                            sqlDB.execSQL("INSERT INTO foodInfo VALUES('" + editName.getText().toString() + "','" +
                                    Integer.parseInt(editCarbohydrate.getText().toString()) + "','" + Integer.parseInt(editProtein.getText().toString()) + "','" +
                                    Integer.parseInt(editFat.getText().toString()) + "','" + Integer.parseInt(editKcal.getText().toString()) + "');");
                            sqlDB.close();
                            Toast.makeText(FoodSearch.this, "추가 완료!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) { //insert 되지 않는다면 중복된 이름 존재 한다는 토스트를 띄움
                            Toast.makeText(FoodSearch.this, "중복된 이름 존재!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dlg.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.foodsearch_menu, menu);
        return true;
    }

    //액션바 활용 https://m.blog.naver.com/PostView.nhn?blogId=cosmosjs&logNo=220943457624&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        foodsearchname = editFoodSearch.getText().toString();
        Log.d("foodname", foodsearchname);
        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(FoodSearch.this, Meal.class);
                intent.putExtra("mealNo", mealNo);
                startActivity(intent);
                break;
            case R.id.btnAdd: //음식 검색 화면 메뉴에서 추가를 클릭시 meal에서 받아온 mealNo을 기준으로 해당하는 테이블에 데이터를 넣어줌
                if (foodsearchname.getBytes().length <= 0) {
                    Toast.makeText(FoodSearch.this, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    sqlDB = myDBHelper.getWritableDatabase();
                    switch (mealNo) {
                        case 0:
                            sqlDB.execSQL("INSERT INTO breakfast SELECT * FROM foodInfo WHERE foodname = '" + foodsearchname + "';");
                            break;
                        case 1:
                            sqlDB.execSQL("INSERT INTO lunch SELECT * FROM foodInfo WHERE foodname = '" + foodsearchname + "';");
                            break;
                        case 2:
                            sqlDB.execSQL("INSERT INTO dinner SELECT * FROM foodInfo WHERE foodname = '" + foodsearchname + "';");
                            break;
                        case 3:
                            sqlDB.execSQL("INSERT INTO snack SELECT * FROM foodInfo WHERE foodname = '" + foodsearchname + "';");
                            break;
                    }
                    sqlDB.close();
                    Toast.makeText(FoodSearch.this, "추가 완료!", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
