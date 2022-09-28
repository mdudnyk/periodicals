package com.periodicals.entity;

import java.io.Serializable;
import java.util.Objects;

public class LocaleCustom implements Serializable {
    private String shortNameId;
    private String langNameOriginal;
    private String currency;
    private String flagIconURL;

    public LocaleCustom(final String shortNameId, final String langNameOriginal, final String currency, final String flagIconURL) {
        this.shortNameId = shortNameId;
        this.langNameOriginal = langNameOriginal;
        this.currency = currency;
        this.flagIconURL = flagIconURL;
    }

    public String getShortNameId() {
        return shortNameId;
    }

    public void setShortNameId(final String shortNameId) {
        this.shortNameId = shortNameId;
    }

    public String getLangNameOriginal() {
        return langNameOriginal;
    }

    public void setLangNameOriginal(final String langNameOriginal) {
        this.langNameOriginal = langNameOriginal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getFlagIconURL() {
        return flagIconURL;
    }

    public void setFlagIconURL(final String flagIconURL) {
        this.flagIconURL = flagIconURL;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LocaleCustom that = (LocaleCustom) o;
        return shortNameId.equals(that.shortNameId) && langNameOriginal.equals(that.langNameOriginal) && currency.equals(that.currency) && flagIconURL.equals(that.flagIconURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortNameId, langNameOriginal, currency, flagIconURL);
    }

    @Override
    public String toString() {
        return "LocaleCustom{" +
                "shortNameId='" + shortNameId + '\'' +
                ", langNameOriginal='" + langNameOriginal + '\'' +
                ", currency='" + currency + '\'' +
                ", flagIconURL='" + flagIconURL + '\'' +
                '}';
    }
}
