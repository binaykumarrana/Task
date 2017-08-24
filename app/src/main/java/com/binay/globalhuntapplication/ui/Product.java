package com.binay.globalhuntapplication.ui;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Binay.
 */

class Product {
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("currency")
    private String currency;

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", currency_bg_selected='" + currency + '\'' +
                '}';
    }
}
