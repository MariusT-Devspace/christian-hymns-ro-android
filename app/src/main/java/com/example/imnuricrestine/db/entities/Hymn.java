package com.example.imnuricrestine.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hymn {
    @PrimaryKey
    @ColumnInfo(index = true)
    public int hymn_index;

    @NonNull
    public String title;

    @Override
    public int hashCode(){
        return hymn_index;
    }

    @Override
    public boolean equals(Object obj){
        Hymn h = (Hymn) obj;
        if(this.hymn_index == h.hymn_index){
            return true;
        }else{
            return false;
        }
    }
}


