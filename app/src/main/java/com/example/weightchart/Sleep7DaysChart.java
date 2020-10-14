package com.example.weightchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sleep7DaysChart extends View {

    private static final SimpleDateFormat formatTimeAxis = new SimpleDateFormat("HH:mm");


    Paint paintStepTextCurrentDate;
    Paint paintDate;
    Paint paintRRect;
    Paint paintRRectDescr;
    Paint paintGrid;
    Paint paintPointFill;
    Paint paintPointStroke;
    Paint paintAverage;
    Paint paintRRectCurrentDate;
    Paint paintLineCurrentDate;

    float paddingLeft = dpToPx(5, getContext());

    float sizeDateOfWeekHeight;
    float sizeDateOfWeekWidth;
    float sizeDateOfWeekPaddingLeft = dpToPx(5, getContext());
    float sizeDateOfWeekPaddingBottom = dpToPx(10, getContext());

    float sizeTitleAverageHeight;
    float sizeTitleAverageWidth;
    float sizeTitleAveragePaddingRight = dpToPx(5, getContext());

    float sizeTimeHeight;
    float sizeTimeWidth;
    float sizeTimePaddingTop = dpToPx(5, getContext());

    float distanceAverageToLastValueAxisX = dpToPx(5, getContext());

    float distanceAxisXYWidth = dpToPx(5, getContext());
    float distanceAxisXYHeight = dpToPx(5, getContext());

    private float sizeStrokeCurrentDateWidth = 2;
    private ArrayList<String> listDateOfWeek;
    private ArrayList<Date> listDateAxisX;
    private ArrayList<Float> listAxisY;


    private float spaceValueX;
    private float dxOneSecond;
    private float spaceValueY = dpToPx(20, getContext());
    private float firstX;
    private int firstXSecond;

    public Sleep7DaysChart(Context context) {
        super(context);
    }

    public Sleep7DaysChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDate();
        initPaintText();
    }

    public Sleep7DaysChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        paintLineCurrentDate.setStrokeWidth(this.sizeStrokeCurrentDateWidth);
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

    private void initDate() {
        this.listDateOfWeek = new ArrayList<>();
        this.listDateOfWeek.add("월");
        this.listDateOfWeek.add("화");
        this.listDateOfWeek.add("수");
        this.listDateOfWeek.add("목");
        this.listDateOfWeek.add("금");
        this.listDateOfWeek.add("토");
        this.listDateOfWeek.add("일");
    }

    private void calculateSize() {
        Rect boundDateOfWeek = new Rect();
        paintDate.getTextBounds("월", 0, 1, boundDateOfWeek);
        sizeDateOfWeekHeight = boundDateOfWeek.height();
        sizeDateOfWeekWidth = boundDateOfWeek.width();

        Rect boundTime = new Rect();
        paintDate.getTextBounds("99:99", 0, 5, boundTime);
        sizeTimeHeight = boundTime.height();
        sizeTimeWidth = boundTime.width();

        Rect boundTitleAverage = new Rect();
        paintDate.getTextBounds("요일 평균 시간", 0, 8, boundTitleAverage);
        sizeTitleAverageHeight = boundTitleAverage.height();
        sizeTitleAverageWidth = boundTitleAverage.width();

    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private void calculateAxisX() {
        float widthListAxisValueX = getWidth() - sizeTitleAveragePaddingRight - sizeTitleAverageWidth - distanceAverageToLastValueAxisX - sizeDateOfWeekWidth - sizeDateOfWeekPaddingLeft - sizeDateOfWeekWidth - distanceAxisXYWidth;
        this.spaceValueX = (widthListAxisValueX - listDateAxisX.size() * sizeTimeWidth) / (listDateAxisX.size() - 1);
        this.firstX = paddingLeft + sizeDateOfWeekWidth + distanceAxisXYWidth + sizeTimeWidth/2;
        this.firstXSecond = convertDateToSecond(this.listDateAxisX.get(0));
        this.dxOneSecond = (this.spaceValueX+this.sizeTimeWidth)/(convertDateToSecond(listDateAxisX.get(1)) - convertDateToSecond(listDateAxisX.get(0)));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calculateSize();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.listDateAxisX = new ArrayList<>();
        Calendar calendar;

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        this.listDateAxisX.add(calendar.getTime());

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        this.listDateAxisX.add(calendar.getTime());

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 00);
        this.listDateAxisX.add(calendar.getTime());

        calculateAxisX();
        drawValueAxisX(canvas, paintDate, listDateAxisX);
        drawValueAxisY(canvas, paintDate);


        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.add(Calendar.DATE,-2);
        Point point = convertTimeToPoint(calendar.getTime());


        canvas.drawCircle(this.firstX,point.y,50,paintGrid);
    }

    private void drawValueAxisX(Canvas canvas, Paint paint, List<Date> listDateX) {
        for (int i = 0; i < listDateX.size(); i++) {
            canvas.drawText(formatTimeAxis.format(listDateX.get(i)), paddingLeft + sizeDateOfWeekWidth + distanceAxisXYWidth + i * (sizeTimeWidth + spaceValueX), sizeTimePaddingTop + sizeTimeHeight, paint);
        }
    }

    private void drawValueAxisY(Canvas canvas, Paint paint) {
        this.listAxisY = new ArrayList<>();
        float dy;
        for (int i = 0; i < listDateOfWeek.size(); i++) {
            dy = (sizeTimePaddingTop + sizeTimeHeight + distanceAxisXYHeight)
                    + sizeDateOfWeekHeight + (sizeDateOfWeekHeight + spaceValueY) * i;
            canvas.drawText(listDateOfWeek.get(i), paddingLeft, dy, paint);
            this.listAxisY.add(dy - sizeDateOfWeekHeight/2);
        }
    }

    private Point convertTimeToPoint(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        float dx = this.firstX + this.dxOneSecond*(convertDateToSecond(time) - this.firstXSecond);

        int dateOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int indexY = dateOfWeek < 2 ? 6 : dateOfWeek - 2;
        return new Point(Math.round(dx),Math.round(this.listAxisY.get(indexY)));
    }

    private int convertDateToSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return hours * 3600 + minutes * 60 + second;
    }
}
