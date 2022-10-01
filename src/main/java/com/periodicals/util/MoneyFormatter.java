package com.periodicals.util;

public class MoneyFormatter {
    public static String toHumanReadable(int value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value / 100);
        sb.append(',');
        String coins = "" + value % 100;
        if (coins.length() == 1) {
            sb.append('0');
        }
        sb.append(coins);
        return sb.toString();
    }
}
