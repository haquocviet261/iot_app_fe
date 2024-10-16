package com.project.smartfrigde.model;

import java.io.Serializable;

public class FoodConsum implements Serializable {
    private String totalConsumedWeight;
    private String eggsTaken;
    private String weightOfMeat;

    public FoodConsum() {
    }

    public FoodConsum(String totalConsumedWeight, String eggsTaken, String weightOfMeat) {
        this.totalConsumedWeight = totalConsumedWeight;
        this.eggsTaken = eggsTaken;
        this.weightOfMeat = weightOfMeat;
    }

    public String getTotalConsumedWeight() {
        return totalConsumedWeight;
    }

    public void setTotalConsumedWeight(String totalConsumedWeight) {
        this.totalConsumedWeight = totalConsumedWeight;
    }

    public String getEggsTaken() {
        return eggsTaken;
    }

    public void setEggsTaken(String eggsTaken) {
        this.eggsTaken = eggsTaken;
    }

    public String getWeightOfMeat() {
        return weightOfMeat;
    }

    public void setWeightOfMeat(String weightOfMeat) {
        this.weightOfMeat = weightOfMeat;
    }
    public int calculateCalories() {
        int caloriesPerGram = 2;
        int caloriesPerEgg = 70;
        int totalCalories = 0;

        if (totalConsumedWeight != null) {
            totalCalories += Integer.parseInt(totalConsumedWeight) * caloriesPerGram;
        }

        if (eggsTaken != null) {
            totalCalories += Integer.parseInt(eggsTaken) * caloriesPerEgg;
        }

        return totalCalories;
    }
}
