package com.example.graphingcalculatorjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BoundDialog.BoundDialogListener{

    private ArrayList<Equation> equations = new ArrayList<>();
    private ArrayList<Boolean> visibilities = new ArrayList<>();
    private ArrayList<Intent> intents = new ArrayList<>();

    private EquationItemAdapter equationAdapter;

    private int xDomain = 10;
    private int yDomain = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addEquationBtn = findViewById(R.id.addEquationBtn);
        addEquationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equations.size() >= 8){
                    Toast.makeText(getApplicationContext(), "Maximum Equation Limit Reached", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), EquationActivity.class);
                    intents.add(intent);
                    startActivityForResult(intent, 1);
                }
            }
        });

        Button generateGraphsBtn = findViewById(R.id.generateGraphsBtn);
        generateGraphsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equations.size() == 0){
                    Toast.makeText(getApplicationContext(), "Please add an equation", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                    visibilities = equationAdapter.getVisibilities();
                    intent.putExtra("visibilities", booleanListToArray());
                    intent.putParcelableArrayListExtra("equations", equations);
                    intent.putExtra("xNum", xDomain);
                    intent.putExtra("yNum", yDomain);
                    startActivity(intent);
                }
            }
        });

        Button setBoundsBtn = findViewById(R.id.setBoundsBtn);
        setBoundsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        ListView equationListView = findViewById(R.id.equationListView);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                Equation newEquation = new Equation(data.getStringExtra("equation"), data.getDoubleArrayExtra("coefficients"),
                        data.getIntExtra("degree", 1), data.getIntExtra("graphColor", Color.BLACK));
                equations.add(newEquation);
                equationAdapter = new EquationItemAdapter(this, R.layout.equations_listview_details, equations);
                equationListView.setAdapter(equationAdapter);
            }
        }
    }

    private void openDialog(){
        BoundDialog boundDialog = new BoundDialog();
        boundDialog.show(getSupportFragmentManager(), "Bound Dialog");
    }

    private boolean[] booleanListToArray(){
        boolean[] toReturn = new boolean[visibilities.size()];
        for (int i = 0; i < toReturn.length; i++){
            toReturn[i] = visibilities.get(i);
        }
        return toReturn;
    }

    @Override
    public void applyXBound(int xBound){
        final TextView xDomainTextView = findViewById(R.id.xDomainTextView);
        xDomain = xBound;
        String xText = "x: [" + -xBound + ", " + xBound + "]";
        xDomainTextView.setText(xText);
    }

    @Override
    public void applyYBound(int yBound){
        final TextView yDomainTextView = findViewById(R.id.yDomainTextView);
        xDomain = yBound;
        String xText = "x: [" + -yBound + ", " + yBound + "]";
        yDomainTextView.setText(xText);
    }

}