package com.example.weightchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class WeightChart extends View {

    int axisXPaddingMon = 70;
    int axisXPaddingSun = 10;
    int axisXPaddingBot = 5;
    int axisXHeightDate;
    int axisXDistanceDate;

    int axisXYdistanceHeight = 20;
    int axisXYdistanceWidth = 20;

    int axisYdistanceNumber;
    int axisYmaxLengthNumber;
    int axisYPaddingTop = 100;
    int axisYPaddingLeft = 10;
    int axisYHeightNumber;

    int widthDate;
    ArrayList<Integer> axisY;

    Paint paintDate;
    Paint paintNumber;

    public WeightChart(Context context) {
        super(context);
    }

    public WeightChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaintText();
        ArrayList<Integer> listDataFake = new ArrayList<>();
        listDataFake.add(47);
        listDataFake.add(68);
        listDataFake.add(70);
        listDataFake.add(81);
        listDataFake.add(53);
        listDataFake.add(50);
        listDataFake.add(60);
        this.axisY = genAxisY(listDataFake);
    }

    public WeightChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initPaintText() {
        paintDate = new Paint();
        paintDate.setColor(Color.BLACK);
        paintDate.setStyle(Paint.Style.FILL);
        paintDate.setTextSize(spToPx(16, getContext()));
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public ArrayList<Integer> genAxisY(ArrayList<Integer> list) {

        int min = Collections.min(list);
        int max = Collections.max(list);
        min = ((int) min / 5) * 5;
        max = ((int) max / 5) * 5 + 5;
        ArrayList<Integer> listResult = new ArrayList<>();
        for (int i = max; i >= min; i -= 5) {
            listResult.add(i);
        }
        return listResult;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Rect boundDate = new Rect();
        paintDate.getTextBounds("월", 0, 1, boundDate);
        axisXHeightDate= boundDate.height();
        axisYHeightNumber = axisXHeightDate;
        widthDate = boundDate.width();

        Rect boundNumber = new Rect();
        paintDate.getTextBounds("999", 0, 3, boundNumber);
        axisYmaxLengthNumber = boundNumber.width();
        axisYdistanceNumber = (getHeight() - axisYPaddingTop - (axisXYdistanceHeight + axisXPaddingBot + axisXHeightDate)
                - axisY.size()*axisYHeightNumber)/(axisY.size()-1);

        axisXDistanceDate = (getWidth() - boundDate.width() * 7 - (axisXPaddingMon + axisYmaxLengthNumber + axisXYdistanceWidth + axisXPaddingSun)) / 6;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <= 6; i++) {
            canvas.drawText("월", axisXPaddingMon + i * (widthDate + axisXDistanceDate), getHeight() - axisXPaddingBot - axisXHeightDate, paintDate);
        }

        for (int i = 0; i < axisY.size(); i++) {
            canvas.drawText(String.valueOf(axisY.get(i)),axisYPaddingLeft , (axisXPaddingBot + axisXHeightDate + axisXYdistanceHeight) + i*(axisYdistanceNumber+axisYHeightNumber),paintDate);
        }

    }
}
