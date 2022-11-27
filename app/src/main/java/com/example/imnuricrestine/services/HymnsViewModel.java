package com.example.imnuricrestine.services;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.imnuricrestine.models.Hymn;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HymnsViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Hymn>> hymns;
    private HymnRepository repository;
    //private SavedStateHandle state;

    public HymnsViewModel(
            Application application
            //,SavedStateHandle savedStateHandle
    ) throws ExecutionException, InterruptedException {
        super(application);
        repository = new HymnRepository(application);
        hymns = new MutableLiveData<>();
        hymns.setValue(repository.getHymns(application));
        //state = savedStateHandle;

        //state.set("hymns", repository.getHymns(application));
        //hymns = new MutableLiveData<ArrayList<Hymn>>(state.get("hymns"));
    }

    public MutableLiveData<ArrayList<Hymn>> getHymns(){

        return hymns;
        //return state.getLiveData("hymns");
    }
}
