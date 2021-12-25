package com.amirhosseinemadi.behealth.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.amirhosseinemadi.behealth.BR;
import com.amirhosseinemadi.behealth.callback.ChangeVmCallback;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;
import com.amirhosseinemadi.behealth.util.Calculator;

public class ChangeVm extends BaseObservable {


    private PrefManager prefManager;
    private ChangeVmCallback changeVmCallback;
    private Calculator calculator;

    @Bindable
    public String currentHeight;
    @Bindable
    public String currentWeight;
    @Bindable
    public String currentAge;

    public String height = "";
    public String weight = "";
    public String age = "";

    public boolean veryLow = false;
    public boolean low = false;
    public boolean medium = false;
    public boolean high = false;
    public boolean veryHigh = false;


    public ChangeVm(ChangeVmCallback changeVmCallback)
    {
        prefManager = Application.dComponent.prefManager();
        calculator = Application.dComponent.calculator();
        this.changeVmCallback = changeVmCallback;
        currentHeight = String.valueOf(prefManager.getHeight());
        currentWeight = String.valueOf(prefManager.getWeight());
        currentAge = String.valueOf(prefManager.getAge());
        switch (prefManager.getActivity())
        {
            case "veryLow":
                veryLow = true;
                break;
            case "low":
                low = true;
                break;
            case "medium":
                medium = true;
                break;
            case "high":
                high = true;
                break;
            case "veryHigh":
                veryHigh = true;
                break;
            default:
                break;
        }

    }


    public void submitOnCLick()
    {

        if (!height.isEmpty())
        {
            prefManager.setHeight(Integer.parseInt(height));
            prefManager.setStride(calculator.calculateStride(prefManager.getGender(), Integer.parseInt(height)));
            prefManager.setBmr(calculator.calculateBmr(prefManager.getGender(),prefManager.getWeight(), Integer.parseInt(height),prefManager.getAge()));
            currentHeight = height;
        }
        if (!weight.isEmpty())
        {
            prefManager.setWeight(Integer.parseInt(weight));
            prefManager.setBmr(calculator.calculateBmr(prefManager.getGender(), Integer.parseInt(weight),prefManager.getHeight(),prefManager.getAge()));
            currentWeight = weight;
        }
        if (!age.isEmpty())
        {
            prefManager.setAge(Integer.parseInt(age));
            prefManager.setBmr(calculator.calculateBmr(prefManager.getGender(),prefManager.getWeight(),prefManager.getHeight(), Integer.parseInt(age)));
            currentAge = age;
        }

        if (veryLow)
        {
            prefManager.setActivity("veryLow");
        }else if (low)
        {
            prefManager.setActivity("low");
        }

        changeVmCallback.onSubmit();

    }


    public String getCurrentHeight() {
        notifyPropertyChanged(BR.currentHeight);
        return currentHeight;
    }

    public void setCurrentHeight(String currentHeight) {
        this.currentHeight = currentHeight;
    }

    public String getCurrentWeight() {
        notifyPropertyChanged(BR.currentWeight);
        return currentWeight;
    }

    public void setCurrentWeight(String currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getCurrentAge() {
        notifyPropertyChanged(BR.currentAge);
        return currentAge;
    }

    public void setCurrentAge(String currentAge) {
        this.currentAge = currentAge;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isVeryLow() {
        return veryLow;
    }

    public void setVeryLow(boolean veryLow) {
        this.veryLow = veryLow;
    }

    public boolean isLow() {
        return low;
    }

    public void setLow(boolean low) {
        this.low = low;
    }

    public boolean isMedium() {
        return medium;
    }

    public void setMedium(boolean medium) {
        this.medium = medium;
    }

    public boolean isHigh() {
        return high;
    }

    public void setHigh(boolean high) {
        this.high = high;
    }

    public boolean isVeryHigh() {
        return veryHigh;
    }

    public void setVeryHigh(boolean veryHigh) {
        this.veryHigh = veryHigh;
    }
}
