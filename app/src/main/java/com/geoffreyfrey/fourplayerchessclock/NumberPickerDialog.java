package com.geoffreyfrey.fourplayerchessclock;

import android.app.AlertDialog;
import android.app.Dialog;
//import android.content.Context;
import android.content.DialogInterface;
//import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
import android.widget.NumberPicker;
//import android.widget.TextView;


public class NumberPickerDialog extends DialogFragment {

    //Setting Listener
    private NumberPicker.OnValueChangeListener valueChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        //numberPicker.setMinValue(1);
        //numberPicker.setMaxValue(60);
        String[] range = new String[60];
        for (int i = 0;i<range.length;i++){
            range[i]=String.valueOf(i*MainActivity.numberPickerFactor);
        }
        numberPicker.setDisplayedValues(null);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(range.length);
        numberPicker.setDisplayedValues(range);

        numberPicker.setWrapSelectorWheel(false);
        //numberPicker.setFormatter(formatter);
        //numberPicker.setValue(numberPicker.getMinValue()*MainActivity.numberPickerFactor);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Value");
        builder.setMessage("Choose a number :");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setView(numberPicker);
        return builder.create();
    }
    /*
    NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            int temp = value * MainActivity.numberPickerFactor;
            return "" + temp;
        }
    };
    */

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}
