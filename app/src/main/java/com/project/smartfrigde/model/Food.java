package com.project.smartfrigde.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Food implements Parcelable {
    private Long food_id;
    private String food_name;
    private Integer date_expired;
    private Integer calories_per_unit;
    private String unit;
    private Long food_category_id;

    public Food(Long food_id, String food_name, Integer date_expired, Integer calories_per_unit, String unit, Long food_category_id) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.date_expired = date_expired;
        this.calories_per_unit = calories_per_unit;
        this.unit = unit;
        this.food_category_id = food_category_id;
    }
    protected Food(Parcel in) {
        if (in.readByte() == 0) {
            food_id = null;
        } else {
            food_id = in.readLong();
        }
        food_name = in.readString();
        if (in.readByte() == 0) {
            date_expired = null;
        } else {
            date_expired = in.readInt();
        }
        if (in.readByte() == 0) {
            calories_per_unit = null;
        } else {
            calories_per_unit = in.readInt();
        }
        unit = in.readString();
        if (in.readByte() == 0) {
            food_category_id = null;
        } else {
            food_category_id = in.readLong();
        }
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (food_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(food_id);
        }
        dest.writeString(food_name);
        if (date_expired == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(date_expired);
        }
        if (calories_per_unit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(calories_per_unit);
        }
        dest.writeString(unit);
        if (food_category_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(food_category_id);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public Integer getDate_expired() {
        return date_expired;
    }

    public void setDate_expired(Integer date_expired) {
        this.date_expired = date_expired;
    }

    public Integer getCalories_per_unit() {
        return calories_per_unit;
    }

    public void setCalories_per_unit(Integer calories_per_unit) {
        this.calories_per_unit = calories_per_unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getFood_category_id() {
        return food_category_id;
    }

    public void setFood_category_id(Long food_category_id) {
        this.food_category_id = food_category_id;
    }
}
