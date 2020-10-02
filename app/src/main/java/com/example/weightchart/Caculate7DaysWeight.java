package com.example.weightchart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class Caculate7DaysWeight {

    public static final int MS_DATE = 24*60*60*1000;

    public static class ObjectPerDate {
        double valueAverage;
        double deviation;
        Date collectedTime;

        public ObjectPerDate(double valueAverage, double deviation, Date collectedTime) {
            this.valueAverage = valueAverage;
            this.deviation = deviation;
            this.collectedTime = collectedTime;
        }

        public double getValueAverage() {
            return valueAverage;
        }

        public void setValueAverage(double valueAverage) {
            this.valueAverage = valueAverage;
        }

        public double getDeviation() {
            return deviation;
        }

        public void setDeviation(double deviation) {
            this.deviation = deviation;
        }

        public Date getCollectedTime() {
            return collectedTime;
        }

        public void setCollectedTime(Date collectedTime) {
            this.collectedTime = collectedTime;
        }
    }


    public static List<WeightData> getAverage7DayASC(List<WeightData> listWeight) {
        List<WeightData> listResult = new ArrayList<>();
        if (listWeight == null || listWeight.size() == 0)
            return listResult;

        Calendar calendar = Calendar.getInstance();

        double sumWeight = 0;
        double sumBMI = 0;
        double sumFat = 0;
        double sumMuscle = 0;
        double sumWater = 0;

        calendar.setTime(listWeight.get(0).getCollectedTime());
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int count = 0;
        WeightData average;
        for (int i = 0; i < listWeight.size(); i++) {
            ++count;
            calendar.setTime(listWeight.get(i).getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) {
                sumWeight += listWeight.get(i).getWeight();
                sumBMI += listWeight.get(i).getBmi();
                sumFat += listWeight.get(i).getFat();
                sumMuscle += listWeight.get(i).getMuscle();
                sumWater += listWeight.get(i).getWater();
            } else {
                --count;
                average = new WeightData(sumBMI / count, listWeight.get(i - 1).getCollectedTime(), sumFat / count, sumMuscle / count, sumWater / count, sumWeight / count);
                listResult.add(average);
                count = 1;
                sumWeight = listWeight.get(i).getWeight();
                sumBMI = listWeight.get(i).getBmi();
                sumFat = listWeight.get(i).getFat();
                sumMuscle = listWeight.get(i).getMuscle();
                sumWater = listWeight.get(i).getWater();
                currentDay = calendar.get(Calendar.DAY_OF_WEEK);
            }

        }
        ////list just have one day:
        calendar.setTime(listWeight.get(0).getCollectedTime());
        if (currentDay == calendar.get(Calendar.DAY_OF_WEEK)) {
            average = new WeightData(sumBMI / count, listWeight.get(0).getCollectedTime(), sumFat / count, sumMuscle / count, sumWater / count, sumWeight / count);
            listResult.add(average);
        }


        return listResult;
    }

    public static List<ObjectPerDate> getAverageWeight7DayASC(List<WeightData> listWeight) {
        List<ObjectPerDate> listResult = new ArrayList<>();
        if (listWeight == null || listWeight.size() == 0)
            return listResult;

        Calendar calendar = Calendar.getInstance();

        double sumWeight = 0;

        calendar.setTime(listWeight.get(0).getCollectedTime());
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int count = 0;
        double average;
        double minPerDate = listWeight.get(0).getWeight();
        double maxPerDate = minPerDate;
        for (int i = 0; i < listWeight.size(); i++) {
            ++count;
            calendar.setTime(listWeight.get(i).getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) { /// still at old date and not a end of list, all case is not end data of date
                if (listWeight.get(i).getWeight() < minPerDate)
                    minPerDate = listWeight.get(i).getWeight();
                if (listWeight.get(i).getWeight() > maxPerDate)
                    maxPerDate = listWeight.get(i).getWeight();
                sumWeight += listWeight.get(i).getWeight();
            } else {
                --count;
                average = sumWeight / count;
                listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(i - 1).getCollectedTime()));
                count = 1;
                minPerDate = listWeight.get(i).getWeight();
                maxPerDate = minPerDate;
                sumWeight = listWeight.get(i).getWeight();
                currentDay = calendar.get(Calendar.DAY_OF_WEEK);
            }

        }
        ////calculate last date of list
        calendar.setTime(listWeight.get(listWeight.size() - 1).getCollectedTime());
        average = sumWeight / count;
        listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(listWeight.size() - 1).getCollectedTime()));


        return listResult;
    }

    public static List<ObjectPerDate> getAverageBMI7DayASC(List<WeightData> listWeight) {
        List<ObjectPerDate> listResult = new ArrayList<>();
        if (listWeight == null || listWeight.size() == 0)
            return listResult;

        Calendar calendar = Calendar.getInstance();

        double sumBMI = 0;

        calendar.setTime(listWeight.get(0).getCollectedTime());
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int count = 0;
        double average;
        double minPerDate = listWeight.get(0).getBmi();
        double maxPerDate = minPerDate;
        for (int i = 0; i < listWeight.size(); i++) {
            ++count;
            calendar.setTime(listWeight.get(i).getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) { /// still at old date and not a end of list, all case is not end data of date
                if (listWeight.get(i).getBmi() < minPerDate)
                    minPerDate = listWeight.get(i).getBmi();
                if (listWeight.get(i).getBmi() > maxPerDate)
                    maxPerDate = listWeight.get(i).getBmi();
                sumBMI += listWeight.get(i).getBmi();
            } else {
                --count;
                average = sumBMI / count;
                listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(i - 1).getCollectedTime()));
                count = 1;
                minPerDate = listWeight.get(i).getBmi();
                maxPerDate = minPerDate;
                sumBMI = listWeight.get(i).getBmi();
                currentDay = calendar.get(Calendar.DAY_OF_WEEK);
            }

        }
        ////calculate last date of list
        calendar.setTime(listWeight.get(listWeight.size() - 1).getCollectedTime());
        average = sumBMI / count;
        listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(listWeight.size() - 1).getCollectedTime()));


        return listResult;
    }

    public static ArrayList<ObjectPerDate> getAverageFat7DayASC(ArrayList<WeightData> listWeight) {
        ArrayList<ObjectPerDate> listResult = new ArrayList<>();
        if (listWeight == null || listWeight.size() == 0)
            return listResult;

        Calendar calendar = Calendar.getInstance();

        double sumFat = 0;

        calendar.setTime(listWeight.get(0).getCollectedTime());
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int count = 0;
        double average;
        double minPerDate = listWeight.get(0).getFat();
        double maxPerDate = minPerDate;
        for (int i = 0; i < listWeight.size(); i++) {
            ++count;
            calendar.setTime(listWeight.get(i).getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) { /// still at old date and not a end of list, all case is not end data of date
                if (listWeight.get(i).getFat() < minPerDate)
                    minPerDate = listWeight.get(i).getFat();
                if (listWeight.get(i).getFat() > maxPerDate)
                    maxPerDate = listWeight.get(i).getFat();
                sumFat += listWeight.get(i).getFat();
            } else {
                --count;
                average = sumFat / count;
                listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(i - 1).getCollectedTime()));
                count = 1;
                minPerDate = listWeight.get(i).getFat();
                maxPerDate = minPerDate;
                sumFat = listWeight.get(i).getFat();
                currentDay = calendar.get(Calendar.DAY_OF_WEEK);
            }

        }
        ////calculate last date of list
        calendar.setTime(listWeight.get(listWeight.size() - 1).getCollectedTime());
        average = sumFat / count;
        listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(listWeight.size() - 1).getCollectedTime()));


        return listResult;
    }

    public static List<ObjectPerDate> getAverageMuscle7DayASC(List<WeightData> listWeight) {
        List<ObjectPerDate> listResult = new ArrayList<>();
        if (listWeight == null || listWeight.size() == 0)
            return listResult;

        Calendar calendar = Calendar.getInstance();

        double sumMuscle = 0;

        calendar.setTime(listWeight.get(0).getCollectedTime());
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int count = 0;
        double average;
        double minPerDate = listWeight.get(0).getMuscle();
        double maxPerDate = minPerDate;
        for (int i = 0; i < listWeight.size(); i++) {
            ++count;
            calendar.setTime(listWeight.get(i).getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) { /// still at old date and not a end of list, all case is not end data of date
                if (listWeight.get(i).getMuscle() < minPerDate)
                    minPerDate = listWeight.get(i).getMuscle();
                if (listWeight.get(i).getMuscle() > maxPerDate)
                    maxPerDate = listWeight.get(i).getMuscle();
                sumMuscle += listWeight.get(i).getMuscle();
            } else {
                --count;
                average = sumMuscle / count;
                listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(i - 1).getCollectedTime()));
                count = 1;
                minPerDate = listWeight.get(i).getMuscle();
                maxPerDate = minPerDate;
                sumMuscle = listWeight.get(i).getMuscle();
                currentDay = calendar.get(Calendar.DAY_OF_WEEK);
            }

        }
        ////calculate last date of list
        calendar.setTime(listWeight.get(listWeight.size() - 1).getCollectedTime());
        average = sumMuscle / count;
        listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(listWeight.size() - 1).getCollectedTime()));


        return listResult;
    }

    public static List<ObjectPerDate> getAverageWater7DayASC(List<WeightData> listWeight) {
        List<ObjectPerDate> listResult = new ArrayList<>();
        if (listWeight == null || listWeight.size() == 0)
            return listResult;

        Calendar calendar = Calendar.getInstance();

        double sumWater = 0;

        calendar.setTime(listWeight.get(0).getCollectedTime());
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int count = 0;
        double average;
        double minPerDate = listWeight.get(0).getWater();
        double maxPerDate = minPerDate;
        for (int i = 0; i < listWeight.size(); i++) {
            ++count;
            calendar.setTime(listWeight.get(i).getCollectedTime());
            if (calendar.get(Calendar.DAY_OF_WEEK) == currentDay) { /// still at old date and not a end of list, all case is not end data of date
                if (listWeight.get(i).getWater() < minPerDate)
                    minPerDate = listWeight.get(i).getWater();
                if (listWeight.get(i).getWater() > maxPerDate)
                    maxPerDate = listWeight.get(i).getWater();
                sumWater += listWeight.get(i).getWater();
            } else {
                --count;
                average = sumWater / count;
                listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(i - 1).getCollectedTime()));
                count = 1;
                minPerDate = listWeight.get(i).getWater();
                maxPerDate = minPerDate;
                sumWater = listWeight.get(i).getWater();
                currentDay = calendar.get(Calendar.DAY_OF_WEEK);
            }

        }
        ////calculate last date of list
        calendar.setTime(listWeight.get(listWeight.size() - 1).getCollectedTime());
        average = sumWater / count;
        listResult.add(new ObjectPerDate(average, maxPerDate - minPerDate, listWeight.get(listWeight.size() - 1).getCollectedTime()));


        return listResult;
    }

    public static ArrayList<ObjectPerDate> insertMissingDate(ArrayList<ObjectPerDate> list) {
        boolean existDate;
        Calendar calendar = Calendar.getInstance();
        ObjectPerDate previousDate;
        for (int date = Calendar.TUESDAY; date <= Calendar.SATURDAY; date++) {
            if(getObjectPerDate(list,date) ==null){
                previousDate =getObjectPerDate(list,date-1);
                list.add(new ObjectPerDate(previousDate.getValueAverage(),previousDate.getDeviation(),new Date(previousDate.getCollectedTime().getTime() + MS_DATE)));
            }
        }
        //check sunday:
        if(getObjectPerDate(list,Calendar.SUNDAY) ==null){
            previousDate =getObjectPerDate(list,Calendar.SATURDAY);
            list.add(new ObjectPerDate(previousDate.getValueAverage(),previousDate.getDeviation(),new Date(previousDate.getCollectedTime().getTime() + MS_DATE)));
        }

        return list;
    }

    public static ObjectPerDate getObjectPerDate(List<ObjectPerDate> list , int dateOfWeek){
        Calendar calendar = Calendar.getInstance();
        for(ObjectPerDate item : list){
            calendar.setTime(item.getCollectedTime());
            if(calendar.get(Calendar.DAY_OF_WEEK) == dateOfWeek){
                return item;
            }
        }
        return null;
    }

    public static ArrayList<ObjectPerDate> sortASCDate(ArrayList<ObjectPerDate> list ){

        Collections.sort(list, new Comparator<ObjectPerDate>() {
            @Override
            public int compare(ObjectPerDate s1, ObjectPerDate s2) {
                if (s1.getCollectedTime().after(s2.getCollectedTime()))
                    return 1;
                else
                    return -1;
            }
        });
        return list;
    }
}
