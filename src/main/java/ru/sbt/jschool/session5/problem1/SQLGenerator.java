package ru.sbt.jschool.session5.problem1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 */
public class SQLGenerator {

    public String repeatNTimes(String s, int n, String separator) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n - 1; i++) {
            builder.append(s + separator);
        }
        builder.append(s);
        return builder.toString();
    }

    public <T> String insert(Class<T> clazz) {
        ArrayList<String> columns = new ArrayList<>();
        String tableName = clazz.getAnnotation(Table.class).name().toUpperCase();

        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            PrimaryKey primaryKeyAnnotation = field.getAnnotation(PrimaryKey.class);

            if (primaryKeyAnnotation != null) {
                if (!primaryKeyAnnotation.name().equals("")) {
                    columns.add(primaryKeyAnnotation.name().toLowerCase());
                    continue;
                }
                columns.add(field.getName().toLowerCase());
                continue;
            }
            if (columnAnnotation != null) {
                if (!columnAnnotation.name().equals("")) {
                    columns.add(columnAnnotation.name().toLowerCase());
                    continue;
                }
                columns.add(field.getName().toLowerCase());
            }
        }
        String values = repeatNTimes("?" , columns.size(), ", ");

        return String.format("INSERT INTO %s(%s) VALUES (%s)",
                tableName,
                columns.stream().collect(Collectors.joining(", ")),
                values);
    }

    public <T> String update(Class<T> clazz) {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> primaryKeys = new ArrayList<>();
        String tableName = clazz.getAnnotation(Table.class).name().toUpperCase();

        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            PrimaryKey primaryKeyAnnotation = field.getAnnotation(PrimaryKey.class);

            if (primaryKeyAnnotation != null) {
                if (!primaryKeyAnnotation.name().equals("")) {
                    primaryKeys.add(primaryKeyAnnotation.name().toLowerCase() + " = ?");
                    continue;
                }
                primaryKeys.add(field.getName().toLowerCase() + " = ?");
                continue;
            }
            if (columnAnnotation != null) {
                if (!columnAnnotation.name().equals("")) {
                    columns.add(columnAnnotation.name().toLowerCase() + " = ?");
                    continue;
                }
                columns.add(field.getName().toLowerCase() + " = ?");
            }
        }

        return String.format("UPDATE %s SET %s WHERE %s",
                tableName,
                columns.stream().collect(Collectors.joining(", ")),
                primaryKeys.stream().collect(Collectors.joining(" AND ")));
    }

    public <T> String delete(Class<T> clazz) {
        ArrayList<String> primaryKeys = new ArrayList<>();
        String tableName = clazz.getAnnotation(Table.class).name().toUpperCase();

        for (Field field : clazz.getDeclaredFields()) {
            PrimaryKey primaryKeyAnnotation = field.getAnnotation(PrimaryKey.class);

            if (primaryKeyAnnotation != null) {
                if (!primaryKeyAnnotation.name().equals("")) {
                    primaryKeys.add(primaryKeyAnnotation.name().toLowerCase() + " = ?");
                    continue;
                }
                primaryKeys.add(field.getName().toLowerCase() + " = ?");
                continue;
            }
        }

        return String.format("DELETE FROM %s WHERE %s",
                tableName,
                primaryKeys.stream().collect(Collectors.joining(" AND ")));
    }

    public <T> String select(Class<T> clazz) {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> primaryKeys = new ArrayList<>();
        String tableName = clazz.getAnnotation(Table.class).name().toUpperCase();

        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            PrimaryKey primaryKeyAnnotation = field.getAnnotation(PrimaryKey.class);

            if (primaryKeyAnnotation != null) {
                if (!primaryKeyAnnotation.name().equals("")) {
                    primaryKeys.add(primaryKeyAnnotation.name().toLowerCase() + " = ?");
                    continue;
                }
                primaryKeys.add(field.getName().toLowerCase() + " = ?");
                continue;
            }
            if (columnAnnotation != null) {
                if (!columnAnnotation.name().equals("")) {
                    columns.add(columnAnnotation.name().toLowerCase());
                    continue;
                }
                columns.add(field.getName().toLowerCase() );
            }
        }

        return String.format("SELECT %s FROM %s WHERE %s",
                columns.stream().collect(Collectors.joining(", ")),
                tableName,
                primaryKeys.stream().collect(Collectors.joining(" AND ")));

    }
}
