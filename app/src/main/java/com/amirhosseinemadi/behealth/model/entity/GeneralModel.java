package com.amirhosseinemadi.behealth.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "general")
public class GeneralModel {

    public GeneralModel(){}

    @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER)
    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "step",typeAffinity = ColumnInfo.INTEGER)
    private int step;

    @ColumnInfo(name = "time",typeAffinity = ColumnInfo.INTEGER)
    private int time;

    @ColumnInfo(name = "distance",typeAffinity = ColumnInfo.REAL)
    private float distance;

    @ColumnInfo(name = "date",typeAffinity = ColumnInfo.INTEGER)
    private long date;

    @ColumnInfo(name = "calorie",typeAffinity = ColumnInfo.INTEGER)
    private int calories;

    @ColumnInfo(name = "calorie_item",typeAffinity = ColumnInfo.TEXT)
    private String caloriesItem;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getCaloriesItem() {
        return caloriesItem;
    }

    public void setCaloriesItem(String caloriesItem) {
        this.caloriesItem = caloriesItem;
    }


    public static class Builder {

        private int id;

        private int step;

        private int time;

        private float distance;

        private long date;

        private int calories;

        private String caloriesItem;

        public int getId() {
            return id;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public int getStep() {
            return step;
        }

        public Builder setStep(int step) {
            this.step = step;
            return this;
        }

        public int getTime() {
            return time;
        }

        public Builder setTime(int time) {
            this.time = time;
            return this;
        }

        public float getDistance() {
            return distance;
        }

        public Builder setDistance(float distance) {
            this.distance = distance;
            return this;
        }

        public long getDate() {
            return date;
        }

        public Builder setDate(long date) {
            this.date = date;
            return this;
        }

        public int getCalories() {
            return calories;
        }

        public Builder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public String getCaloriesItem() {
            return caloriesItem;
        }

        public Builder setCaloriesItem(String caloriesItem) {
            this.caloriesItem = caloriesItem;
            return this;
        }

        public GeneralModel build()
        {
            GeneralModel model = new GeneralModel();
            model.setCalories(calories);
            model.setCaloriesItem(caloriesItem);
            model.setDate(date);
            model.setDistance(distance);
            model.setId(id);
            model.setStep(step);
            model.setTime(time);
            return model;
        }

    }

}
