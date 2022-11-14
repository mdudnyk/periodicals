package com.periodicals.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {
    public static String getString(LocalDate date, String locale) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("LLL yyyy", Locale.forLanguageTag(locale));
        return date.format(formatter);

    }

    public static String getString(LocalDateTime date, String locale) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd LLL yyyy", Locale.forLanguageTag(locale));
        return date.format(formatter);
    }

    public static String getShortMonthName(int monthNumber, String locale) {
        LocalDate date = LocalDate.of(2022, monthNumber, 1);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("LLL", Locale.forLanguageTag(locale));
        return date.format(formatter);
    }
}