package com.example.subscriptiontracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscriptions")
public class Subscription {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String subscriptionName;
    private String startDate;
    private String expirationDate;
    private long daysLeft;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(long daysLeft) {
        this.daysLeft = daysLeft;
    }
}
