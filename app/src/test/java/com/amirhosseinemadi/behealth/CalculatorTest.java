package com.amirhosseinemadi.behealth;

import com.amirhosseinemadi.behealth.util.Calculator;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    public void bmiTest()
    {
        Assert.assertEquals(Float.parseFloat(calculator.calculateBmi(185,71f)),20.7f,0f);
    }

    @Test
    public void strideTest()
    {
        Assert.assertEquals(calculator.calculateStride("male",185),76.775f,0f);
    }


    @Test
    public void distanceTest()
    {
        Assert.assertEquals(calculator.calculateDistance(5751f,76.775f),4400f,100f);
    }


    @Test
    public void bmrTest()
    {
        Assert.assertEquals(calculator.calculateBmr("female",71,185,20),1605,0);
    }


    @Test
    public void metTest()
    {
        Assert.assertEquals(calculator.calculateMet(6000f,80),3f,0f);
    }


    @Test
    public void caloriesTest()
    {
        Assert.assertEquals(calculator.calculateCalories(1771,3,4800),295f,1f);
    }


    @Test
    public void caloriesNTest()
    {
        Assert.assertEquals(calculator.calculateCaloriesN(1771,5),3365,1);
    }

    

}
