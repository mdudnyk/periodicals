package com.periodicals.entity;

import com.periodicals.util.MoneyFormatter;

import java.io.Serializable;
import java.util.Objects;

public class PeriodicalForTable implements Serializable {
    private final int id;
    private final String title;
    private final String topicName;
    private final String price;
    private final boolean isPeriodicalActive;

    public PeriodicalForTable(final int id, final String title,
                              final String topicName, final int price,
                              final boolean isPeriodicalActive) {
        this.id = id;
        this.title = title;
        this.topicName = topicName;
        this.price = MoneyFormatter.toHumanReadable(price);
        this.isPeriodicalActive = isPeriodicalActive;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getPrice() {
        return price;
    }

    public boolean isPeriodicalActive() {
        return isPeriodicalActive;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PeriodicalForTable that = (PeriodicalForTable) o;
        return id == that.id && isPeriodicalActive == that.isPeriodicalActive
                && title.equals(that.title) && topicName.equals(that.topicName)
                && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, topicName, price, isPeriodicalActive);
    }

    @Override
    public String toString() {
        return "PeriodicalForTable{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", topicName='" + topicName + '\'' +
                ", price='" + price + '\'' +
                ", isPeriodicalActive=" + isPeriodicalActive +
                '}';
    }
}
