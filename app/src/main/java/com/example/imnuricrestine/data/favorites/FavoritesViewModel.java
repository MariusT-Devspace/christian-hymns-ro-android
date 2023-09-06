package com.example.imnuricrestine.data.favorites;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.imnuricrestine.data.db.entities.Favorite;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoritesViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<Favorite>> _favorites;

    @Inject
    public FavoritesViewModel(
            Application application,
            FavoritesRepository favoritesRepository
    ) {
        super(application);
        _favorites = new MutableLiveData<>();
        try {
            _favorites.setValue(favoritesRepository.getFavorites());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<ArrayList<Favorite>> getFavorites() { return _favorites; }
}
