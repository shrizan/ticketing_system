package com.lambton.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppUtil {
    public static final int DEFAULT_TOTAL_STRING_SIZE = 20;

    public static String formatString(String str) {
        int strLength = str.length();
        if (strLength > DEFAULT_TOTAL_STRING_SIZE) return str;
        else {
            int remainLength = DEFAULT_TOTAL_STRING_SIZE - strLength;
            return String.format("%s%s", str, " ".repeat(remainLength));
        }
    }

    public static String formatString(String str, int stringSize) {
        int strLength = str.length();
        if (strLength > stringSize) return str;
        else {
            int remainLength = stringSize - strLength;
            return String.format("%s%s", str, " ".repeat(remainLength));
        }
    }

    public static String formatString(int stringSize, String... str) {
        return Arrays.stream(str).map(s -> formatString(s, stringSize)).collect(Collectors.joining());
    }

    public static String formatString(String... str) {
        return Arrays.stream(str).map(AppUtil::formatString).collect(Collectors.joining());
    }
}
