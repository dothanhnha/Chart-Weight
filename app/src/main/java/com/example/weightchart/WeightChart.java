package com.example.weightchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WeightChart extends View {

    final float radRRectCurrentDate = dpToPx(2, getContext());
    final float widthRRectCurrentDate = dpToPx(80, getContext());
    final float heightRRectCurrentDate = dpToPx(50, getContext());
    final float widthStrokeCurrentDate = dpToPx(2, getContext());
    private ArrayList<Float> listWeight;

    private float topPaddingRRectCurrentDate = dpToPx(6, getContext());
    private float leftPaddingRRectCurrentDate = dpToPx(11, getContext());
    final float topPaddingTextStepCurrentDate = dpToPx(5, getContext());

    Paint paintRRectCurrentDate;
    private Paint paintLineCurrentDate;
    private float leftRRectCurrentDate;
    private float heightTextStepCurrentDate;


    float widthRRect = dpToPx(20, getContext());



    int axisXPaddingMon = 70;
    int axisXPaddingSun = 10;
    int axisXPaddingBot = 5;
    int axisXHeightDate;
    int axisXDistanceDate;

    int axisXYdistanceHeight = 0;
    int axisXYdistanceWidth = 20;

    float axisYdistanceNumber;
    int axisYmaxLengthNumber;
    float axisYPaddingTop = heightRRectCurrentDate;
    int axisYPaddingLeft = 10;
    int axisYHeightNumber;

    int widthDate;
    ArrayList<Integer> listNumber;
    ArrayList<String> listDate;

    ArrayList<Float> listAxisX;
    ArrayList<Float> listAxisY;
    float unitX;
    float unitY;

    Paint paintStepTextCurrentDate;
    Paint paintDate;
    Paint paintRRect;
    Paint paintGrid;
    Paint paintPointFill;
    Paint paintPointStroke;
    Paint paintAverage;


    public WeightChart(Context context) {
        super(context);
    }

    public WeightChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDate();
        initPaintText();
        this.listWeight = new ArrayList<>();
        listWeight.add(39f);
        listWeight.add(68f);
        listWeight.add(70f);
        listWeight.add(81f);
        listWeight.add(53f);
        listWeight.add(50f);
        listWeight.add(60f);
        this.listNumber = genAxisYValue(listWeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Rect boundDate = new Rect();
        paintDate.getTextBounds("월", 0, 1, boundDate);
        axisXHeightDate = boundDate.height();
        axisYHeightNumber = axisXHeightDate;
        widthDate = boundDate.width();

        Rect boundNumber = new Rect();
        paintDate.getTextBounds("999", 0, 3, boundNumber);
        axisYmaxLengthNumber = boundNumber.width();
        axisYdistanceNumber = (getHeight() - axisYPaddingTop - (axisXYdistanceHeight + axisXPaddingBot + axisXHeightDate)
                - listNumber.size() * axisYHeightNumber) / (listNumber.size() - 1);

        axisXDistanceDate = (getWidth() - boundDate.width() * 7 - (axisXPaddingMon + axisYmaxLengthNumber + axisXYdistanceWidth + axisXPaddingSun)) / 6;

        Rect boundValueStepCurrentDate = new Rect();
        paintStepTextCurrentDate.getTextBounds("보", 0, 1, boundValueStepCurrentDate);
        this.heightTextStepCurrentDate = boundValueStepCurrentDate.height();

        calculateAxis();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawGrid(canvas, paintGrid);
        drawValueAxis(canvas, paintDate);

        List<Point> listPointTest = new ArrayList<>();
        List<Point> listPointAverageTest = new ArrayList<>();
        List<Float> listHeightRRectTest = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            listPointTest.add(new Point(Math.round(listAxisX.get(i)), Math.round(listAxisY.get(getRandomNumber(0, listAxisY.size() - 1)))));
            listHeightRRectTest.add((float) getRandomNumber(100, 300));
        }

        for (int i = 0; i <= 6; i++) {
            listPointAverageTest.add(new Point(Math.round(listAxisX.get(i)), Math.round(listAxisY.get(getRandomNumber(0, listAxisY.size() - 1)))));
            listPointAverageTest.add(new Point(Math.round(listAxisX.get(i) + (float) unitX / 2f), Math.round(listAxisY.get(getRandomNumber(0, listAxisY.size() - 1)))));
        }
        listPointAverageTest.remove(listPointAverageTest.size() - 1);

        drawAllRRects(canvas, paintRRect, listHeightRRectTest, listPointTest);
        drawLineValueAverage(canvas, paintAverage, listPointAverageTest);
        drawLineValueDate(canvas, paintPointFill, paintPointStroke, listPointTest);

/*        drawLineCurrentDate(canvas, paintLineCurrentDate, 6);
        drawRRectCurrentDate(canvas, paintRRectCurrentDate, 6);
        drawTextStepCurrentDate(canvas, paintDate, paintStepTextCurrentDate, 6);*/


    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void initDate() {
        this.listDate = new ArrayList<>();
        this.listDate.add("월");
        this.listDate.add("화");
        this.listDate.add("수");
        this.listDate.add("목");
        this.listDate.add("금");
        this.listDate.add("토");
        this.listDate.add("일");
    }

    public WeightChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initPaintText() {

        paintDate = new Paint();
        paintDate.setColor(Color.BLACK);
        paintDate.setStyle(Paint.Style.FILL);
        paintDate.setTextSize(spToPx(12, getContext()));

        paintRRect = new Paint();
        paintRRect.setColor(0x267f12b5);
        paintRRect.setStyle(Paint.Style.FILL);

        paintGrid = new Paint();
        paintGrid.setColor(Color.RED);
        paintGrid.setStyle(Paint.Style.STROKE);

        paintPointFill = new Paint();
        paintPointFill.setColor(Color.WHITE);
        paintPointFill.setStyle(Paint.Style.FILL);

        paintPointStroke = new Paint();
        paintPointStroke.setColor(0xFF7f12b5);
        paintPointStroke.setStrokeWidth(dpToPx(2, getContext()));
        paintPointStroke.setStyle(Paint.Style.STROKE);

        paintAverage = new Paint();
        paintAverage.setColor(0xFFf39e21);
        paintAverage.setStrokeWidth(dpToPx(2, getContext()));
        paintAverage.setStyle(Paint.Style.STROKE);
        paintAverage.setStrokeCap(Paint.Cap.ROUND);

        paintDate.setAntiAlias(true);
        paintRRect.setAntiAlias(true);
        paintGrid.setAntiAlias(true);
        paintPointFill.setAntiAlias(true);
        paintPointStroke.setAntiAlias(true);
        paintAverage.setAntiAlias(true);

        paintStepTextCurrentDate = new Paint();
        //paintStepTextCurrentDate.setColor(getResources().getColor(R.color.grey900, null));
        paintStepTextCurrentDate.setStyle(Paint.Style.FILL_AND_STROKE);
        //paintStepTextCurrentDate.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));
        paintStepTextCurrentDate.setTextSize(spToPx(15, getContext()));

        paintRRectCurrentDate = new Paint();
        //paintRRectCurrentDate.setColor(getResources().getColor(R.color.grey100, null));
        paintRRectCurrentDate.setStyle(Paint.Style.FILL);

        paintLineCurrentDate = new Paint();
        //paintLineCurrentDate.setColor(getResources().getColor(R.color.grey300, null));
        paintLineCurrentDate.setStrokeWidth(this.widthStrokeCurrentDate);
        paintLineCurrentDate.setStyle(Paint.Style.STROKE);


        paintGrid = new Paint();
        paintGrid.setColor(Color.RED);
        paintGrid.setStyle(Paint.Style.STROKE);



        paintGrid.setAntiAlias(true);

        paintStepTextCurrentDate.setAntiAlias(true);
        paintRRectCurrentDate.setAntiAlias(true);
        paintLineCurrentDate.setAntiAlias(true);
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
        int max = (int)Math.ceil(Collections.max(list));
        min = ((int) min / 5) * 5;
        max = ((int) max / 5) * 5 + 5;
        ArrayList<Integer> listResult = new ArrayList<>();
        for (int i = max; i >= min; i -= 5) {
            listResult.add(i);
        }
        return listResult;
    }


    private void calculateAxis() {
        this.unitX = widthDate + axisXDistanceDate;
        this.unitY = axisYdistanceNumber + axisYHeightNumber;

        this.listAxisX = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            this.listAxisX.add(axisXPaddingMon + i * unitX + widthDate / 2);
        }
        this.listAxisY = new ArrayList<>();
        for (int i = 0; i < this.listNumber.size(); i++) {
            this.listAxisY.add((axisXPaddingBot + axisXHeightDate + axisXYdistanceHeight) + i * unitY - axisYHeightNumber / 2);
        }
        Collections.reverse(this.listAxisY);
    }

    private void drawValueAxis(Canvas canvas, Paint paint) {
        for (int i = 0; i <= 6; i++) {
            canvas.drawText(this.listDate.get(i), axisXPaddingMon + i * (widthDate + axisXDistanceDate), getHeight() - axisXPaddingBot - axisXHeightDate, paint);
        }

        for (int i = 0; i < listNumber.size(); i++) {
            canvas.drawText(String.valueOf(listNumber.get(i)), axisYPaddingLeft, (axisXPaddingBot + axisXHeightDate + axisXYdistanceHeight) + i * (axisYdistanceNumber + axisYHeightNumber), paint);
        }
    }

    private void drawGrid(Canvas canvas, Paint paint) {
        for (int i = 0; i <= 6; i++) {
            canvas.drawLine(this.listAxisX.get(i), this.listAxisY.get(0), this.listAxisX.get(i), this.listAxisY.get(this.listAxisY.size() - 1), paint);
        }


        for (int i = 0; i < this.listAxisY.size(); i++) {
            canvas.drawLine(this.listAxisX.get(0), this.listAxisY.get(i), this.listAxisX.get(this.listAxisX.size() - 1), this.listAxisY.get(i), paint);
        }

    }

    private void drawRRect(Canvas canvas, Paint paint, Point center, float height) {
        canvas.drawRoundRect(new RectF(center.x - widthRRect / 2, center.y - height / 2, center.x + widthRRect / 2, center.y + height / 2), dpToPx(2, getContext()), dpToPx(2, getContext()), paint);
    }

    private void drawAllRRects(Canvas canvas, Paint paint, List<Float> heightList, List<Point> pointList) {
        for (int i = 0; i < pointList.size(); i++) {
            drawRRect(canvas, paint, pointList.get(i), heightList.get(i));
        }
    }

    private void drawPoint(Canvas canvas, Paint paintFill, Paint paintStroke, Point center) {
        canvas.drawCircle(center.x, center.y, dpToPx(4, getContext()), paintFill);
        canvas.drawCircle(center.x, center.y, dpToPx(4, getContext()), paintStroke);
    }

    private void drawLineValueDate(Canvas canvas, Paint paintFill, Paint paintStroke, List<Point> pointList) {
        for (int i = 0; i < pointList.size() - 1; i++) {
            canvas.drawLine(pointList.get(i).x, pointList.get(i).y, pointList.get(i + 1).x, pointList.get(i + 1).y, paintStroke);
            drawPoint(canvas, paintFill, paintStroke, pointList.get(i));
        }
        drawPoint(canvas, paintFill, paintStroke, pointList.get(pointList.size() - 1));
    }

    private void drawLineValueAverage(Canvas canvas, Paint paintStroke, List<Point> pointList) {

        for (int i = 0; i < pointList.size() - 1; i++) {
            canvas.drawLine(pointList.get(i).x, pointList.get(i).y, pointList.get(i + 1).x, pointList.get(i + 1).y, paintStroke);
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
        canvas.drawText(this.listWeight.get(indexCurrentDate) + "보", this.leftRRectCurrentDate + this.leftPaddingRRectCurrentDate,
                this.topPaddingRRectCurrentDate + axisXHeightDate + heightTextStepCurrentDate + this.topPaddingTextStepCurrentDate,
                paintValueStep);
    }


}
