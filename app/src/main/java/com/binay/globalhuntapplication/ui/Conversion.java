package com.binay.globalhuntapplication.ui;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Binay.
 */
public class Conversion {
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("rate")
    private String rate;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getRate() {
        return rate;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
