package com.example.imnuricrestine.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.imnuricrestine.data.models.Hymn;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HymnsViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Hymn>> _hymns;

    public HymnsViewModel(
            Application application
            //,SavedStateHandle savedStateHandle
    ) throws ExecutionException, InterruptedException {
        super(application);
        final HymnRepository repository = new HymnRepository(application);
        _hymns = new MutableLiveData<>();
        _hymns.setValue(repository.getHymns(application));
    }

    public LiveData<ArrayList<Hymn>> getHymns(){
        return _hymns;
    }
}
