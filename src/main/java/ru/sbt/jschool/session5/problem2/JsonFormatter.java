package ru.sbt.jschool.session5.problem2;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonFormatter {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final Set<Class<?>> PRIMITIVE_WRAPPERS = getPrimitiveWrappers();

    private static Set<Class<?>> getPrimitiveWrappers() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(Boolean.class);
        set.add(Character.class);
        set.add(Byte.class);
        set.add(Short.class);
        set.add(Integer.class);
        set.add(Long.class);
        set.add(Float.class);
        set.add(Double.class);
        set.add(Void.class);
        return set;
    }

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return PRIMITIVE_WRAPPERS.contains(clazz);
    }

    private static ArrayList<Field> getFields(Object obj) {
        Class<?> clazz = obj.getClass();
        ArrayList<Field> fields = new ArrayList<>();
        while (!clazz.equals(Object.class)) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private String fieldToString(String primitiveFormat, String stringFormat, Object obj) {
        return fieldToString(primitiveFormat, stringFormat, obj, null);
    }

    private String fieldToString(String primitiveFormat, String stringFormat, Object obj, String fieldName) {
        String name = "";
        if (fieldName != null) {
            name = String.format("\"%s\":", fieldName);
        }
        if (obj == null) {
            return name + String.format(primitiveFormat, null);
        }
        Class<?> clazz = obj.getClass();
        if (typeExtensions.containsKey(clazz)) {
            return name + String.format(stringFormat, typeExtensions.get(clazz).format(obj));
        } else if (clazz.isPrimitive() || isPrimitiveWrapper(clazz)) {
            if (clazz.equals(Character.class)) {
                return name + String.format(stringFormat, obj.toString());
            }
            return name + String.format(primitiveFormat, obj.toString());
        } else if (clazz.equals(String.class)) {
            return name + String.format(stringFormat, obj);
        } else if (Date.class.isAssignableFrom(clazz)) {
            return name + String.format(stringFormat, DATE_FORMAT.format(obj));
        } else if (Calendar.class.isAssignableFrom(clazz)) {
            return name + String.format(stringFormat, DATE_FORMAT.format(((Calendar) obj).getTime()));
        } else {
            return name + String.format(primitiveFormat, format(obj));
        }
    }

    private HashMap<Class<?>, Formatting> typeExtensions = new HashMap<>();

    public <T> void addTypeExtension(Class<T> clazz, Formatting formatting) {
        typeExtensions.put(clazz, formatting);
    }

    public String format(Object object) {
        if (object == null) {
            //throw new RuntimeException("Argument for parameter 'object' of Json.marshal must not be null");
            return null;
        }
        ArrayList<String> stringedFields = new ArrayList<>();

        for (Field field : getFields(object)) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Annotation ignoreAnnotation = field.getAnnotation(JsonIgnore.class);
            Annotation setterAnnotation = field.getAnnotation(JsonProperty.class);
            if (ignoreAnnotation != null) {
                continue;
            }

            String fieldName;
            if (setterAnnotation != null) {
                fieldName = ((JsonProperty) setterAnnotation).name();
            } else {
                fieldName = field.getName();
            }

            try {
                if (List.class.isAssignableFrom(field.getType()) || Set.class.isAssignableFrom(field.getType())) {
                    Collection<?> collection = (Collection<?>) field.get(object);
                    ArrayList<String> listElements = new ArrayList<>();
                    for (Object obj : collection) {
                        listElements.add(fieldToString("%s", "\"%s\"", obj));
                    }
                    stringedFields.add(String.format("\"%s\":[%s]", fieldName, listElements.stream().collect(Collectors.joining(","))));
                } else {
                    stringedFields.add(fieldToString("%s", "\"%s\"", field.get(object), fieldName));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access to field: " + field.getName() + " of class: " + field.getType().getName(), e);
            }
        }
        return String.format("{%s}", stringedFields.stream().collect(Collectors.joining(",")));
    }


}
