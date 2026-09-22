package com.calendarevents;

import java.time.Instant;
import java.util.TimeZone;

public final class CalendarEventDateUtilsTest {
    public static void main(String[] args) {
        TimeZone originalTimeZone = TimeZone.getDefault();
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Riyadh"));
            assertEquals(
                "2026-08-28T00:00:00Z",
                CalendarEventDateUtils.toUtcMidnight(
                    Instant.parse("2026-08-27T21:00:00Z").toEpochMilli()
                )
            );
            long startUtcMidnight = CalendarEventDateUtils.toUtcMidnight(
                Instant.parse("2026-09-22T21:00:00Z").toEpochMilli()
            );
            assertEquals(
                "2026-09-23T00:00:00Z",
                CalendarEventDateUtils.toEditorUtcMidnightEnd(
                    startUtcMidnight,
                    Instant.parse("2026-09-23T21:00:00Z").toEpochMilli()
                )
            );
            assertEquals(
                "2026-09-25T00:00:00Z",
                CalendarEventDateUtils.toEditorUtcMidnightEnd(
                    startUtcMidnight,
                    Instant.parse("2026-09-25T21:00:00Z").toEpochMilli()
                )
            );

            TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
            assertEquals(
                "2026-08-28T00:00:00Z",
                CalendarEventDateUtils.toUtcMidnight(
                    Instant.parse("2026-08-28T04:00:00Z").toEpochMilli()
                )
            );
        } finally {
            TimeZone.setDefault(originalTimeZone);
        }
    }

    private static void assertEquals(String expected, long actualMillis) {
        String actual = Instant.ofEpochMilli(actualMillis).toString();
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but received " + actual);
        }
    }
}
