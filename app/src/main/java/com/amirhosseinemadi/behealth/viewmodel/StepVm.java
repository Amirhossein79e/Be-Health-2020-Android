package com.amirhosseinemadi.behealth.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LifecycleOwner;

import com.amirhosseinemadi.behealth.BR;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;
import com.amirhosseinemadi.behealth.util.Calculator;

import java.text.DecimalFormat;

public class StepVm extends BaseObservable {

    private PrefManager prefManager;
    private Calculator calculator;

    @Bindable
    public String steps = "0";

    public String target= "/";

    @Bindable
    public String calories = "0";

    @Bindable
    public String distance = "0";

    @Bindable
    public String time = "0";

    @Bindable
    public Integer progress = 0;

    @Bindable
    public Integer maxProgress = 0;


    public StepVm(LifecycleOwner lifecycleOwner)
    {
        prefManager = Application.dComponent.prefManager();
        calculator = Application.dComponent.calculator();

        target = String.valueOf("/"+prefManager.getTarget());
        maxProgress = prefManager.getTarget();
        time = String.valueOf(prefManager.getTime()/60);
        steps = String.valueOf(prefManager.getStep());
        progress = prefManager.getStep().intValue();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        distance = String.valueOf(decimalFormat.format((calculator.calculateDistance(prefManager.getStep(),prefManager.getStride()))/1000));
        int calorie = calculator.calculateCalories(prefManager.getBmr(),calculator.calculateMet(calculator.calculateDistance(prefManager.getStep(),prefManager.getStride()),prefManager.getTime()),prefManager.getTime());
        calories = String.valueOf(calorie);

    }


    public String getSteps() {
        notifyPropertyChanged(BR.steps);
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String  getTarget() {
        return target;
    }

    public void setTarget(String  target) {
        this.target = target;
    }

    public String getCalories() {
        notifyPropertyChanged(BR.calories);
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDistance() {
        notifyPropertyChanged(BR.distance);
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        notifyPropertyChanged(BR.time);
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getProgress() {
        notifyPropertyChanged(BR.progress);
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getMaxProgress() {
        notifyPropertyChanged(BR.maxProgress);
        return maxProgress;
    }

    public void setMaxProgress(Integer maxProgress) {
        this.maxProgress = maxProgress;
    }
}
