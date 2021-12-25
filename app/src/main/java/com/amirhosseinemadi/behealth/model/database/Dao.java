package com.amirhosseinemadi.behealth.model.database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.amirhosseinemadi.behealth.model.entity.GeneralModel;

import java.util.List;

import io.reactivex.Flowable;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertGeneral(GeneralModel model);

    @Update
    void updateGeneral(GeneralModel model);

    @Query("select * from general where id = :id")
    Flowable<List<GeneralModel>> selectById(int id);

    @Query("select * from general limit 30 offset :myOffset")
    Flowable<List<GeneralModel>> selectStep(int myOffset);

    @Query("select * from general where id = :id")
    List<GeneralModel> selectByIdSync(int id);

    @Query("select * from general limit 30 offset :myOffset")
    List<GeneralModel> selectStepSync(int myOffset);

}
