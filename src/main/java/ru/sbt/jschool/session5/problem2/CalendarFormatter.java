package ru.sbt.jschool.session5.problem2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CalendarFormatter implements Formatting<Calendar> {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public String format(Calendar calendar, JsonFormatter jsonFormatter, HashMap<String, Object> ctx) {
        return String.format("\"%s\"", DATE_FORMAT.format(calendar.getTime()));
    }
}
