package com.periodicals.entity;

import com.periodicals.entity.enums.PublicationStatus;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Periodical implements Serializable {
    private int id;
    private int topicID;
    private String title;
    private String titleImgLink;
    private int price;
    private int frequency;
    private int minSubPeriod;
    private PublicationStatus status;
    private Map<String, PeriodicalTranslate> translate;
    private Map<Integer, MonthSelector> releaseMonth;

    {
        translate = new HashMap<>();
        releaseMonth = new HashMap<>();
    }

    public Periodical(final int id, final int topicID, final String title, final String titleImgLink,
                      final int price, final int frequency, final int minSubPeriod,
                      final PublicationStatus status) {
        this.id = id;
        this.topicID = topicID;
        this.title = title;
        this.titleImgLink = titleImgLink;
        this.price = price;
        this.frequency = frequency;
        this.minSubPeriod = minSubPeriod;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(final int topicID) {
        this.topicID = topicID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitleImgLink() {
        return titleImgLink;
    }

    public void setTitleImgLink(final String titleImgLink) {
        this.titleImgLink = titleImgLink;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(final int frequency) {
        this.frequency = frequency;
    }

    public int getMinSubPeriod() {
        return minSubPeriod;
    }

    public void setMinSubPeriod(final int minSubPeriod) {
        this.minSubPeriod = minSubPeriod;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(final PublicationStatus status) {
        this.status = status;
    }

    public Map<String, PeriodicalTranslate> getAllTranslates() {
        return translate;
    }

    public PeriodicalTranslate getTranslate(String locale) {
        return translate.get(locale);
    }

    public void addTranslate(final PeriodicalTranslate translate) {
        this.translate.put(translate.getLocaleID(), translate);
    }

    public Map<Integer, MonthSelector> getAllReleaseMonth() {
        return releaseMonth;
    }

    public MonthSelector getReleaseMonthByYear(int year) {
        return releaseMonth.get(year);
    }

    public void addReleaseMonth(final MonthSelector releaseMonth) {
        this.releaseMonth.put(releaseMonth.getYear(), releaseMonth);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Periodical that = (Periodical) o;
        return id == that.id && topicID == that.topicID && price == that.price
                && frequency == that.frequency && minSubPeriod == that.minSubPeriod
                && title.equals(that.title) && titleImgLink.equals(that.titleImgLink)
                && status == that.status && translate.equals(that.translate)
                && releaseMonth.equals(that.releaseMonth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topicID, title, titleImgLink, price,
                frequency, minSubPeriod, status, translate, releaseMonth);
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "id=" + id +
                ", topicID=" + topicID +
                ", title='" + title + '\'' +
                ", titleImgLink=" + titleImgLink +
                ", price=" + price +
                ", frequency=" + frequency +
                ", minSubPeriod=" + minSubPeriod +
                ", status=" + status +
                ", translate=" + translate +
                ", releaseMonth=" + releaseMonth +
                '}';
    }
}
