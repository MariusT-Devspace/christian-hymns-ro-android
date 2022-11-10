package com.example.imnuricrestine.services;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.imnuricrestine.adapters.RVMainCustomAdapter;
import com.example.imnuricrestine.db.AppDatabase;
import com.example.imnuricrestine.db.HymnsDao;
import com.example.imnuricrestine.db.entities.HymnWithLyrics;
import com.example.imnuricrestine.models.Hymn;
import com.example.imnuricrestine.models.Verse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HymnsViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Hymn>> hymns;
    private HymnRepository repository;

    public HymnsViewModel(Application application) throws ExecutionException, InterruptedException {
        super(application);
        repository = new HymnRepository(application);
        hymns = new MutableLiveData<ArrayList<Hymn>>();
        hymns.setValue(repository.getHymns(application));   ;
    }
    public MutableLiveData<ArrayList<Hymn>> getHymns(){
        return hymns;
    }


}
