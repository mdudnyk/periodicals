package com.periodicals.entity;

import java.io.Serializable;
import java.util.Objects;

public class PeriodicalTranslate implements Serializable {
    private final String localeID;
    private final String country;
    private final String language;
    private final String description;

    public PeriodicalTranslate(final String localeID, final String country,
                               final String language, final String description) {
        this.localeID = localeID;
        this.country = country;
        this.language = language;
        this.description = description;
    }

    public String getLocaleID() {
        return localeID;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PeriodicalTranslate that = (PeriodicalTranslate) o;
        return localeID.equals(that.localeID) && country.equals(that.country) && language.equals(that.language) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localeID, country, language, description);
    }

    @Override
    public String toString() {
        return "PeriodicalTranslate{" +
                "localeID='" + localeID + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
