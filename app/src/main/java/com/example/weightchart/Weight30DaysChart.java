package com.example.weightchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Weight30DaysChart extends View {

    private final SimpleDateFormat formatFullDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    final float widthRectStepPerDate = dpToPx(6, getContext());
    final float radRRectStepPerDate = dpToPx(4, getContext());
    final float radRRectAveragePerDate = dpToPx(2, getContext());
    final float radRRectCurrentDate = dpToPx(2, getContext());
    final float widthRRectCurrentDate = dpToPx(80, getContext());
    final float heightRRectCurrentDate = dpToPx(50, getContext());
    final float widthStrokeCurrentDate = dpToPx(2, getContext());

    private float topPaddingRRectCurrentDate = dpToPx(6, getContext());
    private float leftPaddingRRectCurrentDate = dpToPx(11, getContext());
    final float topPaddingTextStepCurrentDate = dpToPx(3, getContext());

    final DashPathEffect dashPathEffect = new DashPathEffect(new float[]{5, 5}, 0);
    private final float widthRectAveragePerDate = dpToPx(20, getContext());
    int axisXPaddingRight = (int) widthRectAveragePerDate;
    final int axisXPaddingBot = dpToPx(5, getContext());
    int axisXHeightDate;


    int axisXYdistanceHeight = 0;
    float axisXYdistanceWidth = 0;

    float axisYPaddingTop = heightRRectCurrentDate;
    int axisYdistanceNumber;
    int axisYmaxLengthNumber;

    int axisYPaddingLeft = 0;
    int axisYHeightNumber;

    ArrayList<Integer> listNumber;
    ArrayList<Float> listWidthNumber;
    ArrayList<Float> listWidthDate;
    ArrayList<String> listDate;
    ArrayList<Integer> listDateIndex;

    ArrayList<Float> listData;
    ArrayList<Float> listAverage;
    ArrayList<Float> listAxisX;

    ArrayList<Float> listAxisY;
    float unitX;
    float unitYSpace;
    float unitY;

    Paint paintStepTextCurrentDate;
    Paint paintRRectStepPerDate;
    Paint paintGrid;
    Paint paintRRectAveragePerDate;
    Paint paintRRectCurrentDate;
    private Paint paintLineCurrentDate;
    private Paint paintValueAxisY;
    private Paint paintValueAxisX;

    private float space;
    private Paint paintAverageLine;
    private float leftRRectCurrentDate;
    private float heightTextStepCurrentDate;
    private Paint paintLineValue;


    public Weight30DaysChart(Context context) {
        super(context);
    }

    public Weight30DaysChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
        initPaintText();
    }

    private void initData() {
        listDate = new ArrayList<>();
        listDate.add("4일");
        listDate.add("11일");
        listDate.add("18일");
        listDate.add("25일");

        listDateIndex = new ArrayList<>();
        listDateIndex.add(3);
        listDateIndex.add(10);
        listDateIndex.add(17);
        listDateIndex.add(24);

        this.listData = new ArrayList<>();
        this.listData.add(56f);
        this.listData.add(57f);
        this.listData.add(58f);
        this.listData.add(57.4f);
        this.listData.add(60f);
        this.listData.add(61f);
        this.listData.add(61f);
        this.listData.add(59f);
        this.listData.add(57f);
        this.listData.add(57.5f);

        this.listData.add(56f);
        this.listData.add(57f);
        this.listData.add(58f);
        this.listData.add(59f);
        this.listData.add(60f);
        this.listData.add(62.3f);
        this.listData.add(61f);
        this.listData.add(59f);
        this.listData.add(57f);
        this.listData.add(54.1f);

        this.listData.add(54.2f);
        this.listData.add(54.3f);
        this.listData.add(55f);
        this.listData.add(59f);
        this.listData.add(60f);
        this.listData.add(61f);
        this.listData.add(61f);
        this.listData.add(59f);
        this.listData.add(57f);
        this.listData.add(55f);

        ////////////////////////////////////

        this.listAverage = new ArrayList<>();
        this.listAverage.add(56f);
        this.listAverage.add(56f);
        this.listAverage.add(57f);
        this.listAverage.add(58.4f);
        this.listAverage.add(62f);
        this.listAverage.add(60f);
        this.listAverage.add(60f);
        this.listAverage.add(58f);
        this.listAverage.add(58f);
        this.listAverage.add(57.7f);

        this.listAverage.add(56f);
        this.listAverage.add(58f);
        this.listAverage.add(57.7f);
        this.listAverage.add(58.5f);
        this.listAverage.add(59f);
        this.listAverage.add(63.3f);
        this.listAverage.add(61f);
        this.listAverage.add(59f);
        this.listAverage.add(57.6f);
        this.listAverage.add(54.1f);

        this.listAverage.add(54.2f);
        this.listAverage.add(54.3f);
        this.listAverage.add(56.5f);
        this.listAverage.add(58.5f);
        this.listAverage.add(60f);
        this.listAverage.add(62f);
        this.listAverage.add(61f);
        this.listAverage.add(60.1f);
        this.listAverage.add(56f);
        this.listAverage.add(55f);

        ArrayList<Float> tempList = new ArrayList<Float>();
        tempList.addAll(listAverage);
        tempList.addAll(listData);

        this.listNumber = genAxisYValue(tempList);
        //this
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Rect boundDate = new Rect();
        paintValueAxisX.getTextBounds("4일", 0, 2, boundDate);
        axisXHeightDate = boundDate.height();

        Rect boundNumber = new Rect();
        String max = Collections.max(listNumber).toString();
        paintValueAxisY.getTextBounds(max, 0, max.length(), boundNumber);
        axisYmaxLengthNumber = boundNumber.width();
        axisYHeightNumber = boundNumber.height();

        Rect bound;
        this.listWidthNumber = new ArrayList<>();
        for (int number : listNumber) {
            bound = new Rect();
            paintValueAxisY.getTextBounds(String.valueOf(number), 0, String.valueOf(number).length(), bound);
            listWidthNumber.add((float) bound.width());
        }

        this.listWidthDate = new ArrayList<>();
        for (String date : listDate) {
            bound = new Rect();
            paintValueAxisX.getTextBounds(date, 0, date.length(), bound);
            listWidthDate.add((float) bound.width());
        }

        axisYdistanceNumber = (getHeight() - axisXYdistanceHeight - ((int) axisYPaddingTop + axisXPaddingBot + axisXHeightDate)
                - listNumber.size() * axisYHeightNumber) / (listNumber.size() - 1);

        unitX = (getWidth() - (axisYmaxLengthNumber + (int) axisXYdistanceWidth + axisXPaddingRight)) / 29;

        Rect boundValueStepCurrentDate = new Rect();
        paintStepTextCurrentDate.getTextBounds("보", 0, 1, boundValueStepCurrentDate);
        this.heightTextStepCurrentDate = boundValueStepCurrentDate.height();

        calculateAxis();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //drawGrid(canvas, paintGrid);

        drawValueAxis(canvas, paintValueAxisX, paintValueAxisY);

        drawLineCurrentDate(canvas, paintLineCurrentDate, 6);
        drawRRectCurrentDate(canvas, paintRRectCurrentDate, 6);
        drawTextStepCurrentDate(canvas, paintValueAxisX, paintStepTextCurrentDate, 6);

        drawLineValue(canvas, paintLineValue, listData);

        drawLineValue(canvas, paintAverageLine, listAverage);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Weight30DaysChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initPaintText() {

        paintStepTextCurrentDate = new Paint();
        paintStepTextCurrentDate.setColor(getResources().getColor(R.color.grey900, null));
        paintStepTextCurrentDate.setStyle(Paint.Style.STROKE);
        //paintStepTextCurrentDate.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));
        paintStepTextCurrentDate.setTextSize(spToPx(15, getContext()));


        paintRRectStepPerDate = new Paint();
        paintRRectStepPerDate.setColor(getResources().getColor(R.color.dailyWalk, null));
        paintRRectStepPerDate.setStyle(Paint.Style.FILL);

        paintRRectAveragePerDate = new Paint();
        paintRRectAveragePerDate.setColor(getResources().getColor(R.color.dailyWalk, null));
        paintRRectAveragePerDate.setAlpha(26); //26 = 15%
        paintRRectAveragePerDate.setStyle(Paint.Style.FILL);

        paintRRectCurrentDate = new Paint();
        paintRRectCurrentDate.setColor(getResources().getColor(R.color.grey100, null));
        paintRRectCurrentDate.setStyle(Paint.Style.FILL);

        paintLineCurrentDate = new Paint();
        paintLineCurrentDate.setColor(getResources().getColor(R.color.grey300, null));
        paintLineCurrentDate.setStrokeWidth(this.widthStrokeCurrentDate);
        paintLineCurrentDate.setStyle(Paint.Style.STROKE);


        paintValueAxisY = new Paint();
        paintValueAxisY.setColor(getResources().getColor(R.color.grey600, null));
        paintValueAxisY.setStyle(Paint.Style.FILL_AND_STROKE);
        //paintValueAxisY.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));
        paintValueAxisY.setTextSize(spToPx(12, getContext()));

        paintValueAxisX = new Paint();
        paintValueAxisX.setColor(getResources().getColor(R.color.grey900, null));
        paintValueAxisX.setStyle(Paint.Style.FILL_AND_STROKE);
        //paintValueAxisX.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));
        paintValueAxisX.setTextSize(spToPx(12, getContext()));

        paintGrid = new Paint();
        paintGrid.setColor(Color.RED);
        paintGrid.setStyle(Paint.Style.STROKE);

        paintAverageLine = new Paint();
        paintAverageLine.setColor(getResources().getColor(R.color.dailySleep, null));
        paintAverageLine.setStrokeWidth(dpToPx(2, getContext()));
        paintAverageLine.setStyle(Paint.Style.STROKE);
        paintAverageLine.setStrokeCap(Paint.Cap.ROUND);

        paintLineValue = new Paint();
        paintLineValue.setColor(getResources().getColor(R.color.dailyWeight,null));
        paintLineValue.setStrokeWidth(dpToPx(2, getContext()));
        paintLineValue.setStyle(Paint.Style.STROKE);
        paintLineValue.setStrokeCap(Paint.Cap.ROUND);


        paintRRectStepPerDate.setAntiAlias(true);
        paintGrid.setAntiAlias(true);
        paintAverageLine.setAntiAlias(true);
        paintStepTextCurrentDate.setAntiAlias(true);
        paintRRectCurrentDate.setAntiAlias(true);
        paintLineCurrentDate.setAntiAlias(true);
        paintValueAxisY.setAntiAlias(true);
        paintValueAxisX.setAntiAlias(true);
        paintLineCurrentDate.setAntiAlias(true);

    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public ArrayList<Integer> genAxisYValue(ArrayList<Float> list) {


        int min = Math.round(Collections.min(list));
        int max = (int) Math.ceil(Collections.max(list));

        int mid = (int) Math.ceil((float) (min + max) / 2);
        max = min + (mid - min) * 2;
        ArrayList<Integer> listResult = new ArrayList<>();
        listResult.add(min);
        listResult.add(mid);
        listResult.add(max);
        this.space = mid - min;
        return listResult;
    }


    private void calculateAxis() {
        this.unitYSpace = axisYdistanceNumber + axisYHeightNumber;
        this.unitY = unitYSpace / space;
        this.listAxisX = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            this.listAxisX.add(axisXYdistanceWidth + axisYmaxLengthNumber + axisYPaddingLeft + i * unitX + listWidthDate.get(0) / 2);
        }
        this.listAxisY = new ArrayList<>();
        for (int i = 0; i < this.listNumber.size(); i++) {
            this.listAxisY.add(axisYPaddingTop + axisYHeightNumber / 2 + (this.listNumber.size() - 1 - i) * unitYSpace);
        }
    }

    private void drawValueAxis(Canvas canvas, Paint paintAxisX, Paint paintAxisY) {
        for (int i = 0; i < this.listDate.size(); i++) {
            canvas.drawText(this.listDate.get(i), this.listAxisX.get(listDateIndex.get(i)) - this.listWidthDate.get(i) / 2, getHeight() - axisXPaddingBot, paintAxisX);
        }

        for (int i = 0; i < listNumber.size(); i++) {
            canvas.drawText(String.valueOf(listNumber.get(i)),
                    axisYmaxLengthNumber + axisYPaddingLeft - this.listWidthNumber.get(i),
                    this.listAxisY.get(i) + axisYHeightNumber / 2, paintAxisY);
        }
    }

    private void drawGrid(Canvas canvas, Paint paint) {
        for (int i = 0; i < listAxisX.size(); i++) {
            canvas.drawLine(this.listAxisX.get(i), this.listAxisY.get(0), this.listAxisX.get(i), this.listAxisY.get(this.listAxisY.size() - 1), paint);
        }


        for (int i = 0; i < this.listAxisY.size(); i++) {
            canvas.drawLine(this.listAxisX.get(0), this.listAxisY.get(i), this.listAxisX.get(this.listAxisX.size() - 1), this.listAxisY.get(i), paint);
        }

    }

    private void drawLineCurrentDate(Canvas canvas, Paint paint, int indexCurrentDate) {
        canvas.drawLine(this.listAxisX.get(indexCurrentDate), this.listAxisY.get(0),
                this.listAxisX.get(indexCurrentDate), 0f, paint);
    }

    private void drawRRectCurrentDate(Canvas canvas, Paint paint, int indexCurrentDate) {
        float left = this.listAxisX.get(indexCurrentDate) - this.widthRRectCurrentDate / 2;
        float right = this.listAxisX.get(indexCurrentDate) + this.widthRRectCurrentDate / 2;
        if (left < 0) {
            left = 0;
            right = this.widthRRectCurrentDate;
        } else if (right > getWidth()) {
            right = getWidth();
            left = right - this.widthRRectCurrentDate;
        }
        this.leftRRectCurrentDate = left;
        canvas.drawRoundRect(left, 0, right, this.heightRRectCurrentDate, this.radRRectCurrentDate, this.radRRectCurrentDate, paint);
    }

    private void drawTextStepCurrentDate(Canvas canvas, Paint paintDate, Paint paintValueStep, int indexCurrentDate) {
        canvas.drawText("8/25 · 오늘", this.leftRRectCurrentDate + this.leftPaddingRRectCurrentDate,
                this.topPaddingRRectCurrentDate + axisXHeightDate, paintDate);
        canvas.drawText(this.listData.get(indexCurrentDate) + "보", this.leftRRectCurrentDate + this.leftPaddingRRectCurrentDate,
                this.topPaddingRRectCurrentDate + axisXHeightDate + heightTextStepCurrentDate + this.topPaddingTextStepCurrentDate,
                paintValueStep);
    }

    private void drawLineValue(Canvas canvas, Paint paintLineValue , List<Float> listData) {
        for (int i = 0; i < listData.size() - 1; i++) {
            canvas.drawLine(listAxisX.get(i), convertValueToPx(listData.get(i)), listAxisX.get(i + 1), convertValueToPx(listData.get(i + 1)), paintLineValue);
        }
    }


    private float convertValueToPx(float value) {
        return this.listAxisY.get(0) - (value - listNumber.get(0)) * unitY;
    }

}
