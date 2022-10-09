package com.periodicals.entity;

import java.util.*;

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

    public static void sortByName(List<Topic> source, String currentLocaleId, String defaultLocaleId) {
        Collections.sort(source, new Comparator<Topic>() {
            @Override
            public int compare(final Topic o1, final Topic o2) {
                TopicTranslate ts1 = o1.getTranslate(currentLocaleId);
                if (ts1 == null) {
                    ts1 = o1.getTranslate(defaultLocaleId);
                }
                String name1 = ts1.getName();

                TopicTranslate ts2 = o2.getTranslate(currentLocaleId);
                if (ts2 == null) {
                    ts2 = o2.getTranslate(defaultLocaleId);
                }
                String name2 = ts2.getName();

                int result = 0;
                if (name1 != null && name2 != null) {
                    result = name1.compareTo(name2);
                }
                return result;
            }
        });
    }
}
