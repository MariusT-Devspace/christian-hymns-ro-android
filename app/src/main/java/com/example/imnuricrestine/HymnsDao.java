package com.example.imnuricrestine;

import android.hardware.lights.LightState;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HymnsDao {
    @Query("SELECT * FROM Hymns")
    List<Hymns> getAll();
}
