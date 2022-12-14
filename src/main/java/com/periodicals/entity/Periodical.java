package com.periodicals.entity;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Periodical implements Serializable {
    private int id;
    private final int topicID;
    private final String title;
    private final String titleImgLink;
    private final int price;
    private final JSONObject frequency;
    private final boolean isActive;
    private Map<String, PeriodicalTranslate> translation;
    private Map<Integer, MonthSelector> releaseCalendar;

    {
        translation = new HashMap<>();
    }

    public Periodical(final int topicID, final String title, final String titleImgLink,
                      final int price, final JSONObject frequency, final boolean isActive) {
        this.topicID = topicID;
        this.title = title;
        this.titleImgLink = titleImgLink;
        this.price = price;
        this.frequency = frequency;
        this.isActive = isActive;
    }

    public Periodical(final int id, final int topicID, final String title, final String titleImgLink,
                      final int price, final JSONObject frequency, final boolean isPeriodicalActive) {
        this.id = id;
        this.topicID = topicID;
        this.title = title;
        this.titleImgLink = titleImgLink;
        this.price = price;
        this.frequency = frequency;
        this.isActive = isPeriodicalActive;
    }

    public int getId() {
        return id;
    }

    public int getTopicID() {
        return topicID;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImgLink() {
        return titleImgLink;
    }

    public int getPrice() {
        return price;
    }

    public JSONObject getFrequency() {
        return frequency;
    }


    public boolean isActive() {
        return isActive;
    }

    public Map<String, PeriodicalTranslate> getTranslation() {
        return translation;
    }

    public Map<Integer, MonthSelector> getReleaseCalendar() {
        return releaseCalendar;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setTranslation(final Map<String, PeriodicalTranslate> translation) {
        this.translation = translation;
    }

    public void setTranslation(final PeriodicalTranslate translation) {
        this.translation.put(translation.getLocaleID(), translation);
    }

    public void setReleaseCalendar(final Map<Integer, MonthSelector> releaseCalendar) {
        this.releaseCalendar = releaseCalendar;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Periodical that = (Periodical) o;
        return id == that.id && topicID == that.topicID && price == that.price
                && isActive == that.isActive
                && title.equals(that.title) && titleImgLink.equals(that.titleImgLink)
                && frequency.equals(that.frequency) && Objects.equals(translation, that.translation)
                && Objects.equals(releaseCalendar, that.releaseCalendar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topicID, title, titleImgLink, price,
                frequency, isActive, translation, releaseCalendar);
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "id=" + id +
                ", topicID=" + topicID +
                ", title='" + title + '\'' +
                ", titleImgLink='" + titleImgLink + '\'' +
                ", price=" + price +
                ", frequency=" + frequency +
                ", isPeriodicalActive=" + isActive +
                ", translation=" + translation +
                ", releaseCalendar=" + releaseCalendar +
                '}';
    }
}
