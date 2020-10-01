package com.example.weightchart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Caculate7DaysWeight {

    public List<WeightData> listWeight;

    public List<WeightData> getAverage7DayASC() {

        Calendar calendar = Calendar.getInstance();

        List<WeightData> listResult = new ArrayList<>();
        int sumWeight = 0;
        int sumBMI = 0;
        int sumFat = 0;
        int sumMuscle = 0;
        int sumWater = 0;

        int currentDay = Calendar.MONDAY;
        int count = 0;
        WeightData average;
        for (WeightData data : listWeight) {
            ++count;
            calendar.setTime(data.getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) {
                sumWeight += data.getWeight();
                sumBMI += data.getBmi();
                sumFat += data.getFat();
                sumMuscle += data.getMuscle();
                sumWater += data.getWater();
            } else {
                average = new WeightData(sumBMI / count, data.getCollectedTime(), sumFat / count, sumMuscle / count, sumWater / count, sumWeight / count);
                listResult.add(average);
                count = 0;
                sumWeight = 0;
                sumBMI = 0;
                sumFat = 0;
                sumMuscle = 0;
                sumWater = 0;
                currentDay = (currentDay == 7 ? 1 : currentDay + 1);
            }

        }
        return listResult;
    }

    public List<WeightData> getAverage7DayASC(UnitWeightMeasure unit) {

        Calendar calendar = Calendar.getInstance();

        List<WeightData> listResult = new ArrayList<>();
        int sumWeight = 0;
        int sumBMI = 0;
        int sumFat = 0;
        int sumMuscle = 0;
        int sumWater = 0;

        int currentDay = Calendar.MONDAY;
        int count = 0;
        WeightData average;
        for (WeightData data : listWeight) {
            ++count;
            calendar.setTime(data.getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) {
                sumWeight += data.getWeight();
                sumBMI += data.getBmi();
                sumFat += data.getFat();
                sumMuscle += data.getMuscle();
                sumWater += data.getWater();
            } else {
                average = new WeightData(sumBMI / count, data.getCollectedTime(), sumFat / count, sumMuscle / count, sumWater / count, sumWeight / count);
                listResult.add(average);
                count = 0;
                sumWeight = 0;
                sumBMI = 0;
                sumFat = 0;
                sumMuscle = 0;
                sumWater = 0;
                currentDay = (currentDay == 7 ? 1 : currentDay + 1);
            }

        }
        return listResult;
    }
}
