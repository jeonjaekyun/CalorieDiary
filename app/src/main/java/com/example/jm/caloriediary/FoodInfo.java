package com.example.jm.caloriediary;

/**
 * Created by JM on 2018-06-10.
 */

public class FoodInfo {
    public String eatenName, eatenCarbohydrate, eatenProtein,eatenFat, eatenKcal;

    public FoodInfo(String eatenName,String eatenCarbohydrate,String eatenProtein,String eatenFat,String eatenKcal){
        this.eatenName = eatenName;
        this.eatenCarbohydrate = eatenCarbohydrate;
        this.eatenProtein = eatenProtein;
        this.eatenFat = eatenFat;
        this.eatenKcal = eatenKcal;
    }
}
