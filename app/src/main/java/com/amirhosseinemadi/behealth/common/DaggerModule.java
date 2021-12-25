package com.amirhosseinemadi.behealth.common;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.amirhosseinemadi.behealth.model.database.Dao;
import com.amirhosseinemadi.behealth.model.database.DataBase;
import com.amirhosseinemadi.behealth.util.Calculator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerModule {

    private final Context context;

    public DaggerModule(Context context)
    {
        this.context = context;
    }

    @Provides
    public Context context()
    {
        return context;
    }

    @Singleton
    @Provides
    public PrefManager prefManager()
    {
        return new PrefManager(context);
    }

    @Singleton
    @Provides
    public Calculator calculator()
    {
        return new Calculator();
    }

    @Singleton
    @Provides
    public Dao dao()
    {
        Dao dao = Room.databaseBuilder(context,DataBase.class,"main").build().dao();
        return dao;
    }

}
