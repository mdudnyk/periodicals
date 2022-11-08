package com.periodicals.entity;

import org.json.simple.JSONArray;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MonthSelector implements Serializable {
    private final int year;
    private final JSONArray month;

    public MonthSelector(final int year, final JSONArray month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public JSONArray getMonth() {
        return month;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MonthSelector that = (MonthSelector) o;
        return year == that.year && month.equals(that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }

    @Override
    public String toString() {
        return "MonthSelector{" +
                "year=" + year +
                ", month=" + month +
                '}';
    }

    public static LocalDate getLastMonth(List<MonthSelector> list) {
        LocalDate date = LocalDate.of(2000, 1, 1);

        if (list != null && list.size() > 0) {
            list.sort(Comparator.comparingInt(MonthSelector::getYear));
            MonthSelector monthSelector = list.get(list.size() - 1);
            int maxMonth = 1;
            if (monthSelector != null) {
                for (int i = 0; i < 12; i++) {
                    boolean activeMonth = (boolean) monthSelector.getMonth().get(i);
                    if (activeMonth && i > maxMonth) {
                        maxMonth = i;
                    }
                }
                date = LocalDate.of(monthSelector.getYear(), maxMonth, 1);
                date = date.with(TemporalAdjusters.lastDayOfMonth());
            }
        }
        return date;
    }
}