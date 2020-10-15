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
import androidx.core.content.res.ResourcesCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sleep30DaysChart extends View {
    private ArrayList<Date> listFirstTime;
    private ArrayList<String> listDate;
    private ArrayList<Integer> listDateIndex;
    private ArrayList<Float> listWidthDate;
    private float unitY;


    private static class PeriodTime {
        private Date begin;
        private Date end;

        public Date getBegin() {
            return begin;
        }

        public void setBegin(Date begin) {
            this.begin = begin;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public PeriodTime(Date begin, Date end) {
            this.begin = begin;
            this.end = end;
        }
    }

    private static class DataPerDate {
        ArrayList<Sleep30DaysChart.PeriodTime> listPeriodTime;
        String textTimeSleep;
        String textAverageWeek;
        int indexDateWeek;

        public DataPerDate(ArrayList<Sleep30DaysChart.PeriodTime> listPeriodTime, String textTimeSleep, String textAverageWeek, int indexDateWeek) {
            this.listPeriodTime = listPeriodTime;
            this.textTimeSleep = textTimeSleep;
            this.textAverageWeek = textAverageWeek;
            this.indexDateWeek = indexDateWeek;
        }
    }

    private final SimpleDateFormat formatFullDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat formatTimeAxis = new SimpleDateFormat("HH:mm");


    Paint paintStepTextCurrentDate;
    Paint paintDate;
    Paint paintRRect;
    Paint paintRRectDescr;
    Paint paintGrid;
    Paint paintPointFill;
    Paint paintPointStroke;
    Paint paintLineSleep;
    Paint paintRRectCurrentDate;
    Paint paintLineCurrentDate;
    Paint paintTextSleep;
    Paint paintTextAverage;
    private Paint paintTime;


    float sizeDateHeight;
    float sizeDateWidth;
    float sizeDatePaddingLeft = dpToPx(5, getContext());
    float sizeDatePaddingBottom = dpToPx(10, getContext());


    float sizeTimeHeight;
    float sizeTimeWidth;
    float sizeTimeLastPaddingRight = dpToPx(5, getContext());
    float sizeTimeFirstPaddingLeft = dpToPx(50, getContext());
    float sizeTimePaddingTop = dpToPx(5, getContext());


    final float sizeStrokeTimeSleepWidth = dpToPx(3, getContext());



    float distanceAxisXYHeight = dpToPx(24, getContext());
    float distanceAxisXYPaddingTopFirstAxisY = dpToPx(5, getContext());
    float distanceAxisXYPaddingBotLastAxisY = dpToPx(10, getContext());


    private float sizeStrokeCurrentDateWidth = 2;
    private ArrayList<String> listDateOfWeek;
    private ArrayList<Date> listDateAxisX;
    private ArrayList<Float> listAxisY;

    private ArrayList<Sleep30DaysChart.DataPerDate> listDataPerdate;


    private float spaceValueX;
    private float dxOneMiliSecond;
    private float spaceValueY = dpToPx(20, getContext());
    private float firstX;
    private float firstY;
    private int firstXMiliSecond;

    public Sleep30DaysChart(Context context) {
        super(context);
    }

    public Sleep30DaysChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDate();
        initPaintText();
    }

    public Sleep30DaysChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initPaintText() {

        paintDate = new Paint();
        paintDate.setColor(getResources().getColor(R.color.grey900, null));
        paintDate.setStyle(Paint.Style.FILL);
        paintDate.setStrokeCap(Paint.Cap.ROUND);
        paintDate.setTextSize(spToPx(12, getContext()));
        paintDate.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));

        paintRRect = new Paint();
        paintRRect.setColor(getResources().getColor(R.color.dailySleep, null));
        paintRRect.setAlpha(8);
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

        paintLineSleep = new Paint();
        paintLineSleep.setColor(getResources().getColor(R.color.dailySleep, null));
        paintLineSleep.setStrokeWidth(this.sizeStrokeTimeSleepWidth);
        paintLineSleep.setStyle(Paint.Style.STROKE);
        paintLineSleep.setStrokeCap(Paint.Cap.ROUND);

        paintTextSleep = new Paint();
        paintTextSleep.setColor(getResources().getColor(R.color.dailySleep, null));
        paintTextSleep.setStyle(Paint.Style.FILL);
        paintTextSleep.setStrokeCap(Paint.Cap.ROUND);
        paintTextSleep.setTextSize(spToPx(10, getContext()));
        //paintTextSleep.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));


        paintTextAverage = new Paint();
        paintTextAverage.setColor(getResources().getColor(R.color.grey900, null));
        paintTextAverage.setStyle(Paint.Style.FILL);
        paintTextAverage.setStrokeCap(Paint.Cap.ROUND);
        paintTextAverage.setTextSize(spToPx(12, getContext()));
        paintTextSleep.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_medium));


        paintTime = new Paint();
        paintTime.setColor(getResources().getColor(R.color.grey600, null));
        paintTime.setStyle(Paint.Style.FILL);
        paintTime.setStrokeCap(Paint.Cap.ROUND);
        paintTime.setTextSize(spToPx(12, getContext()));
        paintTime.setTypeface(ResourcesCompat.getFont(getContext(), R.font.notosanskr_regular));


        paintDate.setAntiAlias(true);
        paintRRect.setAntiAlias(true);
        paintGrid.setAntiAlias(true);
        paintPointFill.setAntiAlias(true);
        paintPointStroke.setAntiAlias(true);
        paintLineSleep.setAntiAlias(true);

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
        paintTextSleep.setAntiAlias(true);

    }

    private void initFakeData() throws ParseException {

        this.listDataPerdate = new ArrayList<>();


        ArrayList<Sleep30DaysChart.PeriodTime> listPeriodTimes;

        listPeriodTimes = new ArrayList<>();
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-12 20:09:09"), formatFullDateTime.parse("2020-10-12 23:19:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-12 23:50:09"), formatFullDateTime.parse("2020-10-13 01:19:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-13 02:30:09"), formatFullDateTime.parse("2020-10-13 03:34:19")));

        this.listDataPerdate.add(new Sleep30DaysChart.DataPerDate(listPeriodTimes, "7시간 45분", "8시간 8분", 0));

        listPeriodTimes = new ArrayList<>();
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-13 21:09:09"), formatFullDateTime.parse("2020-10-13 22:19:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-13 23:30:09"), formatFullDateTime.parse("2020-10-14 01:00:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-14 01:30:09"), formatFullDateTime.parse("2020-10-14 04:00:00")));

        this.listDataPerdate.add(new Sleep30DaysChart.DataPerDate(listPeriodTimes, "7시간 42분", "7시간 12분", 1));


        listPeriodTimes = new ArrayList<>();
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-14 20:40:09"), formatFullDateTime.parse("2020-10-15 03:19:19")));

        this.listDataPerdate.add(new Sleep30DaysChart.DataPerDate(listPeriodTimes, "8시간 0분", "7시간 45분", 2));

        listPeriodTimes = new ArrayList<>();
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-15 21:30:09"), formatFullDateTime.parse("2020-10-15 22:19:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-16 01:50:09"), formatFullDateTime.parse("2020-10-16 02:00:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-16 02:30:09"), formatFullDateTime.parse("2020-10-16 03:00:19")));

        this.listDataPerdate.add(new Sleep30DaysChart.DataPerDate(listPeriodTimes, "7시간 30분", "8시간 0분", 3));

        listPeriodTimes = new ArrayList<>();
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-16 20:09:09"), formatFullDateTime.parse("2020-10-16 23:19:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-16 23:50:09"), formatFullDateTime.parse("2020-10-17 01:19:19")));
        listPeriodTimes.add(new Sleep30DaysChart.PeriodTime(formatFullDateTime.parse("2020-10-17 02:30:09"), formatFullDateTime.parse("2020-10-17 03:34:19")));

        this.listDataPerdate.add(new Sleep30DaysChart.DataPerDate(listPeriodTimes, "8시간 21분", "7시간 12분", 4));

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
        sizeDateHeight = boundDateOfWeek.height();
        sizeDateWidth = boundDateOfWeek.width();

        Rect boundTime = new Rect();
        paintTime.getTextBounds("99:99", 0, 5, boundTime);
        sizeTimeHeight = boundTime.height();
        sizeTimeWidth = boundTime.width();


    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private void calculateAxisX() {
        float widthListAxisValueX = getWidth() - sizeDatePaddingLeft - sizeTimeLastPaddingRight - sizeTimeFirstPaddingLeft;
        this.spaceValueX = (widthListAxisValueX - listDateAxisX.size() * sizeTimeWidth) / (listDateAxisX.size() - 1);
        this.firstX = sizeTimeFirstPaddingLeft + sizeTimeWidth / 2;


        this.dxOneMiliSecond = (this.spaceValueX + this.sizeTimeWidth) / (this.listDateAxisX.get(1).getTime() - this.listDateAxisX.get(0).getTime());
    }

    private void calculateAxisY() {
        this.firstY = sizeTimePaddingTop + sizeTimeHeight + distanceAxisXYPaddingTopFirstAxisY;
        this.unitY = (getHeight() - this.firstY - distanceAxisXYPaddingBotLastAxisY) / 29;
        this.listAxisY = new ArrayList<>();
        for (int i = 0; i <= 29; i++) {
            this.listAxisY.add(this.firstY + i * unitY);
        }
    }

    private void generateValueAxisY() throws ParseException {

        Calendar calendarFirstDate = Calendar.getInstance();
        calendarFirstDate.setTime(new Date());
        calendarFirstDate.set(Calendar.HOUR, 0);
        calendarFirstDate.set(Calendar.MINUTE, 0);
        calendarFirstDate.set(Calendar.SECOND, 0);
        calendarFirstDate.add(Calendar.DATE, -29);


        Calendar calendarLastDate = Calendar.getInstance();
        calendarLastDate.setTime(new Date());
        calendarLastDate.set(Calendar.HOUR, 23);
        calendarLastDate.set(Calendar.MINUTE, 59);
        calendarLastDate.set(Calendar.SECOND, 59);


        listDate = new ArrayList<>();
        listDateIndex = new ArrayList<>();
        int index = -1;
        while (calendarFirstDate.before(calendarLastDate)) {
            index++;
            if (calendarFirstDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                listDate.add(calendarFirstDate.get(Calendar.DATE) + "일");
                listDateIndex.add(index);
            }
            calendarFirstDate.add(Calendar.DATE, 1);
        }

        this.listWidthDate = new ArrayList<>();
        Rect bound;
        for (String date : listDate) {
            bound = new Rect();
            paintDate.getTextBounds(date, 0, date.length(), bound);
            listWidthDate.add((float) bound.width());
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calculateSize();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            initFakeData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            generateValueAxisY();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.listDateAxisX = new ArrayList<>();
        Calendar calendar;

        int firstHours = 20;
        int firstMinute = 00;
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, firstHours);
        calendar.set(Calendar.MINUTE, firstMinute);
        this.listDateAxisX.add(calendar.getTime());

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.add(Calendar.DATE, 1);
        this.listDateAxisX.add(calendar.getTime());

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 04);
        calendar.set(Calendar.MINUTE, 00);
        calendar.add(Calendar.DATE, 1);
        this.listDateAxisX.add(calendar.getTime());

        calculateAxisX();
        drawValueAxisX(canvas, paintTime, listDateAxisX);
        calculateAxisY();
        drawValueAxisY(canvas, paintDate);
        initListFirstTime(firstHours, firstMinute);


        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 04);
        calendar.set(Calendar.MINUTE, 00);
        Point point = convertTimeToPoint(calendar.getTime(), 2);

        drawAveragePerdate(canvas);
        drawAllGroupPerDate(canvas);
    }

    private void initListFirstTime(int firstHours, int firstMinutes) {
        this.listFirstTime = new ArrayList<>();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());
        currentDate.set(Calendar.HOUR_OF_DAY, firstHours);
        currentDate.set(Calendar.MINUTE, firstMinutes);
        int dateOfWeekCurrent = currentDate.get(Calendar.DAY_OF_WEEK);
        currentDate.add(Calendar.DATE, -(dateOfWeekCurrent > 2 ? dateOfWeekCurrent - 2 : 6));// current date become monday of week
        for (int i = 0; i <= 6; i++) {
            this.listFirstTime.add(currentDate.getTime());
            currentDate.add(Calendar.DATE, 1);
        }

    }

    private void drawValueAxisX(Canvas canvas, Paint paint, List<Date> listDateX) {
        for (int i = 0; i < listDateX.size(); i++) {
            canvas.drawText(formatTimeAxis.format(listDateX.get(i)), sizeTimeFirstPaddingLeft + i * (sizeTimeWidth + spaceValueX), sizeTimePaddingTop + sizeTimeHeight, paint);
        }
    }

    private void drawValueAxisY(Canvas canvas, Paint paint) {
        for (int i = 0; i < this.listDate.size(); i++) {
            canvas.drawText(this.listDate.get(i), this.sizeDatePaddingLeft, this.listAxisY.get(this.listDateIndex.get(i)) - sizeDateHeight / 2, paint);
        }
    }

    private Point convertTimeToPoint(Date time, int indexDateBelongTo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        float dx = this.firstX + this.dxOneMiliSecond * (calendar.getTimeInMillis() - this.listFirstTime.get(indexDateBelongTo).getTime());

        return new Point(Math.round(dx), Math.round(this.listAxisY.get(indexDateBelongTo)));
    }


    private void drawAllGroupPerDate(Canvas canvas) {
        for (Sleep30DaysChart.DataPerDate data : this.listDataPerdate) {
            drawGroupPerDate(canvas, data);
        }
    }

    private void drawGroupPerDate(Canvas canvas, Sleep30DaysChart.DataPerDate dataPerDate) {

/*        Point end,begin;
        float dyTopGroup = listAxisY.get(dataPerDate.indexDateWeek) - this.heightGroupPerDate/2;
        for(Sleep30DaysChart.PeriodTime periodTime : dataPerDate.listPeriodTime){
            end = convertTimeToPoint(periodTime.end, dataPerDate.indexDateWeek);
            begin = convertTimeToPoint(periodTime.begin, dataPerDate.indexDateWeek);
            canvas.drawLine(begin.x,dyTopGroup,end.x,dyTopGroup, paintLineSleep);
        }

        Rect boundText = new Rect();
        paintTextSleep.getTextBounds(dataPerDate.textTimeSleep, 0, dataPerDate.textTimeSleep.length(), boundText);
        int width = boundText.width();
        int height = boundText.height();

        float dyTopRRect = dyTopGroup + sizeStrokeTimeSleepWidth + distanceTestTimeSleepToStroke;
        canvas.drawRoundRect(this.firstX,dyTopRRect,this.firstX + width + sizeRRectTextTimeSleepPaddingLeft + sizeRRectTextTimeSleepPaddingRight, dyTopRRect + sizeRRectTextTimeSleepHeight,sizeRRectTextTimeSleepRad,sizeRRectTextTimeSleepRad,paintRRect);
        canvas.drawText(dataPerDate.textTimeSleep,this.firstX + sizeRRectTextTimeSleepPaddingLeft,dyTopRRect + (this.sizeRRectTextTimeSleepHeight - height)/2 + height,paintTextSleep);*/
    }

    private void drawAveragePerdate(Canvas canvas) {
/*        ///title average week:
        canvas.drawText(averageTitle,getWidth() - sizeTitleAveragePaddingRight - sizeTitleAverageWidth,sizeTimePaddingTop + sizeTimeHeight,paintTime);
        ///title average week at each day:
        for(Sleep30DaysChart.DataPerDate data : this.listDataPerdate){
            canvas.drawText(data.textAverageWeek,getWidth() - sizeTitleAveragePaddingRight - sizeTitleAverageWidth,this.listAxisY.get(data.indexDateWeek),paintDate);
        }*/
    }

}
