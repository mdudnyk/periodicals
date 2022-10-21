package com.periodicals.entity;

import com.periodicals.util.MoneyFormatter;

import java.io.Serializable;
import java.util.Objects;

public class PeriodicalForHomePage implements Serializable {
    private final int id;
    private final String title;
    private final String titleImgName;
    private final String price;

    public PeriodicalForHomePage(final int id, final String title, final String titleImgName, final int price) {
        this.id = id;
        this.title = title;
        this.titleImgName = titleImgName;
        this.price = MoneyFormatter.toHumanReadable(price);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImgName() {
        return titleImgName;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PeriodicalForHomePage that = (PeriodicalForHomePage) o;
        return id == that.id && title.equals(that.title) && Objects.equals(titleImgName, that.titleImgName) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, titleImgName, price);
    }

    @Override
    public String toString() {
        return "PeriodicalForHomePage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", titleImgName='" + titleImgName + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
