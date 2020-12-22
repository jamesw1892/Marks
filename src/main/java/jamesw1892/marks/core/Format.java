package jamesw1892.marks.core;

import java.text.DecimalFormat;

public class Format {
    private static final DecimalFormat integerPercentage = new DecimalFormat("0%");

    public static String percentageNull(Float value) {
        if (value == null) {
            return "Unknown";
        }
        return integerPercentage.format(value);
    }

    public static String percentageNotNull(float value) {
        return integerPercentage.format(value);
    }
}
