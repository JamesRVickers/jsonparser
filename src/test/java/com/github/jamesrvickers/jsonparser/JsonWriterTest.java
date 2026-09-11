package com.github.jamesrvickers.jsonparser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.github.jamesrvickers.jsonparser.Json;
import com.github.jamesrvickers.jsonparser.JsonObject;

public class JsonWriterTest {

    public static void main(String[] args) {
        List<Method> tests = new ArrayList<>();
        Map<String, Boolean> passed = new HashMap<>();
        for (Method m : JsonWriterTest.class.getDeclaredMethods()) if (m.getParameterCount() == 0 && m.getName().startsWith("test_")) tests.add(m);
        for (Method m : tests) {
            try {
                boolean pass = (boolean) m.invoke(null);
                passed.put(m.getName(), pass);
            } catch (IllegalAccessException | InvocationTargetException e) { }
        }
        System.out.println("writing:"+passed);
    }

    private static boolean test_01() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object();
            Files.writeString(tempFile, obj.toString());
            String actual = Files.readString(tempFile);
            return "{}".equals(actual.trim());
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean test_02() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object()
                .put("id", 101)
                .put("title", "Config")
                .put("enabled", Json.TRUE);
            Files.writeString(tempFile, obj.toString());
            JsonObject parsedBack = Json.parse(Files.readString(tempFile));
            return obj.equals(parsedBack);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean test_03() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object()
                .put("meta", Json.object()
                .put("version", "1.0.0")
                .put("secure", Json.FALSE));
            Files.writeString(tempFile, obj.toString());
            JsonObject parsedBack = Json.parse(Files.readString(tempFile));
            return obj.equals(parsedBack);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean test_04() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object()
                .put("items", Json.array().add(1).add(2).add(3))
                .put("tags", Json.array().add("prod").add("web"));
            Files.writeString(tempFile, obj.toString());
            JsonObject parsedBack = Json.parse(Files.readString(tempFile));
            return obj.equals(parsedBack);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean test_05() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object().put("value", Json.NULL);
            Files.writeString(tempFile, obj.toString());
            JsonObject parsedBack = Json.parse(Files.readString(tempFile));
            return obj.equals(parsedBack);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean test_06() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object()
                .put("pi", 3.14159)
                .put("negative", -42)
                .put("exp", 1e-5);
            Files.writeString(tempFile, obj.toString());
            JsonObject parsedBack = Json.parse(Files.readString(tempFile));
            return obj.equals(parsedBack);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean test_07() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object().put("whitespace", "trimmed");
            Files.writeString(tempFile, obj.toString());
            JsonObject parsedBack = Json.parse(Files.readString(tempFile));
            return obj.equals(parsedBack);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean test_08() {
        try {
            Path tempFile = Files.createTempFile("json_test_", ".json");
            tempFile.toFile().deleteOnExit();
            JsonObject obj = Json.object()
                .put("emptyString", "")
                .put("emptyArray", Json.array())
                .put("emptyObject", Json.object());
            Files.writeString(tempFile, obj.toString());
            JsonObject parsedBack = Json.parse(Files.readString(tempFile));
            return obj.equals(parsedBack);
        } catch (IOException e) {
            return false;
        }
    }
}
