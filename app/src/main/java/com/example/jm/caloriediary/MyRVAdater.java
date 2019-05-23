package com.example.jm.caloriediary;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by JM on 2018-06-10.
 */
//리사이클러뷰 어탭터 참고 사이트 http://dreamaz.tistory.com/345
public class MyRVAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textEatenName, textEatenCarbohysrate, textEatenProtein, textEatenFat, textEatenKcal;

        MyViewHolder(View view) {
            super(view);
            textEatenName = view.findViewById(R.id.textEatenName);
            textEatenCarbohysrate = view.findViewById(R.id.textEatenCarbohydrate);
            textEatenProtein = view.findViewById(R.id.textEatenProtein);
            textEatenFat = view.findViewById(R.id.textEatenFat);
            textEatenKcal = view.findViewById(R.id.textEatenKcal);
        }
    }

    private ArrayList<FoodInfo> foodInfoArrayList;

    MyRVAdater(ArrayList<FoodInfo> foodInfoArrayList) {
        this.foodInfoArrayList = foodInfoArrayList;
    }

    //리사이클러뷰에 new MyViewHoler(v)가 반환됨
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eatenlist, parent, false);

        return new MyViewHolder(v);
    }

    //리사이클러뷰에 리스트 항목View들이 생성됨
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.textEatenName.setText(foodInfoArrayList.get(position).eatenName);
        myViewHolder.textEatenCarbohysrate.setText(foodInfoArrayList.get(position).eatenCarbohydrate);
        myViewHolder.textEatenProtein.setText(foodInfoArrayList.get(position).eatenProtein);
        myViewHolder.textEatenFat.setText(foodInfoArrayList.get(position).eatenFat);
        myViewHolder.textEatenKcal.setText(foodInfoArrayList.get(position).eatenKcal);
    }
    //FoodInfoArrayList의 전체 리스트 항목 갯수를 반환해준다
    @Override
    public int getItemCount() {
        return foodInfoArrayList.size();
    }
}
