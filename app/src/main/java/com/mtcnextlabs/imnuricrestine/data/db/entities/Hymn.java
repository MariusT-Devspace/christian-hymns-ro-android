package com.mtcnextlabs.imnuricrestine.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Hymns")
public class Hymn {
    @PrimaryKey
    @ColumnInfo(index = true)
    public int id;

    @NonNull
    public String hymn_number;

    @NonNull
    public String title;

    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public boolean equals(Object obj){
        Hymn h = (Hymn) obj;
        if(this.id == h.id){
            return true;
        }else{
            return false;
        }
    }


}


