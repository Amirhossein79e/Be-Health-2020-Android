package com.amirhosseinemadi.behealth.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.amirhosseinemadi.behealth.model.entity.GeneralModel;

@Database(entities = GeneralModel.class, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    public abstract Dao dao();

}
