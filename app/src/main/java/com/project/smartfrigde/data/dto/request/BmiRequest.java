package com.project.smartfrigde.data.dto.request;

import com.project.smartfrigde.utils.Gender;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;

public class BmiRequest {
    private String weight;
    private String height;
    private String age;
    private Gender gender;
    private Long user_id = UserSecurePreferencesManager.getUser().getUser_id();

    public BmiRequest() {
    }

    public BmiRequest(String weight, String height, String age, Gender gender) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
}
