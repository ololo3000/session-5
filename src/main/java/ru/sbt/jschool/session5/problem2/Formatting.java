package ru.sbt.jschool.session5.problem2;

import java.util.HashMap;

@FunctionalInterface
public interface Formatting<T> {
    String format(T object, JsonFormatter jsonFormatter, HashMap<String, Object> ctx);


}
