package com.amirhosseinemadi.behealth.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.amirhosseinemadi.behealth.BR;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;
import com.amirhosseinemadi.behealth.util.Calculator;

public class StatusVm extends BaseObservable {

    @Bindable
    public String bmi = "";
    @Bindable
    public String bmr = "";
    @Bindable
    public String drCalories = "";
    @Bindable
    public String target = "";
    @Bindable
    public String height = "";
    @Bindable
    public String weight = "";
    @Bindable
    public String activity = "";

    private PrefManager prefManager;
    private Calculator calculator;

    public StatusVm()
    {
        prefManager = Application.dComponent.prefManager();
        calculator = Application.dComponent.calculator();
        bmi = String.valueOf(calculator.calculateBmi(prefManager.getHeight(),prefManager.getWeight()));
        bmr = String.valueOf(prefManager.getBmr());
        drCalories = String.valueOf(calculator.calculateCaloriesN(Integer.parseInt(bmr),prefManager.getActivity()));
        target = String.valueOf(prefManager.getTarget());
        height = String.valueOf(prefManager.getHeight());
        weight = String.valueOf(prefManager.getWeight());
        activity = prefManager.getActivity();
    }

    public String getBmi() {
        notifyPropertyChanged(BR.bmi);
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBmr() {
        notifyPropertyChanged(BR.bmr);
        return bmr;
    }

    public void setBmr(String bmr) {
        this.bmr = bmr;
    }

    public String getDrCalories() {
        notifyPropertyChanged(BR.drCalories);
        return drCalories;
    }

    public void setDrCalories(String drCalories) {
        this.drCalories = drCalories;
    }

    public String getTarget() {
        notifyPropertyChanged(BR.target);
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getHeight() {
        notifyPropertyChanged(BR.height);
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        notifyPropertyChanged(BR.weight);
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActivity() {
        notifyPropertyChanged(BR.activity);
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

}
