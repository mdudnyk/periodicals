package com.periodicals.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Topic {
    private int id;
    private Map<String, TopicTranslate> translate;

    {
        translate = new HashMap<>();
    }

    public Topic(int id, TopicTranslate currentTranslate) {
        this.id = id;
        translate.put(currentTranslate.getLocaleID(), currentTranslate);
    }

    public Topic(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Map<String, TopicTranslate> getAllTranslates() {
        return translate;
    }

    public TopicTranslate getTranslate(String localeID) {
        return translate.get(localeID);
    }

    public void addTranslate(TopicTranslate translate) {
        if (translate != null) {
            this.translate.put(translate.getLocaleID(), translate);
        }
    }

    public void addTranslationsMap(Map<String, TopicTranslate> translations) {
        this.translate.putAll(translations);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Topic topic = (Topic) o;
        return id == topic.id && Objects.equals(translate, topic.translate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, translate);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", " + translate +
                '}';
    }
}
