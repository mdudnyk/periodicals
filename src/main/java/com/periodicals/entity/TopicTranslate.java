package com.periodicals.entity;

import java.util.Objects;

public class TopicTranslate {
    private int topicID;
    private final String localeID;
    private String name;

    public TopicTranslate(final int topicID, final String localeID, final String name) {
        this.topicID = topicID;
        this.localeID = localeID;
        this.name = name;
    }

    public void setTopicID(final int topicID) {
        this.topicID = topicID;
    }

    public int getTopicID() {
        return topicID;
    }

    public String getLocaleID() {
        return localeID;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TopicTranslate that = (TopicTranslate) o;
        return topicID == that.topicID && localeID.equals(that.localeID) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicID, localeID, name);
    }

    @Override
    public String toString() {
        return "TopicTranslate{" +
                "topicID=" + topicID +
                ", localeID='" + localeID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
