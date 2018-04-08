package ru.sbt.jschool.session5.problem2;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class JsonTest {
    @Test
    public void testMarshal() {
        JsonFormatter jsonFormatter = new JsonFormatter();
        jsonFormatter.addTypeExtension(BigDecimal.class, (bigDecimal, formatter, ctx) -> {return bigDecimal.pow(2).toString();});
        Scanner sc = new Scanner(JsonTest.class.getResourceAsStream("/JsonExample"));
        String result = jsonFormatter.marshall(new Bar());
        InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        Scanner scr = new Scanner(stream);
        while (sc.hasNextLine()) {
             assertEquals(sc.nextLine(), scr.nextLine());
        }
    }

    @Test
    public void testNullObjectMarshall() {

        assertEquals(null, new JsonFormatter().marshall(null) );
    }
}
