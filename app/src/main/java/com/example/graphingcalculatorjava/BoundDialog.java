package com.example.graphingcalculatorjava;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.NumberFormat;

public class BoundDialog extends AppCompatDialogFragment {

    BoundDialogListener boundDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.layout_bound_dialog, null);
        builder.setView(view);
        builder.setTitle("Set Bounds");

        final TextView xBoundsTextView = view.findViewById(R.id.xBoundsTextEdit);
        final TextView yBoundsTextView = view.findViewById(R.id.yBoundsTextEdit);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    int xBounds = Integer.parseInt(xBoundsTextView.getText().toString());
                    if (xBounds >= 999999){
                        xBounds = 999999;
                    }
                    if (xBounds == 0){
                        xBounds = 1;
                    }
                    boundDialogListener.applyXBound(xBounds);
                }
                catch (NumberFormatException e){
                    if (xBoundsTextView.getText().toString().length() != 0){
                        boundDialogListener.applyXBound(999999);
                    }
                }

                try{
                    int yBounds = Integer.parseInt(yBoundsTextView.getText().toString());
                    if (yBounds >= 999999){
                        yBounds = 999999;
                    }
                    if (yBounds == 0){
                        yBounds = 1;
                    }
                    boundDialogListener.applyYBound(yBounds);
                }
                catch (NumberFormatException e){
                    if (yBoundsTextView.getText().toString().length() != 0){
                        boundDialogListener.applyYBound(999999);
                    }
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            boundDialogListener = (BoundDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement BoundDialogListener!");
        }
    }

    public interface BoundDialogListener{
        void applyXBound(int xBound);
        void applyYBound(int yBound);
    }

}
