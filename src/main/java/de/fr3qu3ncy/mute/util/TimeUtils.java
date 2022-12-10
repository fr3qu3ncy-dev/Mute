package de.fr3qu3ncy.mute.util;

import java.time.Duration;

public class TimeUtils {

    private TimeUtils() {}

    public static String prettyFormatTime(long millis) {
        Duration duration = Duration.ofMillis(millis);
        StringBuilder sb = new StringBuilder();

        int months = 0;
        int weeks = 0;
        long days = duration.toDaysPart();
        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();
        int seconds = duration.toSecondsPart();

        while (days >= 30) {
            months++;
            days -= 30;
        }

        while (days >= 7) {
            weeks++;
            days -= 7;
        }

        if (months > 0) sb.append(months).append(" Month(s) ");
        if (weeks > 0) sb.append(weeks).append(" Week(s) ");
        if (days > 0) sb.append(days).append(" Day(s) ");
        if (hours > 0) sb.append(hours).append(" Hour(s) ");
        if (minutes > 0) sb.append(minutes).append(" Minute(s) ");
        if (seconds > 0) sb.append(seconds).append(" Second(s) ");

        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Convert a given time string to milliseconds.<br>
     * Format: "5m30s"
     *
     * @param timeString The string to convert.
     * @return The time in milliseconds.
     */
    public static long parseDuration(String timeString) {
        long millis = 0;
        millis += findConstraint(timeString, "s") * 1000L;
        millis += findConstraint(timeString, "m") * 1000L * 60;
        millis += findConstraint(timeString, "h") * 1000L * 60 * 60;
        millis += findConstraint(timeString, "w") * 1000L * 60 * 60 * 24 * 7;
        millis += findConstraint(timeString, "mo") * 1000L * 60 * 60 * 24 * 30;

        return millis;
    }

    private static int findConstraint(String timeString, String identifier) {
        if (!timeString.contains(identifier)) return 0;

        int endIndex = timeString.indexOf(identifier);
        int startIndex = findStartIndex(timeString, endIndex);

        if (startIndex == -1) return 0;

        try {
            return Integer.parseInt(timeString.substring(startIndex, endIndex));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private static int findStartIndex(String timeString, int endIndex) {
        for (int index = endIndex - 1 ; index >= 0 ; index--) {
            if (!Character.isDigit(timeString.charAt(index))) {
                return index + 1;
            } else if (index == 0) {
                return 0;
            }
        }
        return -1;
    }
}
