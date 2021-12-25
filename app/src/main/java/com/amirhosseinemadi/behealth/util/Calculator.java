package com.amirhosseinemadi.behealth.util;

import java.text.DecimalFormat;

public class Calculator {


    public String calculateBmi(int height, float weight)
    {
        float mHeight = height/100f;
        float bmi = weight/(mHeight*mHeight);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(bmi);
    }


    public float calculateStride(String gender,int height)
    {
        float stride = 0f;
        switch (gender)
        {
            case "male":
                stride = height*0.415f;
                break;

            case "female":
                stride = height*0.413f;
                break;
        }
        return stride;
    }


    public float calculateDistance(float steps,float stride)
    {
        return steps*stride/100;
    }


    public int calculateBmr(String gender,int weight,int height,int age)
    {
        int bmr = 0;
        switch (gender)
        {
            case "male":
                bmr = (int) (weight*10+6.25*height-5*age+5);
                break;

            case "female":
                bmr = (int) (weight*10+6.25*height-5*age-161);
                break;
        }
        return bmr;
    }


    public float calculateMet(float distance,int time)
    {
        float met;
        float speed = (distance/time/60)*3.6f;
        if (speed<3.2)
        {
            met = 2f;
        }else
            if (speed>=3.2 && speed<4)
            {
                met = 2.8f;
            }else
                if (speed>=4 && speed<4.8)
                {
                    met = 3f;
                }else
                if (speed>=4.8 && speed<5.6)
                {
                    met = 3.5f;
                }else
                    if (speed>=5.6 && speed<6.4)
                    {
                        met = 4.3f;
                    }else
                        if (speed>=6.4 && speed<7.2)
                        {
                            met = 5f;
                        }else
                            if (speed>=7.2 && speed<8)
                            {
                                met = 7f;
                            }else
                                {
                                met = 8.3f;
                                }

        return met;
    }


    public int calculateCalories(int bmr,float met,int time)
    {
        float fBmr = (float) bmr;
        float fTime = (float) time;
        float fCalories = fBmr*met/24*(fTime/3600);
        return (int) fCalories;
    }


    public int calculateCaloriesN(int bmr,String activity)
    {
        int calories;

        switch (activity)
        {
            case "veryLow":
                return (int) (bmr*1.2);
            case "low":
                return  (int) (bmr*1.375);
            case "medium":
                return  (int) (bmr*1.55);
            case "high":
                return  (int) (bmr*1.725);
            case "veryHigh":
                return  (int) (bmr*1.9);
            default:
                return 0;
        }

    }

}
