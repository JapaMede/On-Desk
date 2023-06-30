package com.example.ondesk.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataUtils {

    public String formatData(long l){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(calendar.getTime());

        return dataFormatada;
    }
}
