package ru.sbt.jschool.session5.problem2;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bar extends BaseBase{
    public Bar() {
        barChildList.add(new Child());
        barChildList.add(new Child());
        barIntegerSet.add(1);
        barIntegerSet.add(2);
        barIntegerSet.add(3);
    }

    transient Integer barTransientInt = 666;
    Calendar barCalendar = Calendar.getInstance();
    Date barDate = barCalendar.getTime();
    public Double barDouble = 0.1d;
    private Boolean barBoolean = true;
    private Integer barInteger = 1;
    private Long barLong = 100000000L;
    private Float barFloat = 0.001F;
    private Short barShort = 15;
    private Character barCharacter = 'a';
    private Byte barByte = 117;
    private MyType barMyType = new MyType();
    private String barString = "barString";

    @JsonIgnore
    private int barIntIgnore = 666;
    @JsonProperty(name = "barChangedName")
    private int barIntNotValidName = 666;

    private int bar_int = 1;
    private double bar_double = 0.17d;
    private float bar_float = 1.29f;
    private short bar_short = 128;
    private byte bar_byte = 117;
    private long bar_long = 100000l;
    private boolean bar_boolean = false;

    private Integer barIntegerNull = null;
    private String barStringNull = null;


    private Child barChild = new Child();

    private Set<Integer> barIntegerSet = new HashSet<>();
    private List<Child> barChildList = new ArrayList<>();





}
