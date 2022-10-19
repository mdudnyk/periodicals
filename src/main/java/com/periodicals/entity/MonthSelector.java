package com.periodicals.entity;

import org.json.simple.JSONArray;

import java.io.Serializable;
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
}
