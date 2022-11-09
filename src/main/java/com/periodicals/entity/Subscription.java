package com.periodicals.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Subscription implements Serializable {
    private int id;
    private final int userId;
    private final int periodicalId;
    private final String periodicalTitle;
    private final int price;
    private final LocalDateTime createdAt;
    private final LocalDate expiredAt;
    private Map<Integer, MonthSelector> subscriptionCalendar;

    {
        subscriptionCalendar = new HashMap<>();
    }

    public Subscription(final int id, final int userId, final int periodicalId, final String periodicalTitle,
                        final int price, final LocalDateTime createdAt, final LocalDate expiredAt,
                        final Map<Integer, MonthSelector> subscriptionCalendar) {
        this.id = id;
        this.userId = userId;
        this.periodicalId = periodicalId;
        this.periodicalTitle = periodicalTitle;
        this.price = price;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.subscriptionCalendar = subscriptionCalendar;
    }

    public Subscription(final int id, final int userId, final int periodicalId, final String periodicalTitle,
                        final int price, final LocalDateTime createdAt, final LocalDate expiredAt) {
        this.id = id;
        this.userId = userId;
        this.periodicalId = periodicalId;
        this.periodicalTitle = periodicalTitle;
        this.price = price;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getPeriodicalId() {
        return periodicalId;
    }

    public String getPeriodicalTitle() {
        return periodicalTitle;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDate getExpiredAt() {
        return expiredAt;
    }

    public Map<Integer, MonthSelector> getSubscriptionCalendar() {
        return subscriptionCalendar;
    }

    public void addSubscriptionYearList(List<MonthSelector> list) {
        for (MonthSelector m : list) {
            for (Object b : m.getMonth()) {
                if ((Boolean) b) {
                    this.subscriptionCalendar.put(m.getYear(), m);
                    break;
                }
            }
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Subscription that = (Subscription) o;
        return id == that.id && userId == that.userId && periodicalId == that.periodicalId
                && price == that.price && periodicalTitle.equals(that.periodicalTitle)
                && createdAt.equals(that.createdAt) && expiredAt.equals(that.expiredAt)
                && Objects.equals(subscriptionCalendar, that.subscriptionCalendar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, periodicalId, periodicalTitle,
                price, createdAt, expiredAt, subscriptionCalendar);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", userId=" + userId +
                ", periodicalId=" + periodicalId +
                ", periodicalTitle='" + periodicalTitle + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", subscriptionCalendar=" + subscriptionCalendar +
                '}';
    }
}