package com.project.smartfrigde.data.dto.request;

public class FoodConSumedRequest {
    private String totalConsumedWeight;
    private int eggsTaken;

    public FoodConSumedRequest(String totalConsumedWeight, int eggsTaken) {
        this.totalConsumedWeight = totalConsumedWeight;
        this.eggsTaken = eggsTaken;
    }

    public FoodConSumedRequest() {
    }

    public String getTotalConsumedWeight() {
        return totalConsumedWeight;
    }

    public void setTotalConsumedWeight(String totalConsumedWeight) {
        this.totalConsumedWeight = totalConsumedWeight;
    }

    public int getEggsTaken() {
        return eggsTaken;
    }

    public void setEggsTaken(int eggsTaken) {
        this.eggsTaken = eggsTaken;
    }
}
