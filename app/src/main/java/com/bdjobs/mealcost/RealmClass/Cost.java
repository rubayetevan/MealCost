package com.bdjobs.mealcost.RealmClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Tabriz on 04-May-16.
 */
public class Cost extends RealmObject {
    double extraCost, mainCost;
    @Required
    String Name;
    Date date;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public double getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(double extraCost) {
        this.extraCost = extraCost;
    }

    public double getMainCost() {
        return mainCost;
    }

    public void setMainCost(double mainCost) {
        this.mainCost = mainCost;
    }
}
