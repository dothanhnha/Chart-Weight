package com.example.weightchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Weight7DaysChart chart = findViewById(R.id.chart);

        HashMap<Integer, String> descr = new HashMap<>();
        descr.put(R.color.dailyWeight12,"test1");
        descr.put(R.color.dailySleep, "test2");
        chart.setDescr(descr, "test");
    }



}