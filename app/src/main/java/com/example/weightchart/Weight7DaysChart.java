package com.example.weightchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weight7DaysChart extends View {

    final int countAxisY = 3;
    final float radRRectCurrentDate = dpToPx(2, getContext());
    final float widthRRectCurrentDate = dpToPx(80, getContext());
    final float heightRRectCurrentDate = dpToPx(50, getContext());
    final float widthStrokeCurrentDate = dpToPx(2, getContext());
    private ArrayList<Float> listWeight;

    private float topPaddingRRectCurrentDate = dpToPx(6, getContext());
    private float leftPaddingRRectCurrentDate = dpToPx(11, getContext());
    final float topPaddingTextStepCurrentDate = dpToPx(5, getContext());

    final float widthRRectDescr = dpToPx(17, getContext());
    final float heightRRectDescr = dpToPx(5, getContext());
    final float radRRectDescr = dpToPx(2.5f, getContext());
    private final float paddingBotDescr = dpToPx(2f, getContext());
    final float distanceRRectTextDescr = dpToPx(6f, getContext());
    final float distanceTwoDescr = dpToPx(16, getContext());
    float marginLeftDescr;
    float marginRightDescr;

    Paint paintRRectCurrentDate;
    private Paint paintLineCurrentDate;
    private float leftRRectCurrentDate;
    private float heightTextStepCurrentDate;

    private String textUnit = "";


    float widthRRect = dpToPx(20, getContext());


    int axisXPaddingMon = 70;
    int axisXPaddingSun = 10;
    int axisXPaddingBot = dpToPx(16, getContext());
    int axisXHeightDate;
    int axisXDistanceDate;

    int axisXYdistanceHeight = 10;
    int axisXYdistanceWidth = 20;
    float heightDescr;

    float axisYdistanceNumber;
    int axisYmaxLengthNumber;
    float axisYPaddingTop = heightRRectCurrentDate;
    int axisYPaddingLeft = 10;
    int axisYHeightNumber;

    int widthDate;
    ArrayList<Integer> listNumber;
    ArrayList<String> listDate;
    HashMap<Integer, String> descr;
    List<Float> listWidthTextDescr;

    ArrayList<Float> listAxisX;
    ArrayList<Float> listAxisY;
    float unitXDay;
    float unitXSecond;
    float unitYSpace;

    Paint paintStepTextCurrentDate;
    Paint paintDate;
    Paint paintRRect;
    Paint paintRRectDescr;
    Paint paintGrid;
    Paint paintPointFill;
    Paint paintPointStroke;
    Paint paintAverage;
    private float unitY;
    private int minAxisY;


    public void setDescr(HashMap<Integer, String> descr, String textUnit) {
        this.descr = descr;
        this.textUnit = textUnit;
        this.invalidate();
    }


    public Weight7DaysChart(Context context) {
        super(context);
    }

    public Weight7DaysChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDate();
        initPaintText();
    }
    public void initFakeData(){
        this.listWeight = new ArrayList<>();
        listWeight.add((float)getRandomNumber(50,70));
        listWeight.add((float)getRandomNumber(50,70));
        listWeight.add((float)getRandomNumber(50,70));
        listWeight.add((float)getRandomNumber(50,70));
        listWeight.add((float)getRandomNumber(50,70));
        listWeight.add((float)getRandomNumber(50,70));
        listWeight.add((float)getRandomNumber(50,70));
        this.listNumber = genAxisYValue(listWeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Rect boundDate = new Rect();
        paintDate.getTextBounds("월", 0, 1, boundDate);
        axisXHeightDate = boundDate.height();
        axisYHeightNumber = axisXHeightDate;
        heightDescr = axisYHeightNumber;
        widthDate = boundDate.width();

        Rect boundNumber = new Rect();
        paintDate.getTextBounds("999", 0, 3, boundNumber);
        axisYmaxLengthNumber = boundNumber.width();
        axisYdistanceNumber = (getHeight() - axisYPaddingTop - (axisXYdistanceHeight + axisXPaddingBot + heightDescr + paddingBotDescr + axisXHeightDate)
                - countAxisY * axisYHeightNumber) / (countAxisY - 1);

        axisXDistanceDate = (getWidth() - boundDate.width() * 7 - (axisXPaddingMon + axisYmaxLengthNumber + axisXYdistanceWidth + axisXPaddingSun)) / 6;

        Rect boundValueStepCurrentDate = new Rect();
        paintStepTextCurrentDate.getTextBounds("보", 0, 1, boundValueStepCurrentDate);
        this.heightTextStepCurrentDate = boundValueStepCurrentDate.height();

///////////////////////////////////////////////////


////////////////////////////////////////////
        calculateAxis();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initFakeData();

        this.unitY = this.unitYSpace / (this.listNumber.get(1) - this.listNumber.get(0));

        //drawGrid(canvas, paintGrid);
        drawValueAxis(canvas, paintDate);

        List<Point> listPoint = new ArrayList<>();
        List<Float> listAverageTest = new ArrayList<>();
        List<Float> listHeightRRectTest = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            listPoint.add(convertWeightToPoint(listWeight.get(i), i));
        }

        for (int i = 0; i <= 6; i++) {
            listHeightRRectTest.add((float) getRandomNumber(0, Math.round(dpToPx(60, getContext()))));
        }


        float randWeight;
        for (int i = 0; i < listWeight.size(); i++) {
            randWeight = this.listWeight.get(i) + getRandomNumber(-1, Math.round(dpToPx(1, getContext())));
            if (randWeight < listNumber.get(0))
                randWeight = listNumber.get(0);
            else if (randWeight > listNumber.get(listNumber.size() - 1))
                randWeight = listNumber.get(listNumber.size() - 1);
            listAverageTest.add(randWeight);

            randWeight = this.listWeight.get(i) + getRandomNumber(-1, Math.round(dpToPx(1, getContext())));
            if (randWeight < listNumber.get(0))
                randWeight = listNumber.get(0);
            else if (randWeight > listNumber.get(listNumber.size() - 1))
                randWeight = listNumber.get(listNumber.size() - 1);
            listAverageTest.add(randWeight);

        }
        listAverageTest.remove(listAverageTest.size() - 1);

        drawLineCurrentDate(canvas, paintLineCurrentDate, 6);

        drawAllRRects(canvas, paintRRect, listHeightRRectTest, listPoint);
        drawLineValueAverage(canvas, paintAverage, listAverageTest);
        drawLineValueDate(canvas, paintPointFill, paintPointStroke, listPoint);

        drawRRectCurrentDate(canvas, paintRRectCurrentDate, 6);
        drawTextStepCurrentDate(canvas, paintDate, paintStepTextCurrentDate, 6);
        if (this.descr != null) {
            calculateDescr();
            drawDescr(canvas);
        }
    }

    private void calculateDescr() {

        if (this.descr != null) {
            Rect boundDescr;
            listWidthTextDescr = new ArrayList<>();
            float sumWidthTextDescr = 0;
            for (Map.Entry<Integer, String> entry : descr.entrySet()) {
                boundDescr = new Rect();
                paintDate.getTextBounds(entry.getValue(), 0, entry.getValue().length(), boundDescr);
                listWidthTextDescr.add((float) boundDescr.width());
                sumWidthTextDescr += boundDescr.width();
            }
            this.marginLeftDescr = (getWidth() - sumWidthTextDescr - descr.size() * (widthRRectDescr + distanceRRectTextDescr)
                    - (descr.size() - 1) * distanceTwoDescr) / 2f;
            this.marginRightDescr = this.marginLeftDescr;
        }
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

    public Weight7DaysChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        paintRRectDescr = new Paint();
        paintRRectDescr.setStyle(Paint.Style.FILL);

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
        paintStepTextCurrentDate.setColor(getResources().getColor(R.color.grey900, null));
        paintStepTextCurrentDate.setStyle(Paint.Style.FILL_AND_STROKE);
        //paintStepTextCurrentDate.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));
        paintStepTextCurrentDate.setTextSize(spToPx(15, getContext()));

        paintRRectCurrentDate = new Paint();
        paintRRectCurrentDate.setColor(getResources().getColor(R.color.grey100, null));
        paintRRectCurrentDate.setStyle(Paint.Style.FILL);

        paintLineCurrentDate = new Paint();
        paintLineCurrentDate.setColor(getResources().getColor(R.color.grey300, null));
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
        paintRRectDescr.setAntiAlias(true);

    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public ArrayList<Integer> genAxisYValue(ArrayList<Float> list) {

        this.minAxisY = Math.round(Collections.min(list));
        int max = (int) Math.ceil(Collections.max(list));

        int mid = (int) Math.ceil((float) (this.minAxisY + max) / 2);
        max = this.minAxisY + (mid - this.minAxisY) * 2;
        ArrayList<Integer> listResult = new ArrayList<>();
        listResult.add(this.minAxisY);
        listResult.add(mid);
        listResult.add(max);
        return listResult;
    }


    private void calculateAxis() {
        this.unitXDay = widthDate + axisXDistanceDate;
        this.unitXSecond = unitXDay / 86400;
        this.unitYSpace = axisYdistanceNumber + axisYHeightNumber;

        this.listAxisX = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            this.listAxisX.add(axisXPaddingMon + i * unitXDay + widthDate / 2);
        }
        this.listAxisY = new ArrayList<>();
        for (int i = 0; i < this.countAxisY; i++) {
            this.listAxisY.add(getHeight() - axisXPaddingBot - heightDescr - paddingBotDescr - axisXHeightDate - axisXYdistanceHeight - axisYHeightNumber / 2 - i * unitYSpace);
        }
    }

    private void drawValueAxis(Canvas canvas, Paint paint) {
        for (int i = 0; i <= 6; i++) {
            canvas.drawText(this.listDate.get(i), axisXPaddingMon + i * (widthDate + axisXDistanceDate), getHeight() - axisXPaddingBot - heightDescr - paddingBotDescr, paint);
        }

        for (int i = 0; i < listNumber.size(); i++) {
            canvas.drawText(String.valueOf(listNumber.get(i)), axisYPaddingLeft, this.listAxisY.get(i) + axisYHeightNumber / 2, paint);
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

    private Point convertWeightToPoint(float weight, float indexX) {
        return new Point(Math.round(this.listAxisX.get(0) + indexX * unitXDay), Math.round(listAxisY.get(0) - (weight - this.listNumber.get(0)) * unitY));
    }

    private void drawLineValueAverage(Canvas canvas, Paint paintStroke, List<Float> averageList) {
        float indexX = 0;
        for (int i = 0; i < averageList.size() - 1; i++) {
            Point start = convertWeightToPoint(averageList.get(i), indexX);
            indexX += 0.5;
            Point end = convertWeightToPoint(averageList.get(i + 1), indexX);
            canvas.drawLine(start.x, start.y, end.x, end.y, paintStroke);
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
        canvas.drawText(this.listWeight.get(indexCurrentDate) + this.textUnit, this.leftRRectCurrentDate + this.leftPaddingRRectCurrentDate,
                this.topPaddingRRectCurrentDate + axisXHeightDate + heightTextStepCurrentDate + this.topPaddingTextStepCurrentDate,
                paintValueStep);
    }

    private void drawDescr(Canvas canvas) {
        int index = 0;
        float left;
        for (Map.Entry<Integer, String> entry : descr.entrySet()) {
            paintRRectDescr.setColor(getContext().getColor(entry.getKey()));
            left = marginLeftDescr +
                    index * (widthRRectDescr + distanceRRectTextDescr + listWidthTextDescr.get(index == 0 ? 0 : index - 1) + distanceTwoDescr);
            canvas.drawRoundRect(left,
                    getHeight() - paddingBotDescr - heightDescr / 2 - heightRRectDescr / 2,
                    left + widthRRectDescr, getHeight() - paddingBotDescr - heightDescr / 2 + heightRRectDescr / 2,
                    radRRectDescr, radRRectDescr, paintRRectDescr);
            canvas.drawText(entry.getValue(), left + widthRRectDescr + distanceRRectTextDescr, getHeight() - paddingBotDescr, paintDate);
            index++;
        }
    }
}
