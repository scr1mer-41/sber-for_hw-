package com.serializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class JsonSerializer {
    public String serialize(Object object) throws JsonSerializationException {
        try {
            return serializeObject(object);
        } catch (IllegalAccessException e) {
            throw new JsonSerializationException("Error during serialization", e);
        }
    }

    private String serializeObject(Object object) throws IllegalAccessException, JsonSerializationException {
        if (object == null) {
            return "null";
        }

        Class<?> clazz = object.getClass();

        // Примитивы и строки
        if (isPrimitiveOrWrapper(clazz) || clazz == String.class) {
            return serializePrimitive(object);
        }

        // Коллекции
        if (Collection.class.isAssignableFrom(clazz)) {
            return serializeCollection((Collection<?>) object);
        }

        // Мапы
        if (Map.class.isAssignableFrom(clazz)) {
            return serializeMap((Map<?, ?>) object);
        }

        // Пользовательские объекты
        return serializeCustomObject(object);
    }

    private String serializePrimitive(Object value) {
        if (value instanceof String) {
            return "\"" + escapeString((String) value) + "\"";
        }
        return value.toString();
    }

    private String escapeString(String str) {
        return str.replace("\"", "\\\"")
                .replace("\\", "\\\\")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String serializeCollection(Collection<?> collection) throws IllegalAccessException, JsonSerializationException {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;

        for (Object item : collection) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(serializeObject(item));
            first = false;
        }

        sb.append("]");
        return sb.toString();
    }

    private String serializeMap(Map<?, ?> map) throws IllegalAccessException, JsonSerializationException {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append("\"").append(entry.getKey().toString()).append("\": ");
            sb.append(serializeObject(entry.getValue()));
            first = false;
        }

        sb.append("}");
        return sb.toString();
    }

    private String serializeCustomObject(Object object) throws IllegalAccessException, JsonSerializationException {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonField.class)) {
                field.setAccessible(true);
                Object value = field.get(object);

                if (value != null) {
                    if (!first) {
                        sb.append(", ");
                    }

                    JsonField annotation = field.getAnnotation(JsonField.class);
                    String fieldName = annotation.name().isEmpty() ? field.getName() : annotation.name();
                    sb.append("\"").append(fieldName).append("\": ");
                    sb.append(serializeObject(value));

                    first = false;
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Double.class ||
                clazz == Float.class ||
                clazz == Boolean.class ||
                clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Character.class;
    }
}