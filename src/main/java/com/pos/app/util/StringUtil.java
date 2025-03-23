package com.pos.app.util;

import java.util.Objects;

public class StringUtil {

    public static String normalize(String input) {
        return Objects.nonNull(input) ? input.trim().toLowerCase() : null;
    }

    public static String trim(String input) {
        return Objects.nonNull(input) ? input.trim() : null;
    }

    public static String toLowerCase(String input) {
        return Objects.nonNull(input) ? input.toLowerCase() : null;
    }

    public static String toUpperCase(String input) {
        return Objects.nonNull(input) ? input.toUpperCase() : null;
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isNotBlank(String input) {
        return !isBlank(input);
    }
}
