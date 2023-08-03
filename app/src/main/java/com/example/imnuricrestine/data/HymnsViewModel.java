package com.example.imnuricrestine.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.imnuricrestine.data.db.entities.Favorites;
import com.example.imnuricrestine.data.models.Hymn;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HymnsViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Hymn>> _hymns;
    private final MutableLiveData<List<Favorites>> _favorites;

    public HymnsViewModel(
            Application application
            //,SavedStateHandle savedStateHandle
    ) throws ExecutionException, InterruptedException {
        super(application);
        final HymnRepository repository = new HymnRepository(application);
        _hymns = new MutableLiveData<>();
        _hymns.setValue(repository.getHymns(application));
        _favorites = new MutableLiveData<>();
        _favorites.setValue(repository.getFavorites(application));
    }

    public LiveData<ArrayList<Hymn>> getHymns(){
        return _hymns;
    }
    public LiveData<List<Favorites>> getFavorites() { return _favorites; }
}
