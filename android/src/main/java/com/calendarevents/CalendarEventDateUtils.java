package com.calendarevents;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

final class CalendarEventDateUtils {
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private CalendarEventDateUtils() {}

    // Preserve the device-local day at the UTC boundary required for all-day events
    static long toUtcMidnight(long millis) {
        Calendar localDate = Calendar.getInstance();
        localDate.setTimeInMillis(millis);

        Calendar utcDate = Calendar.getInstance(UTC);
        utcDate.clear();
        utcDate.set(
            localDate.get(Calendar.YEAR),
            localDate.get(Calendar.MONTH),
            localDate.get(Calendar.DAY_OF_MONTH),
            0,
            0,
            0
        );
        return utcDate.getTimeInMillis();
    }

    //android editors display an inclusive end date for all-day events
    static long toEditorUtcMidnightEnd(long startUtcMidnight, long endMillis) {
        long endUtcMidnight = toUtcMidnight(endMillis);
        long editorEndUtcMidnight = endUtcMidnight - TimeUnit.DAYS.toMillis(1);
        return Math.max(startUtcMidnight, editorEndUtcMidnight);
    }
}
