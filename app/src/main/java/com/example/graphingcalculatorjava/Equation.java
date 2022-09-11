package com.example.graphingcalculatorjava;

import android.os.Parcel;
import android.os.Parcelable;

public class Equation implements Parcelable {
    private String equation;
    private double[] coefficients;
    private int degrees;
    private int graphColor;

    public Equation(String setEquation, double[] setCoefficients, int setDegree, int setColor){
        coefficients = setCoefficients;
        degrees = setDegree;
        graphColor = setColor;
        equation = createEquationToIntent();
    }

    protected Equation(Parcel in){
        equation = in.readString();
        coefficients = in.createDoubleArray();
        degrees = in.readInt();
        graphColor = in.readInt();
    }

    public static final Creator<Equation> CREATOR = new Creator<Equation>() {
        @Override
        public Equation createFromParcel(Parcel in) {
            return new Equation(in);
        }

        @Override
        public Equation[] newArray(int size) {
            return new Equation[size];
        }
    };

    private String createEquationToIntent(){
        int numTerms = 0;
        String str = "y = ";
        boolean firstTerm = true;

        for (int i = 2; i <= degrees; i++){
            if (coefficients[degrees - i + 1] != 0.0){
                if (!firstTerm){
                    str += " + ";
                }
                else{
                    firstTerm = false;
                }
                str += round(coefficients[i - 2]) + "x^" + (degrees - i + 2);
                numTerms++;
            }
        }
        if (degrees >= 1){
            if(coefficients[degrees - 1] != 0.0 && coefficients[degrees - 1] != 0.0){
                if(degrees != 1){
                    str += " + ";
                }
                str += round(coefficients[degrees - 1]) + "x";
                numTerms++;
            }
        }
        if (numTerms == 0 || coefficients[degrees] != 0.0){
            if (numTerms != 0){
                str += " + ";
            }
            str += round(coefficients[degrees]);
        }
        return str;
    }
    private double round(double a){
        return Math.round(a * 100) * 1.0/100;
    }

    public int getDegree(){
        return degrees;
    }

    public double[] getCoefficients(){
        return coefficients;
    }

    public String getEquation(){
        return equation;
    }

    public int getColor(){
        return graphColor;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(equation);
        parcel.writeDoubleArray(coefficients);
        parcel.writeInt(degrees);
        parcel.writeInt(graphColor);
    }
}
