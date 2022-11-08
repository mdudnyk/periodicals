package com.periodicals.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateForTable {
    public static String getString(LocalDate date, String locale) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("LLL yyyy", Locale.forLanguageTag(locale));
        return date.format(formatter);
    }
}