package ru.sbt.jschool.session5.problem2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JsonTest {
    @Test
    public void testMarshal() {
        assertEquals("{\"barCalendar\":\"05.04.2018\"," +
                "\"barDate\":\"05.04.2018\"," +
                "\"barDouble\":0.1," +
                "\"barBoolean\":true," +
                "\"barInteger\":1," +
                "\"barLong\":100000000," +
                "\"barFloat\":0.001," +
                "\"barShort\":15," +
                "\"barCharacter\":\"a\"," +
                "\"barByte\":117," +
                "\"barMyType\":42," +
                "\"barString\":\"barString\"," +
                "\"barChangedName\":666," +
                "\"bar_int\":1," +
                "\"bar_double\":0.17," +
                "\"bar_float\":1.29," +
                "\"bar_short\":128," +
                "\"bar_byte\":117," +
                "\"bar_long\":100000," +
                "\"bar_boolean\":false," +
                "\"barIntegerNull\":null," +
                "\"barStringNull\":null," +
                "\"barChild\":{" +
                    "\"childString\":null," +
                    "\"childFoo\":{" +
                        "\"fooString\":null" +
                    "}," +
                    "\"baseString\":\"Base\"," +
                    "\"protectedString\":\"BaseBase\"," +
                    "\"privateString\":\"BaseBase\"" +
                "}," +
                "\"barIntegerSet\":[1,2,3]," +
                "\"barChildList\":[" +
                    "{\"childString\":null," +
                        "\"childFoo\":{" +
                            "\"fooString\":null" +
                        "}," +
                        "\"baseString\":\"Base\"," +
                        "\"protectedString\":\"BaseBase\"," +
                        "\"privateString\":\"BaseBase\"" +
                    "}," +
                    "{\"childString\":null," +
                        "\"childFoo\":{" +
                            "\"fooString\":null" +
                        "}," +
                        "\"baseString\":\"Base\"," +
                        "\"protectedString\":\"BaseBase\"," +
                        "\"privateString\":\"BaseBase\"" +
                    "}" +
                "]," +
                "\"protectedString\":\"BaseBase\"," +
                "\"privateString\":\"BaseBase\"}", Json.marshal(new Bar()));
    }

    @Test
    public void testNullObjectMarshall() {
        boolean isExepted = false;
        try {
            Json.marshal(null);
        } catch (RuntimeException e) {
            isExepted = true;
        }
        assertEquals(true, isExepted );
    }
}
