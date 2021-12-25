package com.amirhosseinemadi.behealth.common;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Be Health",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }


    public PrefManager setFirst(boolean status)
    {
        editor.putBoolean("first",status).commit();
        return this;
    }


    public boolean getFirst()
    {
        return sharedPreferences.getBoolean("first",true);
    }


    public PrefManager setReboot(boolean reboot)
    {
        editor.putBoolean("reboot",reboot).commit();
        return this;
    }


    public boolean getReboot()
    {
        return sharedPreferences.getBoolean("reboot",false);
    }


    public PrefManager setHeight(int height)
    {
        editor.putInt("height",height).commit();
        return this;
    }


    public int getHeight()
    {
        return sharedPreferences.getInt("height",0);
    }


    public PrefManager setWeight(int weight)
    {
        editor.putInt("weight",weight).commit();
        return this;
    }


    public int getWeight()
    {
        return sharedPreferences.getInt("weight",0);
    }


    public PrefManager setGender(String gender)
    {
        editor.putString("gender",gender).commit();
        return this;
    }


    public String getGender()
    {
        return sharedPreferences.getString("gender","");
    }


    public PrefManager setAge(int age)
    {
        editor.putInt("age",age).commit();
        return this;
    }


    public void setActivity(String activity)
    {
        editor.putString("activity",activity).commit();
    }


    public String getActivity()
    {
        return sharedPreferences.getString("activity","medium");
    }


    public int getAge()
    {
        return sharedPreferences.getInt("age",0);
    }


    public PrefManager setStride(float stride)
    {
        editor.putFloat("stride",stride).commit();
        return this;
    }


    public float getStride()
    {
        return sharedPreferences.getFloat("stride",0);
    }


    public PrefManager setTarget(int target)
    {
        editor.putInt("target",target).commit();
        return this;
    }


    public int getTarget()
    {
        return sharedPreferences.getInt("target",0);
    }


    public PrefManager setStep(float step)
    {
        editor.putFloat("step",step).commit();
        return this;
    }


    public Float getStep()
    {
        return sharedPreferences.getFloat("step",0f);
    }


    public PrefManager setPreviousStep(float steps)
    {
        editor.putFloat("previousStep",steps).commit();
        return this;
    }


    public float getPreviousStep()
    {
        return sharedPreferences.getFloat("previousFloat",0f);
    }


    public PrefManager setBackupStep(float steps)
    {
        editor.putFloat("backupStep",steps).commit();
        return this;
    }


    public float getBackupStep()
    {
        return sharedPreferences.getFloat("backupStep",0);
    }


    public PrefManager setTime(int time)
    {
        editor.putInt("time",time).commit();
        return this;
    }


    public int getTime()
    {
        return sharedPreferences.getInt("time",0);
    }


    public PrefManager setBmr(int bmr)
    {
        editor.putInt("bmr",bmr).commit();
        return this;
    }


    public int getBmr()
    {
        return sharedPreferences.getInt("bmr",0);
    }


}
