package com.bdjobs.mealcost.RealmClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Tabriz on 05-May-16.
 */
public class Meal extends RealmObject {


    @Required
    String name;
    int breakfast,launch,dinner;
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    public int getLaunch() {
        return launch;
    }

    public void setLaunch(int launch) {
        this.launch = launch;
    }

    public int getDinner() {
        return dinner;
    }

    public void setDinner(int dinner) {
        this.dinner = dinner;
    }
}
