package com.bdjobs.mealcost.ModelClass;

/**
 * Created by Tabriz on 07-May-16.
 */
public class Summary {

    String name,totalMeal,mealRate,mealCost,extraCost,savings,payment;

    public Summary(String name, String totalMeal, String mealRate, String mealCost, String savings, String extraCost, String payment) {
        this.name = name;
        this.totalMeal = totalMeal;
        this.mealRate = mealRate;
        this.mealCost = mealCost;
        this.savings = savings;
        this.extraCost = extraCost;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalMeal() {
        return totalMeal;
    }

    public void setTotalMeal(String totalMeal) {
        this.totalMeal = totalMeal;
    }

    public String getMealRate() {
        return mealRate;
    }

    public void setMealRate(String mealRate) {
        this.mealRate = mealRate;
    }

    public String getMealCost() {
        return mealCost;
    }

    public void setMealCost(String mealCost) {
        this.mealCost = mealCost;
    }

    public String getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(String extraCost) {
        this.extraCost = extraCost;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
