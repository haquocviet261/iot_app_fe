package com.project.smartfrigde.model;

import com.project.smartfrigde.utils.Gender;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;

public class Bmi {
    private Long bmi_id;
    private String weight;
    private String height;
    private String age;
    private Gender gender;
    private final Long user_id = UserSecurePreferencesManager.getUser().getUser_id();
    private Double calories;

    public Bmi(Long bmi_id, String weight, String height, String age, Gender gender,Double calories) {
        this.bmi_id = bmi_id;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.calories = calories;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Double getCalories() {
        if (weight == null || height == null || age == null || gender == null) {
            return null;
        }
        double weightKg = Double.parseDouble(weight);
        double heightCm = Double.parseDouble(height);
        int ageYears = Integer.parseInt(age);
        double bmr;
        if (gender == Gender.MALE) {
            bmr = 88.362 + (13.397 * weightKg) + (4.799 * heightCm) - (5.677 * ageYears);
        } else {
            bmr = 447.593 + (9.247 * weightKg) + (3.098 * heightCm) - (4.330 * ageYears);
        }
        double totalCalories = bmr * 1.55;
        return  totalCalories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Long getBmi_id() {
        return bmi_id;
    }

    public void setBmi_id(Long bmi_id) {
        this.bmi_id = bmi_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
