package com.periodicals.entity;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class SubscriptionDetails implements Serializable {
    private final String periodicalTitle;
    private String titleImgLink;
    private String topicName;
    private String originCountry;
    private String publishingLanguage;
    private JSONObject publishingFrequency;
    private final Map<Integer, MonthSelector> subscriptionCalendar;

    public SubscriptionDetails(final String periodicalTitle,
                               final Map<Integer, MonthSelector> subscriptionCalendar) {
        this.periodicalTitle = periodicalTitle;
        this.subscriptionCalendar = subscriptionCalendar;
    }

    public String getPeriodicalTitle() {
        return periodicalTitle;
    }

    public String getTitleImgLink() {
        return titleImgLink;
    }

    public void setTitleImgLink(final String titleImgLink) {
        this.titleImgLink = titleImgLink;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(final String topicName) {
        this.topicName = topicName;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(final String originCountry) {
        this.originCountry = originCountry;
    }

    public String getPublishingLanguage() {
        return publishingLanguage;
    }

    public void setPublishingLanguage(final String publishingLanguage) {
        this.publishingLanguage = publishingLanguage;
    }

    public JSONObject getPublishingFrequency() {
        return publishingFrequency;
    }

    public void setPublishingFrequency(final JSONObject publishingFrequency) {
        this.publishingFrequency = publishingFrequency;
    }

    public Map<Integer, MonthSelector> getSubscriptionCalendar() {
        return subscriptionCalendar;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SubscriptionDetails that = (SubscriptionDetails) o;
        return Objects.equals(periodicalTitle, that.periodicalTitle)
                && Objects.equals(titleImgLink, that.titleImgLink)
                && Objects.equals(topicName, that.topicName)
                && Objects.equals(originCountry, that.originCountry)
                && Objects.equals(publishingLanguage, that.publishingLanguage)
                && Objects.equals(publishingFrequency, that.publishingFrequency)
                && Objects.equals(subscriptionCalendar, that.subscriptionCalendar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodicalTitle, titleImgLink, topicName,
                originCountry, publishingLanguage, publishingFrequency,
                subscriptionCalendar);
    }

    @Override
    public String toString() {
        return "SubscriptionDetails{" +
                "periodicalTitle='" + periodicalTitle + '\'' +
                ", titleImgLink='" + titleImgLink + '\'' +
                ", topicName='" + topicName + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", publishingLanguage='" + publishingLanguage + '\'' +
                ", publishingFrequency='" + publishingFrequency + '\'' +
                ", subscriptionCalendar=" + subscriptionCalendar +
                '}';
    }
}