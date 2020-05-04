package com.criteria.simple.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

public class TypeSupporting {
    private static final DateFormat slashFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateFormat dotFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat dashFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final DateFormat fullSlashFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final DateFormat fullDotFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final DateFormat fullDashFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static <T> T evaluateTargetValue(Class<T> targetClass, Object value) {
        try {
            if (value instanceof Iterable) {
                throw new UnsupportedOperationException("Can't evaluate Iterable classes");
            }
            if (targetClass.isAssignableFrom(value.getClass()))
                return (T) value;
            String stringValue = value.toString();
            switch (targetClass.getTypeName()) {
                case "java.lang.String":
                    return (T) stringValue;
                case "long":
                case "java.lang.Long":
                    return (T) Long.valueOf(stringValue);
                case "int":
                case "java.lang.Integer":
                    return (T) Integer.valueOf(stringValue);
                case "short":
                case "java.lang.Short":
                    return (T) Short.valueOf(stringValue);
                case "double":
                case "java.lang.Double":
                    return (T) Double.valueOf(stringValue);
                case "float":
                case "java.lang.Float":
                    return (T) Float.valueOf(stringValue);
                case "java.math.BigDecimal":
                    return (T) new BigDecimal(stringValue);
                case "java.util.Date":
                    return (T) tryToParseDate(stringValue);
                case "java.time.OffsetDateTime":
                    return (T) OffsetDateTime.parse(stringValue);
                default: {
                    throw new UnsupportedOperationException("Can't parse to class " + targetClass.getTypeName());
                }
            }
        } catch (ParseException | RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Date tryToParseDate(String date) throws ParseException {
        try {
            if (date.contains(".")) {
                if (date.length() > 10) {
                    return fullDotFormat.parse(date);
                } else {
                    return dotFormat.parse(date);
                }
            }
            if (date.contains("-")) {
                if (date.length() > 10) {
                    return fullDashFormat.parse(date);
                } else {
                    return dashFormat.parse(date);
                }
            }
            if (date.contains("/")) {
                if (date.length() > 10) {
                    return fullSlashFormat.parse(date);
                } else {
                    return slashFormat.parse(date);
                }
            }
            throw new UnsupportedOperationException("Cannot parse date from " + date);
        } catch (ParseException | RuntimeException ex) {
            throw new ParseException("Cannot parse " + date + " to date format", 0);
        }
    }
}
