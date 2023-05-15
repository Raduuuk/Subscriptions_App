package com.example.subscriptiontracker;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface SubscriptionDao {

    @Insert
    void insert(Subscription subscription);
}