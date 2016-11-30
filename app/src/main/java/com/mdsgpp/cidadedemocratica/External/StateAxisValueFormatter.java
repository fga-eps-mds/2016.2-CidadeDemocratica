package com.mdsgpp.cidadedemocratica.External;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;


/**
 * Created by luisresende on 29/11/16.
 */

public class StateAxisValueFormatter implements IAxisValueFormatter {

    private String [] orderStates;

    public StateAxisValueFormatter (String[] orderStates){
        this.orderStates = orderStates;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return orderStates[(int) value];
    }
}
