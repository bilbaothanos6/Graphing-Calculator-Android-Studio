package com.example.graphingcalculatorjava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class GraphActivity extends AppCompatActivity {

    private MyCanvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        canvas = new MyCanvas(this);
        canvas.setBackgroundColor(Color.WHITE);
        canvas.setEquations(getIntent().<Equation>getParcelableArrayListExtra("equations"));
        canvas.setVisibilities(getIntent().getBooleanArrayExtra("visibilities"));
        canvas.setNums(getIntent().getIntExtra("xNum", 10), getIntent().getIntExtra("yNum", 10));
        setContentView(canvas);
    }
}