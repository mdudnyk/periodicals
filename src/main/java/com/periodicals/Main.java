package com.periodicals;

import com.periodicals.dao.exception.DAOException;


import com.periodicals.service.exceptions.ServiceException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws DAOException, ServiceException {
        LocalDate date = LocalDate.of(2023, 2, 1);
        date = date.with(TemporalAdjusters.lastDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yyyy", Locale.forLanguageTag("uk"));

        System.out.println(date.format(formatter));
    }
}
