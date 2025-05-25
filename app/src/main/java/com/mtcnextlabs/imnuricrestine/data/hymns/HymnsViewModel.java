package com.mtcnextlabs.imnuricrestine.data.hymns;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mtcnextlabs.imnuricrestine.models.Hymn;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HymnsViewModel extends AndroidViewModel {
    private final LiveData<List<Hymn>> _hymns;
    private List<Hymn> _eagerLoad;

    @Inject
    public HymnsViewModel(
            Application application,
            HymnsRepository hymnsRepository
    ) {
        super(application);
        try {
            // Eager initial load
            _eagerLoad = hymnsRepository.getHymns();

            // Reactive source
            _hymns = hymnsRepository.getHymnsAsync();

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Hymn> getHymns(){
        return _eagerLoad;
    }

    public LiveData<List<Hymn>> getHymnsAsync(){ return _hymns; }
}
