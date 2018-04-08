package ru.sbt.jschool.session5.problem2;

import java.util.HashMap;

public class PrimitiveTypeFormatter implements Formatting<Object> {

    @Override
    public String format(Object object, JsonFormatter jsonFormatter, HashMap<String, Object> ctx) {
        return object.toString();
    }
}
