package com.example.jm.caloriediary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JM on 2018-06-03.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, "CalorieDiaryDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE userInfo (id CHAR(1) PRIMARY KEY, goal CHAR(20), gender CHAR(10), activity CAHR(20), height INTEGER, goalweight INTEGER, rekcal INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE foodInfo (foodname CHAR(30) PRIMARY KEY, carbohydrate INTEGER, protein INTEGER, fat INTEGER, kcal INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE breakfast (foodname CHAR(30) , carbohydrate INTEGER, protein INTEGER, fat INTEGER, kcal INTEGER); ");
        sqLiteDatabase.execSQL("CREATE TABLE lunch (foodname CHAR(30) , carbohydrate INTEGER, protein INTEGER, fat INTEGER, kcal INTEGER); ");
        sqLiteDatabase.execSQL("CREATE TABLE dinner (foodname CHAR(30) , carbohydrate INTEGER, protein INTEGER, fat INTEGER, kcal INTEGER); ");
        sqLiteDatabase.execSQL("CREATE TABLE snack (foodname CHAR(30) , carbohydrate INTEGER, protein INTEGER, fat INTEGER, kcal INTEGER); ");
        sqLiteDatabase.execSQL("CREATE TABLE dateInfo(date CHAR(10) PRIMARY KEY, rekcal INTEGERT, inkcal INTEGER, carbohydrate INTEGER, protein INTEGER, fat INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS userInfo");
        sqLiteDatabase.execSQL("CREATE TABLE userInfo (id CHAR(1) PRIMARY KEY, goal CHAR(20), gender CHAR(10), activity CAHR(20), height INTEGER, goalweight INTEGER, rekcal INTEGER);");
    }
}

