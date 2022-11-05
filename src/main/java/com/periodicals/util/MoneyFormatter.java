package com.periodicals.util;

import java.util.regex.Pattern;

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

    public static int toIntegerFormat(String value) {
        int intValue = -1;
        if (isValidMoneyFormat(value)) {
            int delimiterIndex = -1;
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                if (ch == ',' || ch == '.') {
                    delimiterIndex = i;
                    break;
                }
            }
            if (delimiterIndex != -1) {
                if (value.length() - delimiterIndex == 2) {
                    value = value + "0";
                }
                value = value.replaceAll("[.,]", "");
            }

            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                intValue = -2;
            }
        }
        if (intValue < 0) {
            throw new IllegalArgumentException("Not valid money format: " + value);
        }
        return intValue * 100;
    }

    private static boolean isValidMoneyFormat(final String value) {
        String regex = "^\\d*([.,]\\d{1,2})?";
        return Pattern.matches(regex, value);
    }
}