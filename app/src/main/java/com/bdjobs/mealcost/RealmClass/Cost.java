package com.bdjobs.mealcost.RealmClass;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Tabriz on 04-May-16.
 */
public class Cost extends RealmObject {
    double extraCost,mainCost;

    Member member;
    String date;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
