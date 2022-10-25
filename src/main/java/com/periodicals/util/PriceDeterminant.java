package com.periodicals.util;

import org.json.simple.JSONObject;

public class PriceDeterminant {
    private final int pricePerOneCopy;
    private final String period;
    private final Long amount;

    public PriceDeterminant(final int pricePerOneCopy, final JSONObject frequency) {
        this.pricePerOneCopy = pricePerOneCopy;
        this.period = (String) frequency.get("period");
        this.amount = (Long) frequency.get("amount");
        if (period == null || amount == null) {
            throw new IllegalArgumentException("Not valid 'frequency' JSON object. ");
        }
        if (!period.equals("week") && !period.equals("month") && !period.equals("year")) {
            throw new IllegalArgumentException("Not valid 'period' field in 'frequency' JSON object. ");
        }
        if (amount < 1 || amount > 11) {
            throw new IllegalArgumentException("Not valid 'amount' field in 'frequency' JSON object. ");
        }
    }

    public int getPrice() {
        if (isPeriodLessThanMonth()) {
            if (period.equals("month")) {
                return (int) (amount * pricePerOneCopy);
            } else {
                return 4 * (int) (amount * pricePerOneCopy);
            }
        }
        return pricePerOneCopy;
    }

    public boolean isPeriodLessThanMonth() {
        if (period.equals("year")) {
            return false;
        }
        if (period.equals("month")) {
            if (amount < 2) {
                return false;
            }
        }
        return true;
    }
}
