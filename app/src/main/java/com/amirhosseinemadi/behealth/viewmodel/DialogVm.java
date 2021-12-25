package com.amirhosseinemadi.behealth.viewmodel;

import androidx.databinding.BaseObservable;

import com.amirhosseinemadi.behealth.callback.DialogVmCallback;
import com.amirhosseinemadi.behealth.common.Application;
import com.amirhosseinemadi.behealth.common.PrefManager;

public class DialogVm extends BaseObservable {

    public String weight = "";
    public String height = "";
    public String age = "";
    public boolean maleChecked = false;
    public boolean femaleChecked = false;
    public boolean veryLow = false;
    public boolean low = false;
    public boolean medium = false;
    public boolean high = false;
    public boolean veryHigh = false;
    private PrefManager prefManager;
    private DialogVmCallback dialogVmCallback;

    public DialogVm(DialogVmCallback dialogVmCallback)
    {
        this.dialogVmCallback = dialogVmCallback;
        prefManager = Application.dComponent.prefManager();
    }


    public void submitClick()
    {
        if ((maleChecked || femaleChecked)&& !weight.isEmpty() && !height.isEmpty()&& !age.isEmpty() && (veryLow||low||medium||high||veryHigh))
        {
            if (Integer.parseInt(height)!=0 && Integer.parseInt(weight)!=0 && Integer.parseInt(age)!=0)
            {
            if (maleChecked)
            {
                prefManager.setGender("male");
                prefManager.setStride(Application.dComponent.calculator().calculateStride("male",Integer.parseInt(height)));
                prefManager.setBmr(Application.dComponent.calculator().calculateBmr("male",Integer.parseInt(weight),Integer.parseInt(height),Integer.parseInt(age)));
            }else
                {
                    prefManager.setGender("female");
                    prefManager.setStride(Application.dComponent.calculator().calculateStride("female",Integer.parseInt(height)));
                    prefManager.setBmr(Application.dComponent.calculator().calculateBmr("female",Integer.parseInt(weight),Integer.parseInt(height),Integer.parseInt(age)));
                }

                prefManager.setWeight(Integer.parseInt(weight));
                prefManager.setHeight(Integer.parseInt(height));
                prefManager.setAge(Integer.parseInt(age));
                prefManager.setTarget(6000);

                if (veryLow)
                {
                    prefManager.setActivity("veryLow");
                }else if (low)
                {
                    prefManager.setActivity("low");
                }else if (medium)
                {
                    prefManager.setActivity("medium");
                }else if (high)
                {
                    prefManager.setActivity("high");
                }else if (veryHigh)
                {
                    prefManager.setActivity("veryHigh");
                }

                dialogVmCallback.onCorrect();
                dialogVmCallback.onCanceled();

            }else
            {
                dialogVmCallback.onIncorrect();
            }

        }else
        {
            dialogVmCallback.onIncorrect();
        }
    }

}
