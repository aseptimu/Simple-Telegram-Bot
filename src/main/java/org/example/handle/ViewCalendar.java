package org.example.handle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class ViewCalendar {
    private LocalDate date;
    private Locale locale;
    private boolean isNow;

    public ViewCalendar(String callBackData) {
        if (callBackData == null) {
            this.date = LocalDate.now();
            isNow = true;
        } else {
            this.date = parseCallBackData(callBackData);
        }
        this.locale = new Locale.Builder().setLanguage("ru").build();
    }

    private LocalDate parseCallBackData(String callBackData) {
        String[] dates = callBackData.split(" ");
        int year = Integer.parseInt(dates[3]);
        Month month = Month.of(Integer.parseInt(dates[2]));
        LocalDate now = LocalDate.now();
        date = LocalDate.of(year, month, now.getDayOfMonth());
        if (dates[0].startsWith("previousCalendar")) {
            decrementMonth();
        } else if (dates[0].startsWith("nextCalendar")) {
            incrementMonth();
        }
        isNow = date.getYear() == now.getYear() && date.getMonth() == now.getMonth();
        return date;
    }

    private void incrementMonth() {
        date = date.plusMonths(1);
    }

    private void decrementMonth() {
        date = date.minusMonths(1);
    }

    public String getCalendarDate() {
        return date.getDayOfMonth() + " " + date.getMonthValue() + " " + date.getYear();
    }
    
    public String getCalendar() {
        return getFormattedMonthCalendar("Markdown");
    }
    
    private String getFormattedMonthCalendar(String format) {
        String defaultFormat = getCurrentMonthCalendar();
        if (format == null) {
            return defaultFormat;
        }

        if (format.equalsIgnoreCase("html")) {
            return "<pre>" + defaultFormat + "</pre>";
        } else if (format.equalsIgnoreCase("Markdown")) {
            return "```" + defaultFormat + "```";
        }
        return defaultFormat;
    }

    private String getCurrentMonthCalendar() {
        int currentDay = date.getDayOfMonth();
        LocalDate currentDate = date;
        date = date.minusDays(currentDay - 1);
        var month = date.getMonth();
        int days = month.length(date.isLeapYear());
        int firstWeekDay = date.getDayOfWeek().getValue();
        StringBuilder builder = new StringBuilder();
        String mon = month.getDisplayName(TextStyle.FULL_STANDALONE, locale);
        builder.append(String.format("    %s, %d\n\n",
                mon.substring(0, 1).toUpperCase() + mon.substring(1), date.getYear()));
        for(var weekday : DayOfWeek.values()){
            String day = weekday.getDisplayName(TextStyle.SHORT_STANDALONE, locale);
            builder.append(day.substring(0, 1).toUpperCase()).append(day.substring(1)).append(" ");
        }
        builder.append('\n');
        builder.append("   ".repeat(Math.max(0, firstWeekDay - 1)));
        for(int day = 1; day <= days; ++day){
            if (day == currentDay && isNow) {
                builder.append(String.format("%d%s%d ", day / 10, "\u0332", day % 10));
            } else {
                builder.append(String.format("%02d ", day));
            }
            if((day + firstWeekDay)% 7 == 1){
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
