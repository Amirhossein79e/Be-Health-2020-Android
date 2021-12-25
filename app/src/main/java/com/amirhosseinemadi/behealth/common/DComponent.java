package com.amirhosseinemadi.behealth.common;

import android.content.Context;

import com.amirhosseinemadi.behealth.model.database.Dao;
import com.amirhosseinemadi.behealth.util.Calculator;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DaggerModule.class)
public interface DComponent {

    Context context();

    PrefManager prefManager();

    Calculator calculator();

    Dao dao();

}
