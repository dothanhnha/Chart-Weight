package com.example.weightchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Weight7DaysChart chart = findViewById(R.id.chart);

        HashMap<Integer, String> descr = new HashMap<>();
        descr.put(R.color.dailyWeight12,"test1");
        descr.put(R.color.dailySleep, "test2");
        chart.setDescr(descr, "test");

        ArrayList<WeightData> listData = new ArrayList<>();

        try {


            listData.add(new WeightData(3,format.parse("2020-09-28T09:07:48.730Z"),50,5,6,4));
            listData.add(new WeightData(3,format.parse("2020-09-28T09:09:48.730Z"),45,5,6,6));
            listData.add(new WeightData(3,format.parse("2020-09-28T09:10:48.730Z"),43,5,6,8));
            listData.add(new WeightData(3,format.parse("2020-09-28T10:07:48.730Z"),42,5,6,3));
            listData.add(new WeightData(3,format.parse("2020-09-28T11:07:48.730Z"),60,5,6,1));

            listData.add(new WeightData(3,format.parse("2020-09-30T09:07:48.730Z"),59,5,6,4));
            listData.add(new WeightData(3,format.parse("2020-09-30T10:07:48.730Z"),64,5,6,6));
            listData.add(new WeightData(3,format.parse("2020-09-30T11:07:48.730Z"),49,5,6,8));
            listData.add(new WeightData(3,format.parse("2020-09-30T11:30:48.730Z"),50,5,6,3));
            listData.add(new WeightData(3,format.parse("2020-09-30T012:07:48.730Z"),55,5,6,1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        chart.setData(Caculate7DaysWeight.sortASCDate(Caculate7DaysWeight.insertMissingDate(Caculate7DaysWeight.getAverageFat7DayASC(listData))),
                listData);

    }



}