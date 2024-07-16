package com.project.smartfrigde.model;

import java.io.Serializable;

public class FoodConsum implements Serializable {
    private Integer totalConsumedWeight;
    private Integer eggsTaken;
    private Integer weightOfMeat;

    public FoodConsum() {
    }

    public FoodConsum(Integer totalConsumedWeight, Integer eggsTaken, Integer weightOfMeat) {
        this.totalConsumedWeight = totalConsumedWeight;
        this.eggsTaken = eggsTaken;
        this.weightOfMeat = weightOfMeat;
    }

    public Integer getTotalConsumedWeight() {
        return totalConsumedWeight;
    }

    public void setTotalConsumedWeight(Integer totalConsumedWeight) {
        this.totalConsumedWeight = totalConsumedWeight;
    }

    public Integer getEggsTaken() {
        return eggsTaken;
    }

    public void setEggsTaken(Integer eggsTaken) {
        this.eggsTaken = eggsTaken;
    }

    public Integer getWeightOfMeat() {
        return weightOfMeat;
    }

    public void setWeightOfMeat(Integer weightOfMeat) {
        this.weightOfMeat = weightOfMeat;
    }
}
