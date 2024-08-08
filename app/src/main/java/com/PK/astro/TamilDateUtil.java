package com.PK.astro;
import java.util.Calendar;

public class TamilDateUtil {
    // Tamil months in Tamil script
    private static final String[] TAMIL_MONTHS = {
            "சித்திரை", "வைகாசி", "ஆனி", "ஆடி", "ஆவணி", "புரட்டாசி", "ஐப்பசி", "கார்த்திகை", "மார்கழி", "தை", "மாசி", "பங்குனி"
    };

    // Corresponding start dates for Tamil months in Gregorian calendar
    private static final int[][] MONTH_START_DATES = {
            {14, 4},  // சித்திரை starts on 14th April
            {15, 5},  // வைகாசி starts on 15th May
            {15, 6},  // ஆனி starts on 15th June
            {16, 7},  // ஆடி starts on 16th July
            {17, 8},  // ஆவணி starts on 17th August
            {17, 9},  // புரட்டாசி starts on 17th September
            {17, 10}, // ஐப்பசி starts on 17th October
            {16, 11}, // கார்த்திகை starts on 16th November
            {16, 12}, // மார்கழி starts on 16th December
            {14, 1},  // தை starts on 14th January
            {13, 2},  // மாசி starts on 13th February
            {14, 3}   // பங்குனி starts on 14th March
    };

    public static String getCurrentTamilDate() {
        Calendar calendar = Calendar.getInstance();

        // Decrement the date by 1 day
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;  // Months are indexed from 0
        int currentYear = calendar.get(Calendar.YEAR);

        String tamilMonth = "";
        int tamilDay = currentDay;

        for (int i = 0; i < MONTH_START_DATES.length; i++) {
            int startDay = MONTH_START_DATES[i][0];
            int startMonth = MONTH_START_DATES[i][1];
            int nextIndex = (i + 1) % MONTH_START_DATES.length;
            int nextStartDay = MONTH_START_DATES[nextIndex][0];
            int nextStartMonth = MONTH_START_DATES[nextIndex][1];

            if ((currentMonth == startMonth && currentDay >= startDay) ||
                    (currentMonth == nextStartMonth && currentDay < nextStartDay)) {
                tamilMonth = TAMIL_MONTHS[i];
                if (currentMonth == startMonth && currentDay >= startDay) {
                    tamilDay = currentDay - startDay + 1;
                } else if (currentMonth == nextStartMonth && currentDay < nextStartDay) {
                    Calendar prevMonthCal = Calendar.getInstance();
                    prevMonthCal.set(Calendar.DAY_OF_MONTH, startDay);
                    prevMonthCal.set(Calendar.MONTH, startMonth - 1);
                    prevMonthCal.set(Calendar.YEAR, currentYear);
                    int maxDayInPrevMonth = prevMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    tamilDay = maxDayInPrevMonth - startDay + currentDay + 1;
                }
                break;
            }
        }

        return tamilDay + " " + tamilMonth;

    }}