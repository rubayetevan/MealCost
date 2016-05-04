package com.bdjobs.mealcost.RealmClass;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Tabriz on 04-May-16.
 */
public class Member extends RealmObject {
    private String email;
    @Required // Name cannot be null
    private String name;
    private String mobileNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
