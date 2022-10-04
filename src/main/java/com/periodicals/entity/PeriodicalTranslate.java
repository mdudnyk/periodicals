package com.periodicals.entity;

import java.util.Objects;

public class PeriodicalTranslate {
    private final int periodicalID;
    private final String localeID;
    private String country;
    private String language;
    private String description;

    public PeriodicalTranslate(final int periodicalID, final String localeID,
                               final String country, final String language,
                               final String description) {
        this.periodicalID = periodicalID;
        this.localeID = localeID;
        this.country = country;
        this.language = language;
        this.description = description;
    }

    public int getPeriodicalID() {
        return periodicalID;
    }

    public String getLocaleID() {
        return localeID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PeriodicalTranslate that = (PeriodicalTranslate) o;
        return periodicalID == that.periodicalID && localeID.equals(that.localeID) && country.equals(that.country) && language.equals(that.language) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodicalID, localeID, country, language, description);
    }

    @Override
    public String toString() {
        return "PeriodicalTranslate{" +
                "periodicalID=" + periodicalID +
                ", localeID='" + localeID + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
