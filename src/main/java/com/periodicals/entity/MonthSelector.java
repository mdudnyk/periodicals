package com.periodicals.entity;

import java.util.Objects;

public class MonthSelector {
    private int year;
    private String month;

    public MonthSelector (int year) {
        setYear(year);
        month = "000000000000";
    }

    public MonthSelector (int year, String month) {
        setYear(year);
        setMonth(month);
    }

    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        if (year > 2021 && year < 2100) {
            this.year = year;
        } else {
            throw new IllegalArgumentException("You are able to set year " +
                    "between 2021 and 2100. Your input is: " + year);
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(final String month) {
        if (month.length() == 12) {
            for (int i = 0; i < month.length(); i++) {
                if (month.charAt(i) != '0' && month.charAt(i) != '1') {
                    throw new IllegalArgumentException("Not appropriate 'month' field format. " +
                            "This string should looks like '010001001000'," +
                            " and consist of only from '1' and '0' symbols. Your input is: " + month);
                }
            }
            this.month = month;
        } else {
            throw new IllegalArgumentException("Not appropriate 'month' field format. " +
                    "This string should looks like '010001001000'," +
                    " and be 12 symbols long. Your input is: " + month);
        }
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
                ", month='" + month + '\'' +
                '}';
    }
}
