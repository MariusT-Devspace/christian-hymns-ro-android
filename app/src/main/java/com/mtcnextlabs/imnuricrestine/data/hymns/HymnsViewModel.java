package com.mtcnextlabs.imnuricrestine.data.hymns;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mtcnextlabs.imnuricrestine.models.Hymn;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HymnsViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Hymn>> _hymns;

    @Inject
    public HymnsViewModel(
            Application application,
            HymnsRepository hymnsRepository
            //,SavedStateHandle savedStateHandle
    ) {
        super(application);
        _hymns = new MutableLiveData<>();
        try {
            _hymns.setValue(hymnsRepository.getHymns());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<ArrayList<Hymn>> getHymns(){
        return _hymns;
    }
}
