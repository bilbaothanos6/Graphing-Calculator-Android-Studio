package com.example.graphingcalculatorjava;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MyCanvas extends View {

    private int height;
    private int width;

    private double leftNum;
    private double rightNum;
    private double topNum;
    private double bottomNum;

    private int leftEdge;
    private int rightEdge;
    private int topEdge;
    private int bottomEdge;

    private double[] coefficients;

    private int degrees;

    private int graphColor;

    private ArrayList<Equation> equations;

    private boolean[] visibilities;

    public MyCanvas(Context context){
        super(context);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    protected void onDraw(Canvas canvas){
        createAxes(canvas);
        drawGraph(canvas);
    }

    private void drawGraph(Canvas canvas){
        for (int j = 0; j < equations.size(); j++){
            if(visibilities[j]){
                graphColor = equations.get(j).getColor();
                coefficients = equations.get(j).getCoefficients();
                degrees = equations.get(j).getDegree();

                Paint paint = new Paint();
                paint.setColor(graphColor);
                paint.setStrokeWidth(10);

                final double spacing = 0.0001 * rightNum;

                for (double i = leftNum; i <= rightNum - spacing; i += spacing){
                    double output1 = getOutput(i);
                    double output2 = getOutput(i + spacing);

                    double x1, x2;
                    double y1, y2;

                    x1 = (i / rightNum) * (rightEdge - 1.0 * width / 2) + (1.0 * width / 2);
                    x2 = ((i + spacing) / rightNum) * (rightEdge - 1.0 * width / 2) + (1.0 * width / 2);

                    y1 = (output1 / topNum) * (topEdge - 1.0 * height / 2) + (1.0 * height / 2);
                    y2 = (output2 / topNum) * (topEdge - 1.0 * height / 2) + (1.0 * height / 2);

                    canvas.drawLine((float) x1, (float) y1, (float) x2, (float) y2, paint);
                }
            }
        }
    }

    private double getOutput(double x){

        double val = coefficients[1];
        Log.d("degrees", "degrees: " + degrees);
        for (int i = 0; i < degrees; i++){
            val += coefficients[i] * Math.pow(x, degrees - i);
        }
        return val;
    }

    private void createAxes(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);

        final int margin = 20;

        leftEdge = margin;
        rightEdge = width - margin;
        topEdge = height/2 - (width - 2 * margin)/2;
        bottomEdge = height/2 + (width - 2 * margin)/2;

        canvas.drawLine(margin, height/2, width - margin, height/2, paint);
        canvas.drawLine(width/2, height/2 - (width - 2 * margin)/2, width/2, height/2 + (width - 2 * margin)/2, paint);

        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(leftNum + "", leftEdge, height / 2 + 3 * margin, paint);
        canvas.drawLine(leftEdge, height/2 + margin, leftEdge, height/2 - margin, paint);

        canvas.drawText(rightNum + "", rightEdge, height / 2 + 3 * margin, paint);
        canvas.drawLine(rightEdge, height/2 + margin, rightEdge, height/2 - margin, paint);

        canvas.drawText(topNum + "", width/2, topEdge - margin, paint);
        canvas.drawLine(width/2 - margin, topEdge, width/2 + margin, topEdge, paint);

        canvas.drawText(bottomNum + "", width/2, bottomEdge + 2 * margin, paint);
        canvas.drawLine(width/2 - margin, bottomEdge, width/2 + margin, bottomEdge, paint);
    }

    public void setVisibilities(boolean[] setVisibilities){
        visibilities = setVisibilities;
    }

    public void setEquations(ArrayList<Equation> setEquations){
        equations = setEquations;
    }

    public void setNums(int xNum, int yNum){
        leftNum = -xNum;
        rightNum = xNum;
        bottomNum = -yNum;
        topNum = yNum;
    }
}
