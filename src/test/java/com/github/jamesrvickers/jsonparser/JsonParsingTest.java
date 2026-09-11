package com.github.jamesrvickers.jsonparser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.github.jamesrvickers.jsonparser.Json;
import com.github.jamesrvickers.jsonparser.JsonObject;
import com.github.jamesrvickers.jsonparser.JsonParseException;

public class JsonParsingTest {
    public static void main(String[] args) {
        List<Method> tests = new ArrayList<>();
        Map<String, Boolean> passed = new HashMap<>();
        for (Method m : JsonParsingTest.class.getDeclaredMethods()) {
            if (m.getParameterCount() == 0 && m.getName().startsWith("test_")) {
                tests.add(m);
            }
        }
        for (Method m : tests) {
            try {
                boolean pass = (boolean)m.invoke(null);
                passed.put(m.getName(), pass);
            }
            catch (IllegalAccessException | InvocationTargetException e) { }
        }
        System.out.println(passed);
    }


    private static boolean test_01() {
        String input = "{}";
        JsonObject expected = Json.object();
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }

    private static boolean test_02() {
        String input = 
            """
            {
                "id": 101,
                "title": "Config",
                "enabled": true
            }
            """;
        JsonObject expected = Json.object()
            .put("id", 101)
            .put("title", "Config")
            .put("enabled", Json.TRUE);
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }

    private static boolean test_03() {
        String input = 
            """
            {
                "meta": {
                    "version": "1.0.0",
                    "secure": false
                }
            }
            """;
        JsonObject expected = Json.object()
            .put("meta", Json.object()
            .put("version", "1.0.0")
            .put("secure", Json.FALSE));
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }

    private static boolean test_04() {
        String input = "{\"items\":[1,2,3],\"tags\":[\"prod\",\"web\"]}";
        JsonObject expected = Json.object()
            .put("items", Json.array().add(1).add(2).add(3))
            .put("tags", Json.array().add("prod").add("web"));
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }

    private static boolean test_05() {
        String input = "{\"value\":null}";
        JsonObject expected = Json.object().put("value", Json.NULL);
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }

    private static boolean test_06() {
        String input = "{\"pi\":3.14159,\"negative\":-42,\"exp\":1e-5}";
        JsonObject expected = Json.object()
            .put("pi", 3.14159)
            .put("negative", -42)
            .put("exp", 1e-5);
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }

    private static boolean test_07() {
        String input = "{\n\t\"whitespace\":   \"trimmed\"\n}";
        JsonObject expected = Json.object().put("whitespace", "trimmed");
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }

    private static boolean test_08() {
        String input = "{\"emptyString\":\"\",\"emptyArray\":[],\"emptyObject\":{}}";
        JsonObject expected = Json.object()
            .put("emptyString", "")
            .put("emptyArray", Json.array())
            .put("emptyObject", Json.object());
        JsonObject actual = Json.parse(input);
        return expected.equals(actual);
    }
}
