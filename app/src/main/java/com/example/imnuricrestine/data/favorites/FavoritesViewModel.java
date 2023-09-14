package com.example.imnuricrestine.data.favorites;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.imnuricrestine.data.db.entities.Favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoritesViewModel extends AndroidViewModel {
    //private final MutableLiveData<ArrayList<Favorite>> _favorites;
    private final FavoritesRepository _favoritesRepository;

    @Inject
    public FavoritesViewModel(
            Application application,
            FavoritesRepository favoritesRepository
    ) {
        super(application);
        _favoritesRepository = favoritesRepository;
    }

    public LiveData<List<Favorite>> getFavorites() throws ExecutionException, InterruptedException { return _favoritesRepository.getFavorites(); }
    public void addFavorite(Favorite favorite) {
        try {
            _favoritesRepository.addFavorite(favorite.hymn_id);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFavorite(Favorite favorite) {
        try {
            _favoritesRepository.deleteFavorite(favorite);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
